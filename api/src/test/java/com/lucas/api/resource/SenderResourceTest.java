package com.lucas.api.resource;

import io.quarkus.test.junit.QuarkusTest;
import org.hamcrest.CoreMatchers;

import javax.ws.rs.core.MediaType;

import static io.restassured.RestAssured.given;

@QuarkusTest
public class SenderResourceTest {

    //@Test
    void testListSenders() {
        given()
                .when().get("/senders")
                .then()
                    .statusCode(200)
                    .contentType(MediaType.APPLICATION_JSON)
                    .body("size()", CoreMatchers.is(0));
    }
}
