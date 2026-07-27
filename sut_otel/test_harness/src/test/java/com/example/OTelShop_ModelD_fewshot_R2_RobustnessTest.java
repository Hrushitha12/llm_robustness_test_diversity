package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class OTelShop_ModelD_fewshot_R2_RobustnessTest extends OTelShopBaseTest {

    @Test
    public void test_R1_ProductInvalidPath() throws Exception {
        HttpResponse<String> response = get("/api/products/invalid/path");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ProductEmptyId() throws Exception {
        HttpResponse<String> response = get("/api/products/");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartMissingSessionId() throws Exception {
        HttpResponse<String> response = get("/api/cart");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddInvalidProductId() throws Exception {
        String body = "{\"userId\":\"user-1\",\"item\":{\"productId\":\"INVALID\",\"quantity\":1}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartDeleteInvalidSessionId() throws Exception {
        HttpResponse<String> response = delete("/api/cart?sessionId=INVALID");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CheckoutInvalidCurrencyCode() throws Exception {
        String body = "{\"userId\":\"user-1\",\"userCurrency\":\"ZZZ\","
            + "\"address\":{\"streetAddress\":\"1 Main St\",\"state\":\"CA\","
            + "\"country\":\"US\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},"
            + "\"email\":\"test@example.com\","
            + "\"creditCard\":{\"creditCardNumber\":\"4111-1111-1111-1111\","
            + "\"creditCardCvv\":123,\"creditCardExpirationYear\":2030,\"creditCardExpirationMonth\":1}}";
        HttpResponse<String> response = postJson("/api/checkout", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CurrencyZeroUnits() throws Exception {
        String body = "{\"from\":{\"currencyCode\":\"USD\",\"units\":0,\"nanos\":0},\"toCode\":\"EUR\"}";
        HttpResponse<String> response = postJson("/api/currency", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ShippingInvalidCountry() throws Exception {
        String body = "{\"address\":{\"streetAddress\":\"1 Main St\",\"state\":\"CA\","
            + "\"country\":\"INVALID\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},"
            + "\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}";
        HttpResponse<String> response = postJson("/api/shipping", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_RecommendationsMissingSessionId() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=OLJCESPC7Z&currencyCode=USD");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_RecommendationsInvalidCurrencyCode() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=OLJCESPC7Z&sessionId=session-1&currencyCode=XYZ");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CheckoutMissingCreditCardNumber() throws Exception {
        String body = "{\"userId\":\"user-1\",\"userCurrency\":\"USD\","
            + "\"address\":{\"streetAddress\":\"1 Main St\",\"state\":\"CA\","
            + "\"country\":\"US\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},"
            + "\"email\":\"test@example.com\","
            + "\"creditCard\":{\"creditCardCvv\":123,\"creditCardExpirationYear\":2030,\"creditCardExpirationMonth\":1}}";
        HttpResponse<String> response = postJson("/api/checkout", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ShippingEmptyAddress() throws Exception {
        String body = "{\"address\":{},\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}";
        HttpResponse<String> response = postJson("/api/shipping", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddZeroQuantity() throws Exception {
        String body = "{\"userId\":\"user-1\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":0}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_RecommendationsEmptyProductIds() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=&sessionId=session-1&currencyCode=USD");
        assertNoServerError(response);
    }
}