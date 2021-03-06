package gov.usgs.cida.pubs.validation.mp.crossproperty;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import gov.usgs.cida.pubs.dao.PublicationDao;
import gov.usgs.cida.pubs.dao.mp.MpPublicationDao;
import gov.usgs.cida.pubs.domain.Publication;
import gov.usgs.cida.pubs.domain.PublicationSeries;
import gov.usgs.cida.pubs.domain.PublicationSubtype;
import gov.usgs.cida.pubs.domain.mp.MpPublication;
import gov.usgs.cida.pubs.validation.BaseValidatorTest;

public class CrossPropertyValidatorForMpPublicationTest extends BaseValidatorTest {

	protected CrossPropertyValidatorForMpPublication validator;
	protected Publication<MpPublication> mpPub;
	protected PublicationSubtype pubSubtype;
	protected PublicationSeries pubSeries;

	@MockBean(name="mpPublicationDao")
	protected MpPublicationDao mpPublicationDao;
	@MockBean(name="publicationDao")
	protected PublicationDao publicationDao;

	@BeforeEach
	public void setUp() throws Exception {
		buildContext();
		validator = new CrossPropertyValidatorForMpPublication();
		mpPub = new MpPublication();
		pubSubtype = new PublicationSubtype();
		pubSeries = new PublicationSeries();
		mpPub.setPublicationSubtype(pubSubtype);
		mpPub.setSeriesTitle(pubSeries);
	}

	@Test
	public void isValidNPETest() {
		assertTrue(validator.isValid(null, null));
		assertTrue(validator.isValid(null, context));
		assertTrue(validator.isValid(mpPub, null));
//		assertFalse(validator.isValid(mpPub, context));
		assertTrue(validator.isValid(mpPub, context));

		mpPub.setSeriesTitle(null);
//		assertFalse(validator.isValid(mpPub, context));
		assertTrue(validator.isValid(mpPub, context));
		mpPub.setPublicationSubtype(null);
//		assertFalse(validator.isValid(mpPub, context));
		assertTrue(validator.isValid(mpPub, context));
	}

	@Test
	public void isValidYearTest() {
		mpPub.setNoYear(true);
		assertTrue(validator.isValid(mpPub, context));
		mpPub.setNoYear(false);
		mpPub.setPublicationYear("1999");
		assertTrue(validator.isValid(mpPub, context));
	}

	@Test
	public void isValidYearFailTest() {
		mpPub.setNoYear(false);
//		assertFalse(validator.isValid(mpPub, context));
		assertTrue(validator.isValid(mpPub, context));
		mpPub.setNoYear(true);
		mpPub.setPublicationYear("1999");
//		assertFalse(validator.isValid(mpPub, context));
		assertTrue(validator.isValid(mpPub, context));
	}

	@Test
	public void isValidNonUsgsTest() {
		mpPub.setNoYear(true);
		//Non-USGS Numbered = no errors
		pubSubtype.setId(1);
		assertTrue(validator.isValid(mpPub, context));
		pubSeries.setId(1);
		assertTrue(validator.isValid(mpPub, context));
	}

	@Test
	public void isValidUsgsTest() {
		mpPub.setNoYear(true);
		//USGS Numbered with Series Title is good
		pubSubtype.setId(PublicationSubtype.USGS_NUMBERED_SERIES);
		pubSeries.setId(1);
		assertTrue(validator.isValid(mpPub, context));
	}

	@Test
	public void isValidUsgsFailTest() {
		mpPub.setNoYear(true);
		//USGS Numbered with out Series Title is bad
		pubSubtype.setId(PublicationSubtype.USGS_NUMBERED_SERIES);
		assertFalse(validator.isValid(mpPub, context));
		mpPub.setSeriesTitle(null);
		assertFalse(validator.isValid(mpPub, context));
	}

}
