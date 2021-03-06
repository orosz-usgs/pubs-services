package gov.usgs.cida.pubs.webservice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;

import gov.usgs.cida.pubs.PubsConstantsHelper;
import gov.usgs.cida.pubs.busservice.intfc.IBusService;
import gov.usgs.cida.pubs.domain.Contributor;
import gov.usgs.cida.pubs.domain.CorporateContributor;
import gov.usgs.cida.pubs.domain.OutsideContributor;
import gov.usgs.cida.pubs.domain.PersonContributor;
import gov.usgs.cida.pubs.domain.UsgsContributor;
import gov.usgs.cida.pubs.json.View;
import gov.usgs.cida.pubs.utility.PubsUtils;
import gov.usgs.cida.pubs.validation.ValidatorResult;
import gov.usgs.cida.pubs.validation.constraint.ManagerChecks;

@RestController
@RequestMapping(produces=PubsConstantsHelper.MEDIA_TYPE_APPLICATION_JSON_UTF8_VALUE)
public class ContributorMvcService extends MvcService<Contributor<?>> {

	private static final Logger LOG = LoggerFactory.getLogger(ContributorMvcService.class);

	private IBusService<CorporateContributor> corporateContributorBusService;
	private IBusService<PersonContributor<?>> personContributorBusService;

	@Autowired
	public ContributorMvcService(@Qualifier("corporateContributorBusService") IBusService<CorporateContributor> corporateContributorBusService,
			@Qualifier("personContributorBusService") IBusService<PersonContributor<?>> personContributorBusService) {
		this.corporateContributorBusService = corporateContributorBusService;
		this.personContributorBusService = personContributorBusService;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@GetMapping(value={"/contributor/{id}"})
	@JsonView(View.PW.class)
	public @ResponseBody Contributor<?> getContributor(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		LOG.debug("getContributor");
		setHeaders(response);
		Contributor<?> rtn = personContributorBusService.getObject(PubsUtils.parseInteger(id));
		if (null == rtn) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return rtn;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@GetMapping(value={"/person/{id}"})
	@JsonView(View.PW.class)
	public @ResponseBody Contributor<?> getPerson(HttpServletRequest request, HttpServletResponse response, @PathVariable("id") String id) {
		LOG.debug("getPerson");
		setHeaders(response);
		Contributor<?> rtn = (Contributor<?>) personContributorBusService.getObject(PubsUtils.parseInteger(id));
		if (null == rtn) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return rtn;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@PostMapping(value={"/usgscontributor"})
	@JsonView(View.PW.class)
	@Transactional
	public @ResponseBody UsgsContributor createUsgsContributor(@RequestBody UsgsContributor person, HttpServletResponse response) {
		LOG.debug("createUsgsContributor");
		setHeaders(response);
		UsgsContributor result = (UsgsContributor) personContributorBusService.createObject(person, ManagerChecks.class);
		if (null != result && result.isValid()) {
			response.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return result;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@PutMapping(value="/usgscontributor/{id}")
	@JsonView(View.PW.class)
	@Transactional
	public @ResponseBody UsgsContributor updateUsgsContributor(@RequestBody UsgsContributor person, @PathVariable("id") String id, HttpServletResponse response) {
		LOG.debug("updateUsgsContributor");
		setHeaders(response);

		UsgsContributor result = person;
		ValidatorResult idNotMatched = PubsUtils.validateIdsMatch(id, person);

		if (null == idNotMatched) {
			result = (UsgsContributor) personContributorBusService.updateObject(person, ManagerChecks.class);
		} else {
			result.addValidatorResult(idNotMatched);
		}

		if (null != result && result.isValid()) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return result;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@PostMapping(value={"/outsidecontributor"})
	@JsonView(View.PW.class)
	@Transactional
	public @ResponseBody OutsideContributor createOutsideContributor(@RequestBody OutsideContributor person,
			HttpServletResponse response) {
		LOG.debug("createOutsideContributor");
		setHeaders(response);
		OutsideContributor result = (OutsideContributor) personContributorBusService.createObject(person, ManagerChecks.class);
		if (null != result && result.isValid()) {
			response.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return result;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@PutMapping(value="/outsidecontributor/{id}")
	@JsonView(View.PW.class)
	@Transactional
	public @ResponseBody OutsideContributor updateOutsideContributor(@RequestBody OutsideContributor person, @PathVariable String id, HttpServletResponse response) {
		LOG.debug("updateOutsideContributor");
		setHeaders(response);

		OutsideContributor result = person;
		ValidatorResult idNotMatched = PubsUtils.validateIdsMatch(id, person);

		if (null == idNotMatched) {
			result = (OutsideContributor) personContributorBusService.updateObject(person, ManagerChecks.class);
		} else {
			result.addValidatorResult(idNotMatched);
		}

		if (null != result && result.isValid()) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return result;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@GetMapping(value={"/corporation/{id}"})
	@JsonView(View.PW.class)
	public @ResponseBody CorporateContributor getCorporation(HttpServletRequest request, HttpServletResponse response,
			@PathVariable("id") String id) {
		LOG.debug("getCorporation");
		setHeaders(response);
		CorporateContributor rtn = corporateContributorBusService.getObject(PubsUtils.parseInteger(id));
		if (null == rtn) {
			response.setStatus(HttpStatus.NOT_FOUND.value());
		}
		return rtn;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@PostMapping(value={"/corporation"})
	@JsonView(View.PW.class)
	@Transactional
	public @ResponseBody CorporateContributor createCorporation(@RequestBody CorporateContributor corporation, HttpServletResponse response) {
		LOG.debug("createCorporation");
		setHeaders(response);
		CorporateContributor result = corporateContributorBusService.createObject(corporation, ManagerChecks.class);
		if (null != result && result.isValid()) {
			response.setStatus(HttpServletResponse.SC_CREATED);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return result;
	}

//	@ApiOperation(value = "", authorizations = { @Authorization(value=PubsConstantsHelper.API_KEY_NAME) })
	@PutMapping(value="/corporation/{id}")
	@JsonView(View.PW.class)
	@Transactional
	public @ResponseBody CorporateContributor updateCorporation(@RequestBody CorporateContributor corporation, @PathVariable String id, HttpServletResponse response) {
		LOG.debug("updateCorporation");
		setHeaders(response);

		CorporateContributor result = corporation;
		ValidatorResult idNotMatched = PubsUtils.validateIdsMatch(id, corporation);

		if (null == idNotMatched) {
			result = (CorporateContributor) corporateContributorBusService.updateObject(corporation, ManagerChecks.class);
		} else {
			result.addValidatorResult(idNotMatched);
		}

		if (null != result && result.isValid()) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
		}
		return result;
	}
}
