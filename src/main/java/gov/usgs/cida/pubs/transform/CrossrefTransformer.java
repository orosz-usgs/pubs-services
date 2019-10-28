package gov.usgs.cida.pubs.transform;

import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.io.IOUtils;
import org.apache.commons.text.StringEscapeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableMap;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import gov.usgs.cida.pubs.ConfigurationService;
import gov.usgs.cida.pubs.PubsConstantsHelper;
import gov.usgs.cida.pubs.busservice.intfc.IPublicationBusService;
import gov.usgs.cida.pubs.domain.ContributorType;
import gov.usgs.cida.pubs.domain.Publication;
import gov.usgs.cida.pubs.domain.PublicationContributor;
import gov.usgs.cida.pubs.utility.PubsUtils;

/**
 * Transforms Publications into Crossref XML. One Transformer should be 
 * instantiated for each batch.
 */
public class CrossrefTransformer extends Transformer {
	private static final Logger LOG = LoggerFactory.getLogger(CrossrefTransformer.class);

	protected Configuration templateConfiguration;
	protected OutputStreamWriter streamWriter;
	protected BufferedWriter bufferedWriter;
	protected ConfigurationService configurationService;
	protected IPublicationBusService pubBusService;
	protected final String authorKey;
	protected final String editorKey;
	protected final String batchId;
	protected final String timestamp;

	/**
	 * Constructs and initializes a transformer with a particular batch id
	 * and timestamp every time.
	 * @param target
	 * @param templateConfiguration
	 * @param crossRefDepositorEmail String email used in Crossref submission
	 * @param pubBusService
	 */
	public CrossrefTransformer(
		OutputStream target,
		Configuration templateConfiguration,
		ConfigurationService configurationService,
		IPublicationBusService pubBusService,
		String authorKey,
		String editorKey
	) {
		super(target, null);
		this.templateConfiguration = templateConfiguration;
		this.configurationService = configurationService;
		try {
			this.streamWriter = new OutputStreamWriter(target, PubsConstantsHelper.DEFAULT_ENCODING);
		} catch (UnsupportedEncodingException ex) {
			//we can't do anything about this
			throw new RuntimeException(ex);
		}
		this.bufferedWriter = new BufferedWriter(streamWriter);
		this.pubBusService = pubBusService;
		this.authorKey = authorKey;
		this.editorKey = editorKey;
		this.batchId = UUID.randomUUID().toString();
		this.timestamp = String.valueOf(new Date().getTime());
		init();
	}

	public String getTimestamp() {
		return timestamp;
	}

	public String getBatchId() {
		return batchId;
	}

	@Override
	protected void init() {
		
		String timestamp = getTimestamp();
		String batchId = getBatchId();
		Map<String, String> model = ImmutableMap.of(
			"doi_batch_id", batchId,
			"submission_timestamp", timestamp,
			"depositor_email", configurationService.getCrossrefDepositorEmail()
		);
		writeHeader(model);
	}

	@Override
	protected void writeHeader(Map<?,?> model) {
		LOG.trace("writing crossref header");
		try{
			writeModelToTemplate(model, "crossref/header.ftlx");
		} catch (IOException | TemplateException e) {
			throw new RuntimeException("Error writing header", e);
		}
	}

	@Override
	protected void writeData(Map<?, ?> result) throws IOException {
		LOG.warn(this.getClass().getCanonicalName() + ".writeData() has no effect. This class uses POJOs instead of Maps");
	}

	@Override
	public void write(Object result) throws IOException {
		//We do not catch any ClassCastExceptions because we want loud, 
		//early failure.
		writeResult((Publication<?>)result);
	}

	/**
	 * 
	 * @param pub
	 * @throws IOException 
	 */
	protected void writeResult(Publication<?> pub) throws IOException {
		boolean retry = true;
		String template = "crossref/body.ftlx";
		List<PublicationContributor<?>> contributors = getContributors(pub);
		String xmlComment = getMinimalRecordComment(pub, contributors);

		if(!xmlComment.isEmpty()) {
			template = "crossref/body_minimal.ftlx";
			retry = false;
		}

		Map<String, Object> model = makeModel(pub, contributors);
		try {
			LOG.trace("Writing crossref report entry for publication with indexId = '" + pub.getIndexId() + "'");
			writeModelToTemplate(model, template);
			retry = false;
		} catch (TemplateException | IOException e) {
			String logMessage = String.format("Error transforming publication (%s) into Crossref XML.",
								getPubIdMsg(pub));
			if(retry) {
				logMessage += " Retrying, creating minimal Crossref XML";
				template = "crossref/body_minimal.ftlx";
			} else {
				xmlComment = getExcludedErrorMessage(pub);
			}
			LOG.error(logMessage, e);
		}

		if(retry) {
			String logMessage = "";
			try {
				writeModelToTemplate(model, template);
				xmlComment = "Minimal doi record shown due to errors transforming full publication.";
				logMessage = String.format("Created minimal Crossref XML (%s)", getPubIdMsg(pub));
				LOG.info(logMessage);
			} catch (TemplateException | IOException e) {
				logMessage = String.format("Error transforming publication (%s) into minimal Crossref XML.",
									getPubIdMsg(pub));
				xmlComment = getExcludedErrorMessage(pub);
				LOG.error(logMessage, e);
			}
		}

		//add error message as a comment to the xml document
		if(!xmlComment.isEmpty()) {
			writeComment(xmlComment);
		}
	}

	// return the reason the minimal record created or empty string if the minimal record does not need to be created.
	protected String getMinimalRecordComment(Publication<?> pub, List<PublicationContributor<?>> contributors) {
		String comment="";
		boolean isNumberedSeries = PubsUtils.isUsgsNumberedSeries(pub.getPublicationSubtype());
		if(contributors.isEmpty()){
			comment = "Minimal doi record shown due to publication having no Authors or Editors listed.";
		} else if(pub.getSeriesTitle() == null || pub.getSeriesTitle().getText() == null) {
			comment = "Minimal doi record shown due to publication missing series title.";
		} else if(isNumberedSeries && pub.getSeriesTitle().getOnlineIssn() == null) {
			comment = "Minimal doi record shown due to publication missing series online issn number.";
		}
		return comment;
	}

	protected Map<String, Object> makeModel(Publication<?> pub, List<PublicationContributor<?>> contributors) {
		Map<String, Object> model = new HashMap<>();
		model.put("pub", pub);
		boolean isNumberedSeries = PubsUtils.isUsgsNumberedSeries(pub.getPublicationSubtype());
		model.put("isNumberedSeries", isNumberedSeries);

		model.put("warehousePage", pubBusService.getWarehousePage(pub));

		model.put("pubContributors", contributors);

		model.put("authorKey", ContributorType.AUTHORS);
		model.put("editorKey", ContributorType.EDITORS);
		model.put("compilerKey", ContributorType.COMPILERS);
		return model;
	}

	protected String wrapInComment(String message) {
		return "<!-- " + StringEscapeUtils.escapeXml11(message) + " -->\n";
	}

	/**
	 * Writes the given string as an XML comment
	 * @param message
	 * @throws IOException 
	 */
	protected void writeComment(String message) throws IOException {
		//add error message as a comment to the xml document
		bufferedWriter.append(wrapInComment(message));
	}

	protected String getPubIdMsg(Publication<?> pub) {
		return String.format("indexId: %s  doi: %s", pub.getIndexId(), pub.getDoi());
	}

	/**
	 * 
	 * @param pub
	 * @return a message that helps identify a problematic pub.
	 */
	protected String getExcludedErrorMessage(Publication<?> pub) {
		String message = "Excluded Problematic Publication";
		if (null != pub) {
			message += " with "  + getPubIdMsg(pub);
		}
		return message;
	}

	/** output the closing tags and close stuff as appropriate. */
	@Override
	public void end() {
		LOG.trace("writing crossref footer");
		Map<String, Object> model = new HashMap<>();
		try{
			writeModelToTemplate(model, "crossref/footer.ftlx");
			bufferedWriter.flush();
		} catch (IOException | TemplateException e ) {
			throw new RuntimeException("Error writing footer", e);
		} finally {
			closeQuietly(bufferedWriter);
			closeQuietly(streamWriter);
		}
	}

	/**
	 *
	 * @param model
	 * @param templatePath a classpath relative path
	 * @throws java.io.IOException
	 * @throws freemarker.template.TemplateException
	 */
	protected void writeModelToTemplate(Map<?, ?> model, String templatePath) throws IOException, TemplateException{
		Template t;
		try {
			t = this.templateConfiguration.getTemplate(templatePath);
		} catch (IOException ex) {
			throw new RuntimeException("Error loading template", ex);
		}
		/**
		 * We only want to include reports that are successfully
		 * transformed into Crossref in the output. Thankfully, each
		 * report is small, so we can write each to its own in-memory 
		 * buffer. If the transformation is successful, then the result
		 * is written to the main output. If not, the caller is 
		 * responsible for handling the error.
		*/
		ByteArrayInputStream bais = null;
		try(
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			OutputStreamWriter reportWriter = new OutputStreamWriter(baos)
		) {
			t.process(model, reportWriter);
			bais = new ByteArrayInputStream(baos.toByteArray());
			IOUtils.copy(bais, bufferedWriter, PubsConstantsHelper.DEFAULT_ENCODING);
		} finally {
			closeQuietly(bais);
		}
	}

	protected List<PublicationContributor<?>> getContributors(Publication<?> pub) {
		List<PublicationContributor<?>> rtn = new ArrayList<>();
		//This process requires that the contributors are in rank order.
		//And that the contributor is valid.
		if (null != pub && null != pub.getContributors() && !pub.getContributors().isEmpty()) {
			Map<String, List<PublicationContributor<?>>> contributors = pub.getContributorsToMap();
			List<PublicationContributor<?>> authors = contributors.get(authorKey);
			if (null != authors && !authors.isEmpty()) {
				rtn.addAll(authors);
			}
			List<PublicationContributor<?>> editors = contributors.get(editorKey);
			if (null != editors && !editors.isEmpty()) {
				rtn.addAll(editors);
			}
		}
		return rtn;
	}

	protected void closeQuietly(final Closeable closeable) {
		// per IOUTILS - deprecated with no replacement -  Please use the try-with-resources statement or handle suppressed exceptions manually.
		try {
			if (closeable != null) {
				closeable.close();
			}
		} catch (final IOException ioe) {
			// ignore
		}
	}
}
