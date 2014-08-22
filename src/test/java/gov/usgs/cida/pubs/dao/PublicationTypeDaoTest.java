package gov.usgs.cida.pubs.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.usgs.cida.pubs.domain.PublicationType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author drsteini
 *
 */
public class PublicationTypeDaoTest extends BaseDaoTest {

    public static final int pubTypeCnt = 11;

    @Test
    public void getByIdInteger() {
        PublicationType pubType = PublicationType.getDao().getById(2);
        assertEquals(2, pubType.getId().intValue());
        assertEquals("Article", pubType.getName());
    }

    @Test
    public void getByIdString() {
        PublicationType pubType = PublicationType.getDao().getById(PublicationType.REPORT);
        assertEquals(PublicationType.REPORT, pubType.getId());
        assertEquals("Report", pubType.getName());
    }

    @Test
    public void getByMap() {
        List<PublicationType> pubTypes = PublicationType.getDao().getByMap(null);
        assertEquals(pubTypeCnt, pubTypes.size());

        Map<String, Object> filters = new HashMap<>();
        filters.put("id", "16");
        pubTypes = PublicationType.getDao().getByMap(filters);
        assertEquals(1, pubTypes.size());
        assertEquals(16, pubTypes.get(0).getId().intValue());
        assertEquals("Patent", pubTypes.get(0).getName());

        filters.clear();
        filters.put("name", "p");
        pubTypes = PublicationType.getDao().getByMap(filters);
        assertEquals(2, pubTypes.size());
    }

    @Test
    public void notImplemented() {
        try {
            PublicationType.getDao().add(new PublicationType());
            fail("Was able to add.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("prodId", 1);
            PublicationType.getDao().getObjectCount(params);
            fail("Was able to get count.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            PublicationType.getDao().update(new PublicationType());
            fail("Was able to update.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            PublicationType.getDao().delete(new PublicationType());
            fail("Was able to delete.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            PublicationType.getDao().deleteById(1);
            fail("Was able to delete by it.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }
    }

}