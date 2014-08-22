package gov.usgs.cida.pubs.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.usgs.cida.pubs.domain.Affiliation;
import gov.usgs.cida.pubs.domain.OutsideAffiliation;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author drsteini
 *
 */
public class OutsideAffiliationDaoTest extends BaseSpringDaoTest {

    public static final int outsideAffiliationCnt = 3;

    public static final List<String> IGNORE_PROPERTIES = Arrays.asList("validationErrors", "valErrors", "ipdsId");

    @Test
    public void getByIdInteger() {
        Affiliation<?> outsideAffiliation = OutsideAffiliation.getDao().getById(5);
        AffiliationDaoTest.assertAffiliation5(outsideAffiliation);
    }

    @Test
    public void getByIdString() {
        Affiliation<?> outsideAffiliation = OutsideAffiliation.getDao().getById("5");
        AffiliationDaoTest.assertAffiliation5(outsideAffiliation);
    }

    @Test
    public void getByMap() {
        List<Affiliation<?>> outsideAffiliations = OutsideAffiliation.getDao().getByMap(null);
        assertEquals(outsideAffiliationCnt, outsideAffiliations.size());

        Map<String, Object> filters = new HashMap<>();
        filters.put("id", "5");
        outsideAffiliations = OutsideAffiliation.getDao().getByMap(filters);
        assertEquals(1, outsideAffiliations.size());
        AffiliationDaoTest.assertAffiliation5(outsideAffiliations.get(0));

        filters.clear();
        filters.put("name", "out");
        outsideAffiliations = OutsideAffiliation.getDao().getByMap(filters);
        assertEquals(3, outsideAffiliations.size());

        filters.clear();
        filters.put("active", false);
        outsideAffiliations = OutsideAffiliation.getDao().getByMap(filters);
        assertEquals(1, outsideAffiliations.size());

        filters.clear();
        filters.put("active", true);
        outsideAffiliations = OutsideAffiliation.getDao().getByMap(filters);
        assertEquals(2, outsideAffiliations.size());

        filters.clear();
        filters.put("usgs", true);
        outsideAffiliations = OutsideAffiliation.getDao().getByMap(filters);
        assertEquals(0, outsideAffiliations.size());

        filters.clear();
        filters.put("usgs", false);
        outsideAffiliations = OutsideAffiliation.getDao().getByMap(filters);
        assertEquals(3, outsideAffiliations.size());

        filters.put("id", "5");
        filters.put("name", "out");
        filters.put("active", true);
        outsideAffiliations = OutsideAffiliation.getDao().getByMap(filters);
        assertEquals(1, outsideAffiliations.size());
    }

    @Test
    public void addUpdateTest() {
        OutsideAffiliation affiliation = new OutsideAffiliation();
        affiliation.setName("outside org 1");
        OutsideAffiliation.getDao().add(affiliation);
        OutsideAffiliation persistedAffiliation = (OutsideAffiliation) OutsideAffiliation.getDao().getById(affiliation.getId());
        assertDaoTestResults(OutsideAffiliation.class, affiliation, persistedAffiliation, IGNORE_PROPERTIES, true, true);

        affiliation.setName("outside org 2");
        OutsideAffiliation.getDao().update(affiliation);
        persistedAffiliation = (OutsideAffiliation) OutsideAffiliation.getDao().getById(affiliation.getId());
        assertDaoTestResults(OutsideAffiliation.class, affiliation, persistedAffiliation, IGNORE_PROPERTIES, true, true);
    }

    @Test
    public void notImplemented() {
        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("prodId", 1);
            OutsideAffiliation.getDao().getObjectCount(params);
            fail("Was able to get count.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            OutsideAffiliation.getDao().delete(new OutsideAffiliation());
            fail("Was able to delete.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            OutsideAffiliation.getDao().deleteById(1);
            fail("Was able to delete by it.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }
    }

}
