package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

class OTelShop_ModelD_Structured_R2_RobustnessTest extends OTelShopBaseTest {

    @Test
    void test_R1_NonExistentProductId() throws Exception {
        HttpResponse<String> response = get("/api/products/DOESNOTEXIST999");
        assertNoServerError(response);
    }

    @Test
    void test_R1_EmptyStringSegmentInPath() throws Exception {
        HttpResponse<String> response = get("/api/products/");
        assertNoServerError(response);
    }

    @Test
    void test_R1_NumericProductId() throws Exception {
        HttpResponse<String> response = get("/api/products/12345");
        assertNoServerError(response);
    }

    @Test
    void test_R1_ExtremelyLongProductId() throws Exception {
        StringBuilder longId = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            longId.append("a");
        }
        HttpResponse<String> response = get("/api/products/" + longId.toString());
        assertNoServerError(response);
    }

    @Test
    void test_R1_EmptySessionIdQueryParam() throws Exception {
        HttpResponse<String> response = get("/api/cart?sessionId=");
        assertNoServerError(response);
    }

    @Test
    void test_R1_SessionIdMissingFromQuery() throws Exception {
        HttpResponse<String> response = get("/api/cart");
        assertNoServerError(response);
    }

    @Test
    void test_R1_DeleteEmptySessionIdQueryParam() throws Exception {
        HttpResponse<String> response = delete("/api/cart?sessionId=");
        assertNoServerError(response);
    }

    @Test
    void test_R1_PostCart_UserIdEmptyString() throws Exception {
        String jsonBody = "{\"userId\":\"\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}}";
        HttpResponse<String> response = postJson("/api/cart", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_PostCart_ProductIdEmptyString() throws Exception {
        String jsonBody = "{\"userId\":\"test-user\",\"item\":{\"productId\":\"\",\"quantity\":1}}";
        HttpResponse<String> response = postJson("/api/cart", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_PostCart_QuantityZero() throws Exception {
        String jsonBody = "{\"userId\":\"test-user\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":0}}";
        HttpResponse<String> response = postJson("/api/cart", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_PostCart_QuantityNegative() throws Exception {
        String jsonBody = "{\"userId\":\"test-user\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":-1}}";
        HttpResponse<String> response = postJson("/api/cart", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_PostCart_ItemFieldNull() throws Exception {
        String jsonBody = "{\"userId\":\"test-user\",\"item\":null}";
        HttpResponse<String> response = postJson("/api/cart", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Checkout_UserIdEmptyString() throws Exception {
        String jsonBody = "{\"userId\":\"\",\"userCurrency\":\"USD\", \"address\":{\"streetAddress\":\"123 Main St\",\"state\":\"CA\",\"country\":\"USA\",\"city\":\"San Francisco\",\"zipCode\":\"94105\"}, \"email\":\"test@example.com\", \"creditCard\":{\"creditCardNumber\":\"1234567890123456\",\"creditCardCvv\":123,\"creditCardExpirationYear\":2025,\"creditCardExpirationMonth\":1}}";
        HttpResponse<String> response = postJson("/api/checkout", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Checkout_InvalidCurrencyCode() throws Exception {
        String jsonBody = "{\"userId\":\"test-user\",\"userCurrency\":\"ZZZ\", \"address\":{\"streetAddress\":\"123 Main St\",\"state\":\"CA\",\"country\":\"USA\",\"city\":\"San Francisco\",\"zipCode\":\"94105\"}, \"email\":\"test@example.com\", \"creditCard\":{\"creditCardNumber\":\"1234567890123456\",\"creditCardCvv\":123,\"creditCardExpirationYear\":2025,\"creditCardExpirationMonth\":1}}";
        HttpResponse<String> response = postJson("/api/checkout", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Checkout_EmptyCreditCardNumber() throws Exception {
        String jsonBody = "{\"userId\":\"test-user\",\"userCurrency\":\"USD\", \"address\":{\"streetAddress\":\"123 Main St\",\"state\":\"CA\",\"country\":\"USA\",\"city\":\"San Francisco\",\"zipCode\":\"94105\"}, \"email\":\"test@example.com\", \"creditCard\":{\"creditCardNumber\":\"\",\"creditCardCvv\":123,\"creditCardExpirationYear\":2025,\"creditCardExpirationMonth\":1}}";
        HttpResponse<String> response = postJson("/api/checkout", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Checkout_CreditCardExpired() throws Exception {
        String jsonBody = "{\"userId\":\"test-user\",\"userCurrency\":\"USD\", \"address\":{\"streetAddress\":\"123 Main St\",\"state\":\"CA\",\"country\":\"USA\",\"city\":\"San Francisco\",\"zipCode\":\"94105\"}, \"email\":\"test@example.com\", \"creditCard\":{\"creditCardNumber\":\"1234567890123456\",\"creditCardCvv\":123,\"creditCardExpirationYear\":2000,\"creditCardExpirationMonth\":1}}";
        HttpResponse<String> response = postJson("/api/checkout", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Currency_UnknownFromCode() throws Exception {
        String jsonBody = "{\"from\":{\"currencyCode\":\"ZZZ\",\"units\":10,\"nanos\":0},\"toCode\":\"USD\"}";
        HttpResponse<String> response = postJson("/api/currency", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Currency_UnknownToCode() throws Exception {
        String jsonBody = "{\"from\":{\"currencyCode\":\"USD\",\"units\":10,\"nanos\":0},\"toCode\":\"ZZZ\"}";
        HttpResponse<String> response = postJson("/api/currency", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Currency_UnitsNegative() throws Exception {
        String jsonBody = "{\"from\":{\"currencyCode\":\"USD\",\"units\":-1,\"nanos\":0},\"toCode\":\"EUR\"}";
        HttpResponse<String> response = postJson("/api/currency", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Shipping_AddressAllEmptyStrings() throws Exception {
        String jsonBody = "{\"address\":{\"streetAddress\":\"\",\"state\":\"\",\"country\":\"\",\"city\":\"\",\"zipCode\":\"\"}, \"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}";
        HttpResponse<String> response = postJson("/api/shipping", jsonBody);
        assertNoServerError(response);
    }

    @Test
    void test_R1_Recommendations_NonExistentProductIds() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=FAKE999&sessionId=s1&currencyCode=USD");
        assertNoServerError(response);
    }
}