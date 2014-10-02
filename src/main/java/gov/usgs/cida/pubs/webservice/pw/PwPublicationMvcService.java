package gov.usgs.cida.pubs.webservice.pw;

import gov.usgs.cida.pubs.busservice.intfc.IPwPublicationBusService;
import gov.usgs.cida.pubs.domain.SearchResults;
import gov.usgs.cida.pubs.domain.pw.PwPublication;
import gov.usgs.cida.pubs.json.ResponseView;
import gov.usgs.cida.pubs.json.view.intfc.IMpView;
import gov.usgs.cida.pubs.webservice.MvcService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "publication", produces="application/json")
public class PwPublicationMvcService  extends MvcService<PwPublication> {

    private final IPwPublicationBusService busService;

    @Autowired
    PwPublicationMvcService(@Qualifier("pwPublicationBusService")
    		final IPwPublicationBusService busService) {
    	this.busService = busService;
    }
    
    @RequestMapping(method = RequestMethod.GET)
    @ResponseView(IMpView.class)
    public @ResponseBody SearchResults getPubs(
    		@RequestParam(value="q", required=false) String searchTerms, //single string search
            @RequestParam(value="title", required=false) String[] title,
            @RequestParam(value="abstract", required=false) String[] pubAbstract,
            @RequestParam(value="contributor", required=false) String[] contributor,
            @RequestParam(value="prodId", required=false) String[] prodId,
            @RequestParam(value="indexId", required=false) String[] indexId,
            @RequestParam(value="ipdsId", required=false) String[] ipdsId,
            @RequestParam(value="year", required=false) String[] year,
            @RequestParam(value="startYear", required=false) String[] yearStart,
            @RequestParam(value="endYear", required=false) String[] yearEnd,
    		@RequestParam(value="contributingOffice", required=false) String[] contributingOffice,
            @RequestParam(value="seriesName", required=false) String[] reportSeries,
            @RequestParam(value="reportNumber", required=false) String[] reportNumber,
            @RequestParam(value="page_row_start", required=false, defaultValue = "0") String pageRowStart,
            @RequestParam(value="page_size", required=false, defaultValue = "25") String pageSize,
            @RequestParam(value="listId", required=false) String[] listId,
			HttpServletResponse response) {

        Map<String, Object> filters = new HashMap<>();

    	configureSingleSearchFilters(filters, searchTerms);

    	addToFiltersIfNotNull(filters, "title", title);
    	addToFiltersIfNotNull(filters, "abstract", pubAbstract);
    	addToFiltersIfNotNull(filters, "contributor", contributor);
    	addToFiltersIfNotNull(filters, "id", prodId);
    	addToFiltersIfNotNull(filters, "indexId", indexId);
    	addToFiltersIfNotNull(filters, "ipdsId", ipdsId);
    	addToFiltersIfNotNull(filters, "year", year);
    	addToFiltersIfNotNull(filters, "yearStart", yearStart);
    	addToFiltersIfNotNull(filters, "yearEnd", yearEnd);
    	addToFiltersIfNotNull(filters, "contributingOffice", contributingOffice);
    	addToFiltersIfNotNull(filters, "reportSeries", reportSeries);
    	addToFiltersIfNotNull(filters, "reportNumber", reportNumber);
    	addToFiltersIfNotNull(filters, "pageRowStart", pageRowStart);
    	addToFiltersIfNotNull(filters, "pageSize", pageSize);
    	addToFiltersIfNotNull(filters, "listId", listId);

        List<PwPublication> pubs = busService.getObjects(filters);
        Integer totalPubsCount = busService.getObjectCount(filters);
        SearchResults results = new SearchResults();
        results.setPageSize(pageSize);
        results.setPageRowStart(pageRowStart);
        results.setRecords(pubs);
        results.setRecordCount(totalPubsCount);

        return results;
    }

    @RequestMapping(value="{indexId}", method=RequestMethod.GET)
    @ResponseView(IMpView.class)
    public @ResponseBody PwPublication getPwPublication(HttpServletRequest request, HttpServletResponse response,
                @PathVariable("indexId") String indexId) {
        setHeaders(response);
        return busService.getByIndexId(indexId);
    }

}