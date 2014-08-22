package gov.usgs.cida.pubs.dao;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;
import gov.usgs.cida.pubs.domain.PublicationSubtype;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

/**
 * @author drsteini
 *
 */
public class PublicationSubtypeDaoTest extends BaseDaoTest {

    public static final int pubSubTypeCnt = 30;

    @Test
    public void getByIdInteger() {
        PublicationSubtype pubSubtype = PublicationSubtype.getDao().getById(5);
        assertEquals(5, pubSubtype.getId().intValue());
        assertEquals(18, pubSubtype.getPublicationType().getId().intValue());
        assertEquals("USGS Numbered Series", pubSubtype.getName());
    }

    @Test
    public void getByIdString() {
        PublicationSubtype pubSubtype = PublicationSubtype.getDao().getById("6");
        assertEquals(6, pubSubtype.getId().intValue());
        assertEquals(18, pubSubtype.getPublicationType().getId().intValue());
        assertEquals("USGS Unnumbered Series", pubSubtype.getName());
    }

    @Test
    public void getByMap() {
        List<PublicationSubtype> pubSubtypes = PublicationSubtype.getDao().getByMap(null);
        assertEquals(pubSubTypeCnt, pubSubtypes.size());

        Map<String, Object> filters = new HashMap<>();
        filters.put("id", "10");
        pubSubtypes = PublicationSubtype.getDao().getByMap(filters);
        assertEquals(1, pubSubtypes.size());
        assertEquals(10, pubSubtypes.get(0).getId().intValue());
        assertEquals(2, pubSubtypes.get(0).getPublicationType().getId().intValue());
        assertEquals("Journal Article", pubSubtypes.get(0).getName());

        filters.clear();
        filters.put("publicationTypeId", "4");
        pubSubtypes = PublicationSubtype.getDao().getByMap(filters);
        assertEquals(5, pubSubtypes.size());
        for (PublicationSubtype pubSubtype : pubSubtypes) {
            assertEquals(4, pubSubtype.getPublicationType().getId().intValue());
        }

        filters.put("name", "hand");
        pubSubtypes = PublicationSubtype.getDao().getByMap(filters);
        assertEquals(1, pubSubtypes.size());
        assertEquals(13, pubSubtypes.get(0).getId().intValue());
        assertEquals(4, pubSubtypes.get(0).getPublicationType().getId().intValue());
        assertEquals("Handbook", pubSubtypes.get(0).getName());
    }

    @Test
    public void notImplemented() {
        try {
            PublicationSubtype.getDao().add(new PublicationSubtype());
            fail("Was able to add.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            Map<String, Object> params = new HashMap<String, Object>();
            params.put("prodId", 1);
            PublicationSubtype.getDao().getObjectCount(params);
            fail("Was able to get count.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            PublicationSubtype.getDao().update(new PublicationSubtype());
            fail("Was able to update.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            PublicationSubtype.getDao().delete(new PublicationSubtype());
            fail("Was able to delete.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }

        try {
            PublicationSubtype.getDao().deleteById(1);
            fail("Was able to delete by it.");
        } catch (Exception e) {
            assertEquals("NOT IMPLEMENTED.", e.getMessage());
        }
    }

}