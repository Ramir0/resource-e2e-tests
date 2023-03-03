package dev.amir.resource.steps;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.io.File;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class CreateResourceSteps {
    private static final String RESOURCE_SERVICE_URL = "http://localhost:8081";
    private static final String BASE_FILES_PATH = "src/test/resources/files/";
    private RequestSpecification request;
    private Response response;
    private File file;
    private Long resourceId;

    @Given("the API is available")
    public void the_api_is_available() {
        RestAssured.baseURI = RESOURCE_SERVICE_URL;
        request = RestAssured.given();
    }

    @Given("a file {string}")
    public void a_file(String fileName) {
        file = new File(Paths.get(BASE_FILES_PATH, fileName).toUri());
    }

    @When("send a POST request to {string}")
    public void send_a_post_request_to(String endpoint) {
        response = request.body(file).post(endpoint);
    }

    @When("send a DELETE request to {string} with the Resource Id")
    public void send_a_delete_request_to_with_the_resource_id(String endpoint) {
        response = request.param("id", resourceId).delete(endpoint);
    }

    @Then("the response status code should be {int}")
    public void the_response_status_code_should_be(int statusCode) {
        assertEquals(statusCode, response.getStatusCode());
    }

    @Then("the response body should contain the message {string}")
    public void the_response_body_should_contain_the_message(String message) {
        assertEquals(message, response.getBody().asString());
    }

    @And("the response body should contain a Resource Id")
    public void the_response_body_should_contain_a_resource_id() {
        resourceId = response.jsonPath().getLong("id");
        assertNotNull(resourceId);
        assertTrue(resourceId > 0);
    }

    @And("the response body should contain a List of deleted Resource Id")
    public void the_response_body_should_contain_a_list_of_resource_id() {
        var ids = response.jsonPath().getList("ids", Long.class);
        assertNotNull(ids);
        assertFalse(ids.isEmpty());
        assertTrue(ids.get(0) > 0);
    }

    @And("I wait for {int} seconds")
    public void iWaitForSeconds(int seconds) {
        try {
            Thread.sleep(seconds * 1000L);
        } catch (InterruptedException e) {
            fail();
        }
    }
}
