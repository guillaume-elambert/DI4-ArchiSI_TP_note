package edu.polytech.estore.webservice;

import edu.polytech.estore.business.StoreBusinessLocal;
import edu.polytech.estore.model.Product;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Locale;

@Stateless
@Path("/v1")
public class StoreRest {


    @EJB
    private StoreBusinessLocal storeBusinessLocal;

    @GET
    @Path("/products")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Product> getLocations(@QueryParam("order") String order, @QueryParam("filter") String filter){
        return storeBusinessLocal.getSortedProductsOfCategory(filter, !order.toLowerCase(Locale.ROOT).equals("desc"));
    }
}