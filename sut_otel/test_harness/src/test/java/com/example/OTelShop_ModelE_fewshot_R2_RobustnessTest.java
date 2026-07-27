package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class OTelShop_ModelE_fewshot_R2_RobustnessTest extends OTelShopBaseTest {

    @Test
    public void test_R1_ProductNonExistentId() throws Exception {
        HttpResponse<String> response = get("/api/products/DOESNOTEXIST999");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartEmptySessionId() throws Exception {
        HttpResponse<String> response = get("/api/cart?sessionId=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddNegativeQuantity() throws Exception {
        String body = "{\"userId\":\"user-1\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":-1}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CheckoutEmptyUserId() throws Exception {
        String body = "{\"userId\":\"\",\"userCurrency\":\"USD\","
            + "\"address\":{\"streetAddress\":\"1 Main St\",\"state\":\"CA\","
            + "\"country\":\"US\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},"
            + "\"email\":\"test@example.com\","
            + "\"creditCard\":{\"creditCardNumber\":\"4111-1111-1111-1111\","
            + "\"creditCardCvv\":123,\"creditCardExpirationYear\":2030,\"creditCardExpirationMonth\":1}}";
        HttpResponse<String> response = postJson("/api/checkout", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CurrencyUnknownFromCode() throws Exception {
        String body = "{\"from\":{\"currencyCode\":\"ZZZ\",\"units\":10,\"nanos\":0},\"toCode\":\"USD\"}";
        HttpResponse<String> response = postJson("/api/currency", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ShippingEmptyItemsList() throws Exception {
        String body = "{\"address\":{\"streetAddress\":\"1 Main St\",\"state\":\"CA\","
            + "\"country\":\"US\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},\"items\":[]}";
        HttpResponse<String> response = postJson("/api/shipping", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_RecommendationsUnknownProductIds() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=UNKNOWN&sessionId=session-1&currencyCode=USD");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_RecommendationsEmptySessionId() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=OLJCESPC7Z&sessionId=&currencyCode=USD");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_AdsUnknownContextKeys() throws Exception {
        HttpResponse<String> response = get("/api/ads?contextKeys=UNKNOWN");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ProductsInvalidPath() throws Exception {
        HttpResponse<String> response = get("/api/products INVALID PATH");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartDeleteEmptySessionId() throws Exception {
        HttpResponse<String> response = delete("/api/cart?sessionId=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CheckoutInvalidJsonBody() throws Exception {
        String body = "INVALID JSON BODY";
        HttpResponse<String> response = postJson("/api/checkout", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CurrencyEmptyFromCode() throws Exception {
        String body = "{\"from\":{\"currencyCode\":\"\",\"units\":10,\"nanos\":0},\"toCode\":\"USD\"}";
        HttpResponse<String> response = postJson("/api/currency", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ShippingInvalidAddress() throws Exception {
        String body = "{\"address\":{\"streetAddress\":\"\",\"state\":\"CA\","
            + "\"country\":\"US\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}";
        HttpResponse<String> response = postJson("/api/shipping", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_RecommendationsInvalidCurrencyCode() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=OLJCESPC7Z&sessionId=session-1&currencyCode=INVALID");
        assertNoServerError(response);
    }
}