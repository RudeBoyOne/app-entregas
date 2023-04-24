package com.lucas.api.resource;

import com.lucas.api.assembler.SenderAssembler;
import com.lucas.api.dtos.input.SenderInput;
import com.lucas.api.dtos.output.SenderOutput;
import com.lucas.common.exceptions.Problem;
import com.lucas.domain.model.Sender;
import com.lucas.domain.service.SenderService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;
import org.eclipse.microprofile.openapi.annotations.tags.Tag;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

@Path("/senders")
@Tag(name = "Sender")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class SenderResource {

    @Inject
    SenderService senderService;

    @Inject
    SenderAssembler senderAssembler;


    @Operation(summary = "Cria um remetente no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "CREATED", content = @Content(schema =
            @Schema(implementation = SenderOutput.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public Response create(@Valid SenderInput senderInput) {
        Sender senderEntity = senderAssembler.toEntity(senderInput);

        Sender senderSave = senderService.createSender(senderEntity);

        SenderOutput senderOutput = senderAssembler.toOutput(senderSave);

        return Response.status(Response.Status.CREATED).entity(senderOutput).build();
    }

    @Operation(summary = "Atualiza um remetente no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = SenderOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response update(@PathParam("id") UUID id, SenderInput senderInput) {
        Sender senderEntity = senderAssembler.toEntity(senderInput);
        Sender senderUpdated = senderService.updateSender(id, senderEntity);
        SenderOutput senderOutput = senderAssembler.toOutput(senderUpdated);
        return Response.ok().entity(senderOutput).build();
    }


    @Operation(summary = "Busca um remetente em específico no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = SenderOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response searchById(@PathParam("id") UUID id) {
        SenderOutput senderOutput = senderAssembler.toOutput(senderService.findSenderById(id));
        return Response.ok().entity(senderOutput).build();
    }


    @Operation(summary = "Exclui um remetente em específico no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "NO CONTENT"),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema =
            @Schema(implementation = Problem.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @DELETE
    @Path("{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response delete(UUID id) {
        senderService.deleteSender(id);
        return Response.noContent().build();
    }

    @Operation(summary = "Lista todos remetentes no sistema")
    @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
    @Schema(implementation = SenderOutput.class)))
    @GET
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public List<SenderOutput> list() {
        List<SenderOutput> senderOutputs = senderAssembler.toCollectionOutput(senderService.listAll());
        return senderOutputs;
    }

}
