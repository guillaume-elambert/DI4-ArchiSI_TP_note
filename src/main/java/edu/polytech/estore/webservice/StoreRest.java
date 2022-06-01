package edu.polytech.estore.webservice;

import edu.polytech.estore.business.StoreBusinessLocal;
import edu.polytech.estore.model.Comment;
import edu.polytech.estore.model.Product;
import edu.polytech.estore.model.WsCurrencyConversionResult;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Locale;

@Stateless
@Path("/v1")
public class StoreRest {


    @EJB
    private StoreBusinessLocal storeBusinessLocal;


    private static final String CURRENCY_CONVERSION_API_URI = "https://api.apilayer.com/exchangerates_data/convert";
    private static final String CURRENCY_CURRENCY_API_KEY = "oPsdeLVPpY6xOmMLlUXHmuRrNAPge11u";
    private static final String DEFAULT_CURRENCY = "EUR";

    
    
    @GET
    @Path("/products")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Product> getProducts(@QueryParam("order") String order, @QueryParam("category") String category, @QueryParam("currency") String currency) {

        List<Product> products = null;

        if(order != null && category != null) products = storeBusinessLocal.getSortedProductsOfCategory(category, !order.toLowerCase(Locale.ROOT).equals("desc"));
        if(order != null)  products = storeBusinessLocal.getSortedProducts(!order.toLowerCase(Locale.ROOT).equals("desc"));
        if(category != null)  products = storeBusinessLocal.getProductsOfCategory(category);
        if(products == null) products = storeBusinessLocal.getProducts();

        if(currency == null) return products;

        //Get the currency conversion rate
        Double conversionRate = ConversionResult.getConversionRate(DEFAULT_CURRENCY, currency).getConversionRate();

        if(conversionRate == null) return products;

        //Apply the conversion for each product
        for(Product product : products) {
            product.setPriceInCurrency(product.getPriceInEuro() * conversionRate);
        }

        return products;

    }

    
    @GET
    @Path("/products/{id}")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Product getProduct(@PathParam("id") long idProduct, @QueryParam("currency") String currency) {
        Product product = storeBusinessLocal.getProduct(idProduct);

        if(currency == null) return product;

        //Get the currency conversion rate
        Double conversionRate = ConversionResult.getConversionRate(DEFAULT_CURRENCY, currency).getConversionRate();

        if(conversionRate == null) return product;

        //Apply the conversion for the product
        product.setPriceInCurrency(product.getPriceInEuro() * conversionRate);

        return product;
    }

    
    @DELETE
    @Path("/products/{id}")
    public Response deleteProduct(@PathParam("id") long idProduct) {

        if(storeBusinessLocal.getProduct(idProduct) == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        storeBusinessLocal.deleteProduct(idProduct);
        return Response.ok().build();
    }

    
    @POST
    @Path("/products")
    @Consumes({ MediaType.APPLICATION_FORM_URLENCODED })
    public Response createProduct(@FormParam("label") String label, @FormParam("category") String category, @FormParam("price") Double price, @FormParam("quantity") int quantity) {
        storeBusinessLocal.createProduct(new Product(label, category, price, quantity));
        return Response.ok().build();
    }

    
    @POST
    @Path("/products")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response createProduct(Product product, @QueryParam("currency") String currency) {

        Response conversionResponse = computeProductPriceCurrencyConversion(product, currency);

        //If the conversion failed, return the response
        if(conversionResponse.getStatusInfo() != Response.Status.OK)
            return conversionResponse;

        storeBusinessLocal.createProduct(product);
        return Response.ok().build();
    }

    
    @PUT
    @Path("/products/{id}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response updateProduct(@PathParam("id") long idProduct, Product product, @QueryParam("currency") String currency) {
        if(storeBusinessLocal.getProduct(idProduct) == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        Response conversionResponse = computeProductPriceCurrencyConversion(product, currency);

        //If the conversion failed, return the response
        if(conversionResponse.getStatusInfo() != Response.Status.OK)
            return conversionResponse;

        product.setProductId(idProduct);
        storeBusinessLocal.updateProduct(product);
        return Response.ok().build();
    }

    
    @PATCH
    @Path("/products/{id}")
    @Consumes({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public Response patchProduct(@PathParam("id") long idProduct, Product product, @QueryParam("currency") String currency) {
        if(storeBusinessLocal.getProduct(idProduct) == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        Response conversionResponse = computeProductPriceCurrencyConversion(product, currency);

        //If the conversion failed, return the response
        if(conversionResponse.getStatusInfo() != Response.Status.OK)
            return conversionResponse;

        storeBusinessLocal.patchProduct(idProduct, product);
        return Response.ok().build();
    }

    @GET
    @Path("/products/{id}/comments")
    @Produces({ MediaType.APPLICATION_XML, MediaType.APPLICATION_JSON })
    public List<Comment> getComments(@PathParam("id") long idProduct) {
        return storeBusinessLocal.getProductComments(idProduct);
    }


    /**
     * Method to compute the currency conversion for a product from {@code product} to {@link #DEFAULT_CURRENCY}
     * @param product the product with the price to convert (will be modified)
     * @param currency the currency to convert to
     * @return The conversion api response or a {@link Response.Status} if the conversion conditions are not met :
     * <ul>
     *     <li>{@link Response.Status#BAD_REQUEST} if this means that there is an error</li>
     *     <li>{@link Response.Status#BAD_REQUEST} if the user don't want to convert the price</li>
     * </ul>
     */
    public Response computeProductPriceCurrencyConversion(Product product, String currency){

        //Entering : the user is not trying to create/update a product with a specific currency
        //      => Returning an OK response
        if(currency == null){
            return Response.ok().build();
        }

        //Entering : the user wants to update a product with a specific currency
        //           but the currency is the default one or the priceInCurrency is not set
        //      => Returning a BAD_REQUEST response
        if(        currency.toUpperCase(Locale.ROOT).equals(DEFAULT_CURRENCY)
                || product.getPriceInCurrency() == null
        ) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }


        //Get the currency conversion rate
        ConversionResult conversionResult = ConversionResult.getConversionRate(currency, DEFAULT_CURRENCY);
        Double conversionRate = conversionResult.getConversionRate();

        if(!conversionResult.isOk())
            return conversionResult.getApiResponse();

        //Apply the conversion for the product
        product.setPriceInEuro(product.getPriceInCurrency() * conversionRate);

        return Response.ok().build();
    }


    /**
     * Return the conversion rate between two currencies and the response of the API used for the conversion
     */
    public static class ConversionResult {
        /**
         * The conversion rate
         */
        private Double conversionRate;
        /**
         * The response of the API used for the conversion
         */
        private Response apiResponse;

        public ConversionResult(Double conversionRate, Response apiResponse) {
            this.conversionRate = conversionRate;
            this.apiResponse = apiResponse;
        }

        public ConversionResult(Double conversionRate) {
            this(conversionRate, Response.ok().build());
        }

        public ConversionResult(Response apiResponse) {
            this(null, apiResponse);
        }

        public ConversionResult() {
            this(null, Response.status(Response.Status.BAD_REQUEST).build());
        }


        /**
         * Return the conversion rate between two currencies
         * @param from The currency to convert from
         * @param to The currency to convert to
         * @return A {@link ConversionResult} object containing the conversion rate and the {@link Response} of the API used for the conversion
         */
        private static ConversionResult getConversionRate(String from, String to) {

            if(from == null || from.equals("") || to == null || to.equals("")) return null;

            from = from.toUpperCase();
            to = to.toUpperCase();

            if(from.equals(to)) {
                return new ConversionResult();
            }


            Client client = ClientBuilder.newClient();
            WebTarget webTarget = client.target(CURRENCY_CONVERSION_API_URI)
                    .queryParam("from", from)
                    .queryParam("to", to)
                    .queryParam("amount", 1);

            Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON).header("apikey", CURRENCY_CURRENCY_API_KEY);

            Response response = invocationBuilder.get();

            if(response.getStatusInfo() != Response.Status.OK || response.getStatus() != 200)
                return new ConversionResult(response);

            WsCurrencyConversionResult result = response.readEntity(WsCurrencyConversionResult.class);

            if(result == null)
                return new ConversionResult();

            return new ConversionResult(result.getResult());
        }


        public boolean isOk(){
            return apiResponse.getStatusInfo() == Response.Status.OK && apiResponse.getStatus() == 200 && conversionRate != null;
        }

        public Double getConversionRate() {
            return conversionRate;
        }

        public void setConversionRate(Double conversionRate) {
            this.conversionRate = conversionRate;
        }

        public Response getApiResponse() {
            return apiResponse;
        }

        public void setApiResponse(Response apiResponse) {
            this.apiResponse = apiResponse;
        }

    }
}