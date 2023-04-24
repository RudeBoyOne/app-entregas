package com.lucas.api.resource;

import com.lucas.api.assembler.AddresseeAssembler;
import com.lucas.api.dtos.input.addressee.AddresseeInputClient;
import com.lucas.api.dtos.input.addressee.serve.AddresseeInput;
import com.lucas.api.dtos.output.AddresseeOutput;
import com.lucas.common.exceptions.Problem;
import com.lucas.domain.model.Addressee;
import com.lucas.domain.service.addressee.AddresseeService;
import com.lucas.domain.service.addressee.AddresseeServiceProxy;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Path("/addressees")
@Tag(name = "Addressee")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AddresseeResource {

    @Inject
    AddresseeService addresseeService;

    @Inject
    AddresseeAssembler addresseeAssembler;

    @Inject
    @RestClient
    AddresseeServiceProxy serviceProxy;


    @Operation(summary = "Cria novos destinatários no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "CREATED", content = @Content(schema =
            @Schema(implementation = AddresseeOutput.class))),
            @APIResponse(responseCode = "500", description = "Internal Server Error server")
        }
    )
    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public Response create(AddresseeInputClient addresseeInput) {
        Optional<AddresseeInput> dataAddressee = serviceProxy.getDataAddressee(addresseeInput.getCep());

        if (dataAddressee.isPresent()) {
            dataAddressee.get().setName(addresseeInput.getName());
            dataAddressee.get().setHouseNumber(addresseeInput.getHouseNumber());

            Addressee addresseEentity = addresseeAssembler.toEntity(dataAddressee.get());

            Addressee addresseSave = addresseeService.createAddresse(addresseEentity);

            AddresseeOutput output = addresseeAssembler.toOutput(addresseSave);

            return Response.status(Response.Status.CREATED).entity(output).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(summary = "Atualiza destinatários existentes no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = AddresseeOutput.class))),
            @APIResponse(responseCode = "500", description = "Internal Server Error server")
    }
    )
    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response update(@PathParam("id") UUID id, AddresseeInputClient addresseeInput) {
        Optional<AddresseeInput> dataAddressee = serviceProxy.getDataAddressee(addresseeInput.getCep());

        if (dataAddressee.isPresent()) {
            dataAddressee.get().setName(addresseeInput.getName());
            dataAddressee.get().setHouseNumber(addresseeInput.getHouseNumber());

            Addressee addresseEentity = addresseeAssembler.toEntity(dataAddressee.get());

            Addressee addresseSave = addresseeService.updateAddressee(id, addresseEentity);

            AddresseeOutput output = addresseeAssembler.toOutput(addresseSave);

            return Response.status(Response.Status.CREATED).entity(output).build();
        }

        return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
    }

    @Operation(summary = "Lista todos destinatários existente no sistema")
    @APIResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation =
            AddresseeOutput.class)))
    @GET
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response list() {
        List<AddresseeOutput> collectionOutput = addresseeAssembler.toCollectionOutput(addresseeService.listAll());
        return Response.ok(collectionOutput).build();
    }

    @Operation(summary = "Busca um destinatário em específico no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = AddresseeOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content =
            @Content(schema = @Schema(implementation = Problem.class)))
        }
    )
    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response searchById(@PathParam("id") UUID id) {
        AddresseeOutput output = addresseeAssembler.toOutput(addresseeService.findAddresseeById(id));
        return Response.ok(output).build();
    }

    @Operation(summary = "Exclui um destinatário em específico no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "No Content"),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content =
            @Content(schema = @Schema(implementation = Problem.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response delete(@PathParam("id") UUID id) {
        addresseeService.deleteAddressee(id);
        return Response.noContent().build();
    }

}
