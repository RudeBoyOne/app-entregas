package com.lucas.api.resource;

import com.lucas.api.assembler.LoginAssembler;
import com.lucas.api.dtos.input.UserLoginInput;
import com.lucas.api.dtos.output.UserLoginOutput;
import com.lucas.common.exceptions.Problem;
import com.lucas.domain.model.User;
import com.lucas.domain.service.JwtService;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class AuthResource {

    @Inject
    JwtService jwtService;

    @Inject
    LoginAssembler loginAssembler;



    @Operation(summary = "endpoint de login da aplicação", description = "onde usuários previamente cadastrados irão " +
            "se autenticar para consumir os recursos do sistema")
    @APIResponses(value = {
            @APIResponse(responseCode = "200", description = "OK", content = @Content(schema =
            @Schema(implementation = UserLoginOutput.class))),
            @APIResponse(responseCode = "400", description = "BAD REQUEST",content = @Content(schema =
            @Schema(implementation = Problem.class)) ),
            @APIResponse(responseCode = "404", description = "NOT FOUND",content = @Content(schema =
            @Schema(implementation = Problem.class)) )
    }
    )
    @POST
    @PermitAll
    public Response login(@Valid UserLoginInput userLoginInput) {
        List<Object> response = jwtService.generateJwt(userLoginInput);

        User user;
        String token;

        token= (String)response.get(0);
        user = (User) response.get(1);

        UserLoginOutput userLoginOutput = loginAssembler.toOutput(user);

        userLoginOutput.setToken(token);

        return Response.ok(userLoginOutput).build();
    }
}
