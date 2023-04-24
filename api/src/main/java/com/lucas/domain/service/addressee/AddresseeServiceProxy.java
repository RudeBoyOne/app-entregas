package com.lucas.domain.service.addressee;

import com.lucas.api.dtos.input.addressee.serve.AddresseeInput;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Optional;

@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@RegisterRestClient
public interface AddresseeServiceProxy {

    //https://brasilapi.com.br/api/cep/v2/08061360
    @GET
    @Path("/cep/v2/{cep}")
    public Optional<AddresseeInput> getDataAddressee(@PathParam("cep") String cep);
}
