package gov.usgs.cida.pubs.domain;

import gov.usgs.cida.pubs.json.View;
import gov.usgs.cida.pubs.utility.PubsUtils;
import gov.usgs.cida.pubs.validation.constraint.ParentExists;
import gov.usgs.cida.pubs.validation.constraint.UniqueKey;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonUnwrapped;
import com.fasterxml.jackson.annotation.JsonView;

@UniqueKey(message = "{publication.indexid.duplicate}")
@ParentExists
public class PublicationCostCenter<D> extends BaseDomain<D> implements Serializable {
	private static final long serialVersionUID = -1839682568695179903L;

	@JsonIgnore
	private Integer publicationId;

	@JsonView(View.PW.class)
	@JsonUnwrapped
	private CostCenter costCenter;

	public Integer getPublicationId() {
		return publicationId;
	}

	public void setPublicationId(final Integer inPublicationId) {
		publicationId = inPublicationId;
	}

	public void setPublicationId(final String inPublicationId) {
		publicationId = PubsUtils.parseInteger(inPublicationId);
	}

	@NotNull
	public CostCenter getCostCenter() {
		return costCenter;
	}

	public void setCostCenter(final CostCenter inCostCenter) {
		costCenter = inCostCenter;
	}

	//We don't want the publicationCostCenter.id in the json view
	@JsonIgnore
	@Override
	public Integer getId() {
		return id;
	}

	//We also don't want to deserialize the costCenter.id to the publicationCostCenter.id
	@JsonIgnore
	@Override
	public void setId(final String inId) {
		id = PubsUtils.parseInteger(inId);
	}
}