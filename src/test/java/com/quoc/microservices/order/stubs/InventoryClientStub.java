package com.quoc.microservices.order.stubs;

import static com.github.tomakehurst.wiremock.client.WireMock.*;

// Stub class for InventoryClient to be used in OrderServiceTest
public class InventoryClientStub {

    public static void stubInventoryCall(String skuCode, Integer quantity) {
        // Stub the call to Inventory service
        // If the request is call to /api/inventory?skuCode={skuCode}&quantity={quantity}, return 200 with body "true"
        stubFor(get(urlEqualTo("/api/inventory?skuCode=" + skuCode + "&quantity=" + quantity))
                .willReturn(aResponse()
                        .withHeader("Content-Type", "application/json")
                        .withStatus(200)
                        .withBody("true")));
    }
}
