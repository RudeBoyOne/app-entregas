package com.lucas.api.resource;

import com.lucas.api.assembler.DeliveryAssembler;
import com.lucas.api.dtos.input.DeliveryInput;
import com.lucas.api.dtos.output.DeliveryOutput;
import com.lucas.common.exceptions.Problem;
import com.lucas.domain.model.Addressee;
import com.lucas.domain.model.Delivery;
import com.lucas.domain.model.Item;
import com.lucas.domain.model.Sender;
import com.lucas.domain.service.DeliveryService;
import com.lucas.domain.service.ItemService;
import com.lucas.domain.service.SenderService;
import com.lucas.domain.service.addressee.AddresseeService;
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

@Path("/deliveries")
@Tag(name = "Delivery")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class DeliveryResource {

    @Inject
    SenderService senderService;

    @Inject
    AddresseeService addresseeService;

    @Inject
    ItemService itemService;

    @Inject
    DeliveryService deliveryService;
    
    @Inject
    DeliveryAssembler deliveryAssembler;


    @Operation(summary = "Cria uma entrega de um item no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "201", description = "CREATED", content = @Content(schema =
            @Schema(implementation = DeliveryOutput.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST",  content = @Content(schema =
            @Schema(implementation =
                    Problem.class)))
    }
    )
    @POST
    @RolesAllowed({"ADMIN", "USER"})
    public Response create(@Valid DeliveryInput deliveryInput) {
        Sender sender = senderService.findSenderById(deliveryInput.getSender());

        Addressee addressee = addresseeService.findAddresseeById(deliveryInput.getAddressee());

        Item item = itemService.findItemById(deliveryInput.getItem());

        Delivery deliveryEntity = deliveryAssembler.toEntity(deliveryInput);
        deliveryEntity.setSender(sender);
        deliveryEntity.setAddressee(addressee);
        deliveryEntity.setItem(item);

        Delivery deliverySave = deliveryService.createDelivery(deliveryEntity);

        DeliveryOutput deliveryOutput = deliveryAssembler.toOutput(deliverySave);

        return Response.status(Response.Status.CREATED).entity(deliveryOutput).build();
    }


    @Operation(summary = "Finaliza uma entrega não concluída (PENDING) de um item no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = DeliveryOutput.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation =
                    Problem.class))),
            @APIResponse(responseCode = "404", description =  "NOT FOUND", content = @Content(schema = @Schema(implementation =
                    Problem.class)))
    }
    )
    @PUT
    @Path("/{id}/finalize")
    @RolesAllowed({"ADMIN", "USER"})
    public Response finalize(@PathParam("id") UUID id) {
        DeliveryOutput deliveryOutput = deliveryAssembler.toOutput(deliveryService.finalizeDelivery(id));
        return Response.ok(deliveryOutput).build();
    }

    @Operation(summary = "Cancela uma entrega ainda não concluída (PENDING) de um item no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = DeliveryOutput.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST", content = @Content(schema = @Schema(implementation =
                    Problem.class))),
            @APIResponse(responseCode = "404", description =  "NOT FOUND", content = @Content(schema = @Schema(implementation =
                    Problem.class)))
    }
    )
    @PUT
    @Path("/{id}/cancel")
    @RolesAllowed({"ADMIN", "USER"})
    public Response cancel(@PathParam("id") UUID id) {
        DeliveryOutput deliveryOutput = deliveryAssembler.toOutput(deliveryService.cancelDelivery(id));
        return Response.ok(deliveryOutput).build();
    }

    @Operation(summary = "Busca uma entrega em específico no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = DeliveryOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND",content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @GET
    @Path("/{id}")
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response searchById(@PathParam("id") UUID id) {
        DeliveryOutput deliveryOutput = deliveryAssembler.toOutput(deliveryService.findDeliveryById(id));
        return Response.ok(deliveryOutput).build();
    }

    @Operation(summary = "Lista entregas referente a um remetente no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = DeliveryOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND",content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @GET
    @Path("/searchBySender")
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response searchBySender(@QueryParam("sender_id") UUID id) {
        Sender sender = senderService.findSenderById(id);

        List<DeliveryOutput> deliveryOutput =
                deliveryAssembler.toCollectionOutput(deliveryService.findDeliveryBySender(sender));

        return Response.ok(deliveryOutput).build();
    }

    @Operation(summary = "Busca a entrega referente de item no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = DeliveryOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND",content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @GET
    @Path("/searchByItem")
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response searchByItem(@QueryParam("item_id") UUID id) {
        Item item = itemService.findItemById(id);

        DeliveryOutput deliveryOutput =
                deliveryAssembler.toOutput(deliveryService.findDeliveryByItem(item));

        return Response.ok(deliveryOutput).build();
    }

    @Operation(summary = "Lista entregas referente a um destinatário no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = DeliveryOutput.class))),
            @APIResponse(responseCode = "404", description = "NOT FOUND",content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @GET
    @Path("/searchByAddressee")
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response searchByAddressee(@QueryParam("addressee_id") UUID id) {
        Addressee addressee = addresseeService.findAddresseeById(id);

        List<DeliveryOutput> deliveryOutput =
                deliveryAssembler.toCollectionOutput(deliveryService.findDeliveryByAddressee(addressee));

        return Response.ok(deliveryOutput).build();
    }

    @Operation(summary = "Lista todas entregas no sistema")
    @APIResponse(responseCode = "200", description =  "OK", content = @Content(schema = @Schema(implementation =
            DeliveryOutput.class)))
    @GET
    @RolesAllowed({"ADMIN", "USER", "CLIENT"})
    public Response list() {
        List<DeliveryOutput> collectionOutput = deliveryAssembler.toCollectionOutput(deliveryService.listAll());
        return Response.ok(collectionOutput).build();
    }


    @Operation(summary = "Exclui uma entrega em específico no sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "204", description = "NO CONTENT"),
            @APIResponse(responseCode = "404", description = "NOT FOUND", content = @Content(schema =
            @Schema(implementation = Problem.class)))
    }
    )
    @DELETE
    @Path("{id}")
    @RolesAllowed({"ADMIN", "USER"})
    public Response delete(@PathParam("id") UUID id) {
        deliveryService.deleteDelivery(id);
        return Response.noContent().build();
    }
}
