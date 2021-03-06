package gov.usgs.cida.pubs.domain.sipp;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

import gov.usgs.cida.pubs.dao.sipp.InformationProductDao;
import gov.usgs.cida.pubs.domain.PublicationSeries;
import gov.usgs.cida.pubs.domain.PublicationSubtype;
import gov.usgs.cida.pubs.domain.PublicationType;
import io.swagger.v3.oas.annotations.media.Schema;

@Component
public class InformationProduct {
	private static InformationProductDao informationProductDao;

	@JsonProperty("IPNumber")
	private String ipNumber;
	@JsonProperty("Abstract")
	private String abstractText;
	@JsonProperty("BasisNumber")
	private String basisNumber;
	@JsonProperty("CostCenter")
	private String costCenter;
	@JsonProperty("IPPANumber")
	private String ippaNumber;
	@JsonProperty("Cooperators")
	private String cooperators;
	@JsonProperty("EditionNumber")
	private String editionNumber;
	@JsonProperty("FinalTitle")
	private String finalTitle;
	@JsonProperty("JournalTitle")
	private String journalTitle;
	@JsonProperty("NumberOfMapsOrPlates")
	private String numberOfMapsOrPlates;
	@JsonProperty("PageRange")
	private String pageRange;
	@JsonProperty("PhysicalDescription")
	private String physicalDescription;
	@JsonProperty("PlannedDisseminationDate")
	private String plannedDisseminationDate;
	@JsonProperty("SupersedesIPNumber")
	private String supersedesIPNumber;
	@JsonProperty("TeamProjectName")
	private String teamProjectName;
	@JsonProperty("VISpecialist")
	private String viSpecialist;
	@JsonProperty("Volume")
	private String volume;
	@JsonProperty("WorkingTitle")
	private String workingTitle;
	@JsonProperty("USGSSeriesNumber")
	private String usgsSeriesNumber;
	@JsonProperty("USGSRegion")
	private String usgsRegion;
	@JsonProperty("USGSProgram")
	private String usgsProgram;
	@JsonProperty("USGSSeriesType")
	private String usgsSeriesType;
	@JsonProperty("SeniorUSGSAuthor")
	private String seniorUSGSAuthor;
	@JsonProperty("LocationOfSupportingData")
	private String locationOfSupportingData;
	@JsonProperty("PSCChief")
	private String pscChief;
	@JsonProperty("PublishedURL")
	private String publishedURL;
	@JsonProperty("CostCenterChief")
	private String costCenterChief;
	@JsonProperty("AuthorsSupervisor")
	private String authorsSupervisor;
	@JsonProperty("BureauApprovingOfficial")
	private String bureauApprovingOfficial;
	@JsonProperty("Citation")
	private String citation;
	@JsonProperty("DigitalObjectIdentifier")
	private String digitalObjectIdentifier;
	@JsonProperty("InterpretivePublication")
	private String interpretivePublication;
	@JsonProperty("Issue")
	private String issue;
	@JsonProperty("ProductType")
	private String productType;
	@JsonProperty("DataManagementPlan")
	private String dataManagementPlan;
	@JsonProperty("USGSFunded")
	private String usgsFunded;
	@JsonProperty("USGSMissionArea")
	private String usgsMissionArea;
	@JsonProperty("ProductSummary")
	private String productSummary;
	@JsonProperty("TaskAssignedTo")
	private String taskAssignedTo;
	@JsonProperty("NonUSGSPublisher")
	private String nonUSGSPublisher;
	@JsonProperty("RelatedIPNumber")
	private String relatedIPNumber;
	@JsonProperty("USGSSeriesLetter")
	private String usgsSeriesLetter;
	@JsonProperty("Task")
	private String task;
	@JsonProperty("TaskStartDate")
	private String taskStartDate;
	@JsonProperty("GeologicalNames")
	private String geologicalNames;
	@JsonProperty("Editor")
	private String editor;
	@JsonProperty("PublishingServiceCenter")
	private String publishingServiceCenter;
	@JsonProperty("Created")
	private String created;
	@JsonProperty("CreatedBy")
	private String createdBy;
	@JsonProperty("Modified")
	private String modified;
	@JsonProperty("ModifiedBy")
	private String modifiedBy;
	@JsonProperty("Authors")
	private List<Author> authors;
	@JsonProperty("USGSPrograms")
	private List<USGSProgram> usgsPrograms;
	@JsonProperty("SpecialProductAlerts")
	private List<SpecialProductAlert> specialProductAlerts;
	@JsonProperty("Notes")
	private List<Note> notes;
	@JsonProperty("Reviewers")
	private List<Reviewer> reviewers;
	@JsonProperty("TaskHistory")
	private List<Task> taskHistory;
	private PublicationType publicationType;
	private PublicationSubtype publicationSubtype;
	private PublicationSeries usgsSeriesTitle;
	private boolean usgsNumberedSeries;
	private String indexId;
	private boolean usgsPeriodical;
	public String getIpNumber() {
		return StringUtils.trimToNull(ipNumber);
	}
	public void setIpNumber(String ipNumber) {
		this.ipNumber = ipNumber;
	}
	public String getAbstractText() {
		return StringUtils.trimToNull(abstractText);
	}
	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}
	public String getBasisNumber() {
		return StringUtils.trimToNull(basisNumber);
	}
	public void setBasisNumber(String basisNumber) {
		this.basisNumber = basisNumber;
	}
	public String getCostCenter() {
		return StringUtils.trimToNull(costCenter);
	}
	public void setCostCenter(String costCenter) {
		this.costCenter = costCenter;
	}
	public String getIppaNumber() {
		return StringUtils.trimToNull(ippaNumber);
	}
	public void setIppaNumber(String ippaNumber) {
		this.ippaNumber = ippaNumber;
	}
	public String getCooperators() {
		return StringUtils.trimToNull(cooperators);
	}
	public void setCooperators(String cooperators) {
		this.cooperators = cooperators;
	}
	public String getEditionNumber() {
		return StringUtils.trimToNull(editionNumber);
	}
	public void setEditionNumber(String editionNumber) {
		this.editionNumber = editionNumber;
	}
	public String getFinalTitle() {
		return StringUtils.trimToNull(finalTitle);
	}
	public void setFinalTitle(String finalTitle) {
		this.finalTitle = finalTitle;
	}
	public String getJournalTitle() {
		return StringUtils.trimToNull(journalTitle);
	}
	public void setJournalTitle(String journalTitle) {
		this.journalTitle = journalTitle;
	}
	public String getNumberOfMapsOrPlates() {
		return StringUtils.trimToNull(numberOfMapsOrPlates);
	}
	public void setNumberOfMapsOrPlates(String numberOfMapsOrPlates) {
		this.numberOfMapsOrPlates = numberOfMapsOrPlates;
	}
	public String getPageRange() {
		return StringUtils.trimToNull(pageRange);
	}
	public void setPageRange(String pageRange) {
		this.pageRange = pageRange;
	}
	public String getPhysicalDescription() {
		return StringUtils.trimToNull(physicalDescription);
	}
	public void setPhysicalDescription(String physicalDescription) {
		this.physicalDescription = physicalDescription;
	}
	public String getPlannedDisseminationDate() {
		return StringUtils.trimToNull(plannedDisseminationDate);
	}
	public void setPlannedDisseminationDate(String plannedDisseminationDate) {
		this.plannedDisseminationDate = plannedDisseminationDate;
	}
	public String getSupersedesIPNumber() {
		return StringUtils.trimToNull(supersedesIPNumber);
	}
	public void setSupersedesIPNumber(String supersedesIPNumber) {
		this.supersedesIPNumber = supersedesIPNumber;
	}
	public String getTeamProjectName() {
		return StringUtils.trimToNull(teamProjectName);
	}
	public void setTeamProjectName(String teamProjectName) {
		this.teamProjectName = teamProjectName;
	}
	public String getViSpecialist() {
		return StringUtils.trimToNull(viSpecialist);
	}
	public void setViSpecialist(String viSpecialist) {
		this.viSpecialist = viSpecialist;
	}
	public String getVolume() {
		return StringUtils.trimToNull(volume);
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public String getWorkingTitle() {
		return StringUtils.trimToNull(workingTitle);
	}
	public void setWorkingTitle(String workingTitle) {
		this.workingTitle = workingTitle;
	}
	public String getUsgsSeriesNumber() {
		return StringUtils.trimToNull(usgsSeriesNumber);
	}
	public void setUsgsSeriesNumber(String usgsSeriesNumber) {
		this.usgsSeriesNumber = usgsSeriesNumber;
	}
	public String getUsgsRegion() {
		return StringUtils.trimToNull(usgsRegion);
	}
	public void setUsgsRegion(String usgsRegion) {
		this.usgsRegion = usgsRegion;
	}
	public String getUsgsProgram() {
		return StringUtils.trimToNull(usgsProgram);
	}
	public void setUsgsProgram(String usgsProgram) {
		this.usgsProgram = usgsProgram;
	}
	public String getUsgsSeriesType() {
		return StringUtils.trimToNull(usgsSeriesType);
	}
	public void setUsgsSeriesType(String usgsSeriesType) {
		this.usgsSeriesType = usgsSeriesType;
	}
	public String getSeniorUSGSAuthor() {
		return StringUtils.trimToNull(seniorUSGSAuthor);
	}
	public void setSeniorUSGSAuthor(String seniorUSGSAuthor) {
		this.seniorUSGSAuthor = seniorUSGSAuthor;
	}
	public String getLocationOfSupportingData() {
		return StringUtils.trimToNull(locationOfSupportingData);
	}
	public void setLocationOfSupportingData(String locationOfSupportingData) {
		this.locationOfSupportingData = locationOfSupportingData;
	}
	public String getPscChief() {
		return StringUtils.trimToNull(pscChief);
	}
	public void setPscChief(String pscChief) {
		this.pscChief = pscChief;
	}
	public String getPublishedURL() {
		return StringUtils.trimToNull(publishedURL);
	}
	public void setPublishedURL(String publishedURL) {
		this.publishedURL = publishedURL;
	}
	public String getCostCenterChief() {
		return StringUtils.trimToNull(costCenterChief);
	}
	public void setCostCenterChief(String costCenterChief) {
		this.costCenterChief = costCenterChief;
	}
	public String getAuthorsSupervisor() {
		return StringUtils.trimToNull(authorsSupervisor);
	}
	public void setAuthorsSupervisor(String authorsSupervisor) {
		this.authorsSupervisor = authorsSupervisor;
	}
	public String getBureauApprovingOfficial() {
		return StringUtils.trimToNull(bureauApprovingOfficial);
	}
	public void setBureauApprovingOfficial(String bureauApprovingOfficial) {
		this.bureauApprovingOfficial = bureauApprovingOfficial;
	}
	public String getCitation() {
		return StringUtils.trimToNull(citation);
	}
	public void setCitation(String citation) {
		this.citation = citation;
	}
	public String getDigitalObjectIdentifier() {
		return StringUtils.trimToNull(digitalObjectIdentifier);
	}
	public void setDigitalObjectIdentifier(String digitalObjectIdentifier) {
		this.digitalObjectIdentifier = digitalObjectIdentifier;
	}
	public String getInterpretivePublication() {
		return StringUtils.trimToNull(interpretivePublication);
	}
	public void setInterpretivePublication(String interpretivePublication) {
		this.interpretivePublication = interpretivePublication;
	}
	public String getIssue() {
		return StringUtils.trimToNull(issue);
	}
	public void setIssue(String issue) {
		this.issue = issue;
	}
	public String getProductType() {
		return StringUtils.trimToNull(productType);
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getDataManagementPlan() {
		return StringUtils.trimToNull(dataManagementPlan);
	}
	public void setDataManagementPlan(String dataManagementPlan) {
		this.dataManagementPlan = dataManagementPlan;
	}
	public String getUsgsFunded() {
		return StringUtils.trimToNull(usgsFunded);
	}
	public void setUsgsFunded(String usgsFunded) {
		this.usgsFunded = usgsFunded;
	}
	public String getUsgsMissionArea() {
		return StringUtils.trimToNull(usgsMissionArea);
	}
	public void setUsgsMissionArea(String usgsMissionArea) {
		this.usgsMissionArea = usgsMissionArea;
	}
	public String getProductSummary() {
		return StringUtils.trimToNull(productSummary);
	}
	public void setProductSummary(String productSummary) {
		this.productSummary = productSummary;
	}
	public String getTaskAssignedTo() {
		return StringUtils.trimToNull(taskAssignedTo);
	}
	public void setTaskAssignedTo(String taskAssignedTo) {
		this.taskAssignedTo = taskAssignedTo;
	}
	public String getNonUSGSPublisher() {
		return StringUtils.trimToNull(nonUSGSPublisher);
	}
	public void setNonUSGSPublisher(String nonUSGSPublisher) {
		this.nonUSGSPublisher = nonUSGSPublisher;
	}
	public String getRelatedIPNumber() {
		return StringUtils.trimToNull(relatedIPNumber);
	}
	public void setRelatedIPNumber(String relatedIPNumber) {
		this.relatedIPNumber = relatedIPNumber;
	}
	public String getUsgsSeriesLetter() {
		return StringUtils.trimToNull(usgsSeriesLetter);
	}
	public void setUsgsSeriesLetter(String usgsSeriesLetter) {
		this.usgsSeriesLetter = usgsSeriesLetter;
	}
	public String getTask() {
		return StringUtils.trimToNull(task);
	}
	public void setTask(String task) {
		this.task = task;
	}
	public String getTaskStartDate() {
		return StringUtils.trimToNull(taskStartDate);
	}
	public void setTaskStartDate(String taskStartDate) {
		this.taskStartDate = taskStartDate;
	}
	public String getGeologicalNames() {
		return StringUtils.trimToNull(geologicalNames);
	}
	public void setGeologicalNames(String geologicalNames) {
		this.geologicalNames = geologicalNames;
	}
	public String getEditor() {
		return StringUtils.trimToNull(editor);
	}
	public void setEditor(String editor) {
		this.editor = editor;
	}
	public String getPublishingServiceCenter() {
		return StringUtils.trimToNull(publishingServiceCenter);
	}
	public void setPublishingServiceCenter(String publishingServiceCenter) {
		this.publishingServiceCenter = publishingServiceCenter;
	}
	public String getCreated() {
		return StringUtils.trimToNull(created);
	}
	public void setCreated(String created) {
		this.created = created;
	}
	public String getCreatedBy() {
		return StringUtils.trimToNull(createdBy);
	}
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	public String getModified() {
		return StringUtils.trimToNull(modified);
	}
	public void setModified(String modified) {
		this.modified = modified;
	}
	public String getModifiedBy() {
		return StringUtils.trimToNull(modifiedBy);
	}
	public void setModifiedBy(String modifiedBy) {
		this.modifiedBy = modifiedBy;
	}
	public List<Author> getAuthors() {
		return authors;
	}
	public void setAuthors(List<Author> authors) {
		this.authors = authors;
	}
	public List<USGSProgram> getUsgsPrograms() {
		return usgsPrograms;
	}
	public void setUsgsPrograms(List<USGSProgram> usgsPrograms) {
		this.usgsPrograms = usgsPrograms;
	}
	public List<SpecialProductAlert> getSpecialProductAlerts() {
		return specialProductAlerts;
	}
	public void setSpecialProductAlerts(List<SpecialProductAlert> specialProductAlerts) {
		this.specialProductAlerts = specialProductAlerts;
	}
	public List<Note> getNotes() {
		return notes;
	}
	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}
	public List<Reviewer> getReviewers() {
		return reviewers;
	}
	public void setReviewers(List<Reviewer> reviewers) {
		this.reviewers = reviewers;
	}
	public List<Task> getTaskHistory() {
		return taskHistory;
	}
	public void setTaskHistory(List<Task> taskHistory) {
		this.taskHistory = taskHistory;
	}
	public PublicationType getPublicationType() {
		return publicationType;
	}
	public void setPublicationType(PublicationType publicationType) {
		this.publicationType = publicationType;
	}
	public PublicationSubtype getPublicationSubtype() {
		return publicationSubtype;
	}
	public void setPublicationSubtype(PublicationSubtype publicationSubtype) {
		this.publicationSubtype = publicationSubtype;
	}
	public PublicationSeries getUsgsSeriesTitle() {
		return usgsSeriesTitle;
	}
	public void setUsgsSeriesTitle(PublicationSeries usgsSeriesTitle) {
		this.usgsSeriesTitle = usgsSeriesTitle;
	}
	public boolean isUsgsNumberedSeries() {
		return usgsNumberedSeries;
	}
	public void setUsgsNumberedSeries(boolean usgsNumberedSeries) {
		this.usgsNumberedSeries = usgsNumberedSeries;
	}
	public String getIndexId() {
		return StringUtils.trimToNull(indexId);
	}
	public void setIndexId(String indexId) {
		this.indexId = indexId;
	}
	public boolean isUsgsPeriodical() {
		return usgsPeriodical;
	}
	public void setUsgsPeriodical(boolean usgsPeriodical) {
		this.usgsPeriodical = usgsPeriodical;
	}
	public static InformationProductDao getDao() {
		return informationProductDao;
	}
	@Autowired
	@Qualifier("informationProductDao")
	@Schema(hidden = true)
	public void setInformationProductDao(InformationProductDao inInformationProductDao) {
		informationProductDao = inInformationProductDao;
	}
}
