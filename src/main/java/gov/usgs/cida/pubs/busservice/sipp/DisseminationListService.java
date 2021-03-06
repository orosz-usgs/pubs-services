package gov.usgs.cida.pubs.busservice.sipp;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import gov.usgs.cida.pubs.SeverityLevel;
import gov.usgs.cida.pubs.busservice.intfc.ISippProcess;
import gov.usgs.cida.pubs.domain.ProcessType;
import gov.usgs.cida.pubs.domain.mp.MpPublication;
import gov.usgs.cida.pubs.domain.sipp.IpdsBureauApproval;
import gov.usgs.cida.pubs.domain.sipp.SippProcessLog;

@Service
public class DisseminationListService {
	private static final Logger LOG = LoggerFactory.getLogger(DisseminationListService.class);

	private final ISippProcess sippProcess;

	@Autowired
	DisseminationListService(final ISippProcess sippProcess) {
		this.sippProcess = sippProcess;
	}

	@Transactional
	public void processDisseminationList(final int daysLastDisseminated) {
		SippProcessLog sippProcessLog = logProcessStart();

		int additions = 0;
		int errors = 0;
		StringBuilder processingDetails = new StringBuilder();

		List<IpdsBureauApproval> ipdsBureauApprovals = IpdsBureauApproval.getDao().getIpdsBureauApprovals(daysLastDisseminated);

		for (IpdsBureauApproval ipdsBureauApproval : ipdsBureauApprovals) {
			try {
				MpPublication pub = sippProcess.processInformationProduct(ProcessType.DISSEMINATION, ipdsBureauApproval.getIpNumber());

				ProcessSummary processSummary = buildPublicationProcessSummary(ipdsBureauApproval.getIpNumber(), pub);
				processingDetails.append(processSummary.getProcessingDetails());
				additions = additions + processSummary.getAdditions();
				errors = errors + processSummary.getErrors();
			} catch (Exception e) {
				String errMess = String.format("Error processing IPNumber '%s': %s",
					ipdsBureauApproval.getIpNumber(), e.getMessage());
				LOG.error(errMess + String.format(" [Exception: %s]", e.getClass().getName()), e);
				processingDetails.append("\n").append(errMess);
				errors = errors + 1;
			}
		}
		logProcessEnd(sippProcessLog, ipdsBureauApprovals.size(), additions, errors, processingDetails);

	}

	protected ProcessSummary buildPublicationProcessSummary(String ipNumber, MpPublication mpPublication) {
		ProcessSummary processSummary = new ProcessSummary();
		StringBuilder details = new StringBuilder("\n\n(").append(ipNumber).append(")");
		if (mpPublication.isValid()) {
			processSummary.setAdditions(1);
			details.append(" added to MyPubs as ProdId: ").append(mpPublication.getId());
		} else {
			if (SeverityLevel.FATAL == mpPublication.getValidationErrors().maxSeverityLevel()) {
				processSummary.setErrors(1);
			}
			details.append(" not added:");
		}
		if (!mpPublication.getValidationErrors().isEmpty()) {
			details.append("\n\t").append(mpPublication.getValidationErrors().toString().replaceAll("\n", "\n\t"));
		}
		processSummary.setProcessingDetails(details.toString());
		return processSummary;
	}

	protected SippProcessLog logProcessStart() {
		SippProcessLog sippProcessLog = new SippProcessLog();
		sippProcessLog.setProcessType(ProcessType.DISSEMINATION);
		sippProcessLog.setId(SippProcessLog.getDao().add(sippProcessLog));
		return sippProcessLog;
	}

	protected void logProcessEnd(SippProcessLog sippProcessLog, int totalEntries, int additions, int errors, StringBuilder processingDetails) {
		sippProcessLog.setTotalEntries(totalEntries);
		sippProcessLog.setPublicationsAdded(additions);
		sippProcessLog.setErrorsEncountered(errors);
		sippProcessLog.setProcessingDetails(processingDetails.toString());
		SippProcessLog.getDao().update(sippProcessLog);
	}
}
