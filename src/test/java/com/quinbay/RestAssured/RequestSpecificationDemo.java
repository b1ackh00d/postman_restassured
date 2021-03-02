package com.quinbay.RestAssured;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.client.methods.RequestBuilder;
import org.hamcrest.Matchers;

import static io.restassured.RestAssured.given;

public class RequestSpecificationDemo {

    public static void main(String[] args) {
        RequestSpecBuilder requestSpecBuilder = new RequestSpecBuilder();

        requestSpecBuilder.setBaseUri("https://reqres.in");
        requestSpecBuilder.setBasePath("/api");
        requestSpecBuilder.setContentType(ContentType.JSON);
        requestSpecBuilder.log(LogDetail.ALL);
        RequestSpecification reqSpec = requestSpecBuilder.build();

        Response response = given()
                .queryParam("page", "2")
                .spec(reqSpec)
                .when().get("/users/");
        response.prettyPrint();


        ResponseSpecification responseSpecification = new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectStatusLine("HTTP/1.1 200 OK")
                .expectContentType(ContentType.JSON)
                .expectResponseTime(Matchers.lessThan(10000L))
                .log(LogDetail.ALL)
                .build();
//
        given()
                 .queryParam("page", 2)
                .spec(reqSpec)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpecification);
    }
}
