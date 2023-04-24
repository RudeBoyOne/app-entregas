package com.lucas.api.resource;

import com.lucas.api.assembler.ItemAssembler;
import com.lucas.api.dtos.input.ItemInput;
import com.lucas.api.dtos.output.ItemOutput;
import com.lucas.common.exceptions.Problem;
import com.lucas.domain.model.Item;
import com.lucas.domain.model.Sender;
import com.lucas.domain.service.ItemService;
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

@Path("/itens")
@Tag(name = "Item")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ItemResource {

    @Inject
    ItemService itemService;

    @Inject
    SenderService senderService;

    @Inject
    ItemAssembler itemAssembler;

    @Operation(summary = "Cria um item no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "CREATED", content = @Content(schema =
            @Schema(implementation = ItemOutput.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public Response create(@Valid ItemInput itemInput) {
        Item itemEntity = itemAssembler.toEntity(itemInput);

        Sender owner = senderService.findSenderById(itemInput.getOwner());

        itemEntity.setOwner(owner);

        Item itemSave = itemService.createItem(itemEntity);

        ItemOutput itemOutput = itemAssembler.toOutput(itemSave);


        return Response.status(Response.Status.CREATED).entity(itemOutput).build();
    }

    @Operation(summary = "Atualiza um item no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = ItemOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @PUT
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response update(@PathParam("id") UUID id, @Valid ItemInput itemInput) {
        Item itemEntity = itemAssembler.toEntity(itemInput);

        Sender owner = senderService.findSenderById(itemInput.getOwner());

        itemEntity.setOwner(owner);

        Item itemSave = itemService.updateItem(id, itemEntity);

        ItemOutput itemOutput = itemAssembler.toOutput(itemSave);

        return Response.ok().entity(itemOutput).build();
    }

    @Operation(summary = "Lista todos os item no sistema")
    @APIResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation =
            ItemOutput.class)))
    @GET
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response list() {
        List<ItemOutput> outputList = itemAssembler.toCollectionOutput(itemService.listAll());
        return Response.ok().entity(outputList).build();
    }

    @Operation(summary = "Busca um item em específico no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = ItemOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @GET
    @Path("{id}")
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response searchById(UUID id) {
        ItemOutput itemOutput = itemAssembler.toOutput(itemService.findItemById(id));
        return Response.ok().entity(itemOutput).build();
    }

    @Operation(summary = "Exclui um item em específico no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "NO CONTENT"),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema =
            @Schema(implementation = Problem.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @DELETE
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response delete(@PathParam("id") UUID id) {
        itemService.deleteItem(id);
        return Response.noContent().build();
    }


}
