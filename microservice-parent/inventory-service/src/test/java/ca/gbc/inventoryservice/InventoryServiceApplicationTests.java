package ca.gbc.inventoryservice;


import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Import;
import org.testcontainers.containers.PostgreSQLContainer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.hamcrest.Matchers.equalTo;

@Import(TestcontainersConfiguration.class)
@SpringBootTest(webEnvironment =  SpringBootTest.WebEnvironment.RANDOM_PORT)
class InventoryServiceApplicationTests {

    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16");

    @LocalServerPort
    private Integer port;

    @BeforeEach
    void setup(){
        RestAssured.baseURI = "http://localhost";
        RestAssured.port = port;
    }

    static{
        postgreSQLContainer.start();
    }

    @Test
    void shouldBeInStockTest() {
        RestAssured.given()
                .queryParam("skuCode", "SKU001")
                .queryParam("quantity", 1)
                .when()
                .get("/api/inventory")
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo("true"));
    }
    @Test
    void shouldNotBeInStockTest() {

        RestAssured.given()
                .queryParam("skuCode", "SKU001")
                .queryParam("quantity", 101)
                .when()
                .get("/api/inventory")
                .then()
                .log().all()
                .statusCode(200)
                .body(equalTo("false"));
    }
}