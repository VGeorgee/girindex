package com.girindex.backend.resource;

import com.girindex.backend.domain.FoodData;
import com.girindex.backend.domain.FoodDataService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/food-data")
@Produces("application/json")
@Consumes("application/json")
public class FoodDataResource {

    @Inject
    FoodDataService service;

    @GET
    public List<FoodData> getAll() {
        return service.getAll();
    }

    @GET
    @Path("{id}")
    public FoodData getById(@PathParam("id") Long id) {
        return service.getById(id);
    }

    @POST
    public Response create(FoodData foodData) {
        FoodData createdFoodData = service.create(foodData);
        return Response.ok(createdFoodData).build();
    }

    @DELETE
    @Path("{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @GET
    @Path("/most-recent/{place}")
    public FoodData getMostRecentByPlace(@PathParam("place") String place) {
        return service.getMostRecentByPlace(place);
    }

}