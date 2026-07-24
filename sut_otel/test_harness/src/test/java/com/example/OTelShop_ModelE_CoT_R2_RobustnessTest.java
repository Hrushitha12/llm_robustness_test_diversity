package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class OTelShop_ModelE_CoT_R2_RobustnessTest extends OTelShopBaseTest {

    @Test
    public void test_R1_ProductCatalogNonExistentID() throws Exception {
        HttpResponse<String> response = get("/api/products/ NON_EXISTENT_ID");
        assertNoServerError(response);
    }

    @Test
    public void test_R2_EmptyPathSegment() throws Exception {
        HttpResponse<String> response = get("/api/products/");
        assertNoServerError(response);
    }

    @Test
    public void test_R3_VeryLongProductID() throws Exception {
        String veryLongId = "a".repeat(500);
        HttpResponse<String> response = get("/api/products/" + veryLongId);
        assertNoServerError(response);
    }

    @Test
    public void test_R4_CartGetEmptySessionId() throws Exception {
        HttpResponse<String> response = get("/api/cart?sessionId=");
        assertNoServerError(response);
    }

    @Test
    public void test_R5_MissingSessionIdQueryParameter() throws Exception {
        HttpResponse<String> response = get("/api/cart");
        assertNoServerError(response);
    }

    @Test
    public void test_R6_VeryLongSessionId() throws Exception {
        String veryLongSessionId = "a".repeat(500);
        HttpResponse<String> response = get("/api/cart?sessionId=" + veryLongSessionId);
        assertNoServerError(response);
    }

    @Test
    public void test_R7_CartPostEmptyUserId() throws Exception {
        String jsonBody = "{\"userId\":\"\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}}";
        HttpResponse<String> response = postJson("/api/cart", jsonBody);
        assertNoServerError(response);
    }

    @Test
    public void test_R8_CartPostNullItem() throws Exception {
        String jsonBody = "{\"userId\":\"user123\",\"item\":null}";
        HttpResponse<String> response = postJson("/api/cart", jsonBody);
        assertNoServerError(response);
    }

    @Test
    public void test_R9_CheckoutUnknownUserCurrency() throws Exception {
        String jsonBody = "{\"userId\":\"user123\",\"userCurrency\":\"ZZZ\",\"address\":{\"streetAddress\":\"Street 1\",\"state\":\"State 1\",\"country\":\"Country 1\",\"city\":\"City 1\",\"zipCode\":\"Zip Code 1\"},\"email\":\"user@example.com\",\"creditCard\":{\"creditCardNumber\":\"4111111111111111\",\"creditCardCvv\":123,\"creditCardExpirationYear\":2025,\"creditCardExpirationMonth\":12}}";
        HttpResponse<String> response = postJson("/api/checkout", jsonBody);
        assertNoServerError(response);
    }

    @Test
    public void test_R10_CheckoutInvalidEmail() throws Exception {
        String jsonBody = "{\"userId\":\"user123\",\"userCurrency\":\"USD\",\"address\":{\"streetAddress\":\"Street 1\",\"state\":\"State 1\",\"country\":\"Country 1\",\"city\":\"City 1\",\"zipCode\":\"Zip Code 1\"},\"email\":\" invalid email \",\"creditCard\":{\"creditCardNumber\":\"4111111111111111\",\"creditCardCvv\":123,\"creditCardExpirationYear\":2025,\"creditCardExpirationMonth\":12}}";
        HttpResponse<String> response = postJson("/api/checkout", jsonBody);
        assertNoServerError(response);
    }

    @Test
    public void test_R11_CurrencyUnknownFromCode() throws Exception {
        String jsonBody = "{\"from\":{\"currencyCode\":\"ZZZ\",\"units\":1,\"nanos\":0},\"toCode\":\"USD\"}";
        HttpResponse<String> response = postJson("/api/currency", jsonBody);
        assertNoServerError(response);
    }

    @Test
    public void test_R12_ShippingEmptyAddress() throws Exception {
        String jsonBody = "{\"address\":{\"streetAddress\":\"\",\"state\":\"\",\"country\":\"\",\"city\":\"\",\"zipCode\":\"\"},\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}";
        HttpResponse<String> response = postJson("/api/shipping", jsonBody);
        assertNoServerError(response);
    }

    @Test
    public void test_R13_RecommendationsInvalidProductId() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=INVALID_ID&sessionId=session123&currencyCode=USD");
        assertNoServerError(response);
    }

    @Test
    public void test_R14_AdsEmptyContextKeys() throws Exception {
        HttpResponse<String> response = get("/api/ads?contextKeys=");
        assertNoServerError(response);
    }
}