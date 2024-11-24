package ca.gbc.orderservice.stub;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;

public class InventoryClientStub {

    private static WireMockConfiguration wireMockConfig = WireMockConfiguration.options().port(8065);

    public static void stubInventoryCall(String skuCode, Integer quantity) {
        WireMockServer wireMockServer = new WireMockServer(wireMockConfig);
        wireMockServer.start();
        configureFor("localhost", 8065);

        stubFor(get(urlEqualTo("/api/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withStatus(200)
                        .withHeader("Content-Type", "application/json")
                        .withBody("true"))
        );
    }
}
