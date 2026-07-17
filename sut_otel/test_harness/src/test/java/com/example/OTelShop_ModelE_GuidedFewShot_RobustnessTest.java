package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class OTelShop_ModelE_GuidedFewShot_RobustnessTest extends OTelShopBaseTest {

    @Test
    public void test_R1_ProductNullId() throws Exception {
        // String mutation: replace by null value
        HttpResponse<String> response = get("/api/products/null");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ProductNonPrintableCharsId() throws Exception {
        // String mutation: add nonprintable characters to the string
        HttpResponse<String> response = get("/api/products/OLJCESPC7Z%00");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartMissingSessionId2() throws Exception {
        // Structural mutation: omit required parameter entirely (alternative)
        HttpResponse<String> response = delete("/api/cart");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddEmptyUserId() throws Exception {
        // String mutation: replace by empty string
        String body = "{\"userId\":\"\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddNullUserId() throws Exception {
        // Structural mutation: send null where object is expected
        String body = "{\"userId\":null,\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddOverflowQuantity() throws Exception {
        // Number mutation: replace by the maximum number valid for the type plus one (overflow)
        String body = "{\"userId\":\"user-1\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":2147483648}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CurrencyNullFrom() throws Exception {
        // Structural mutation: send null where object is expected
        String body = "{\"from\":null,\"toCode\":\"EUR\"}";
        HttpResponse<String> response = postJson("/api/currency", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CurrencyEmptyToCode() throws Exception {
        // String mutation: replace by empty string
        String body = "{\"from\":{\"currencyCode\":\"USD\",\"units\":10,\"nanos\":0},\"toCode\":\"\"}";
        HttpResponse<String> response = postJson("/api/currency", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ShippingNullAddress() throws Exception {
        // Structural mutation: send null where object is expected
        String body = "{\"address\":null,\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}";
        HttpResponse<String> response = postJson("/api/shipping", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ShippingEmptyItemsList2() throws Exception {
        // List mutation: remove all elements from the list (alternative)
        String body = "{\"address\":{\"streetAddress\":\"1 Main St\",\"state\":\"CA\","
            + "\"country\":\"US\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},\"items\":[{}]}";
        HttpResponse<String> response = postJson("/api/shipping", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CheckoutNullUser() throws Exception {
        // Structural mutation: send null where object is expected
        String body = "{\"userId\":null,\"userCurrency\":\"USD\",\"address\":{\"streetAddress\":\"1 Main St\","
            + "\"state\":\"CA\",\"country\":\"US\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},"
            + "\"email\":\"user@example.com\",\"creditCard\":{\"creditCardNumber\":\"1234567890123456\","
            + "\"creditCardCvv\":123,\"creditCardExpirationYear\":2025,\"creditCardExpirationMonth\":12}}";
        HttpResponse<String> response = postJson("/api/checkout", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CheckoutEmptyUserCurrency() throws Exception {
        // String mutation: replace by empty string
        String body = "{\"userId\":\"user-1\",\"userCurrency\":\"\",\"address\":{\"streetAddress\":\"1 Main St\","
            + "\"state\":\"CA\",\"country\":\"US\",\"city\":\"Springfield\",\"zipCode\":\"12345\"},"
            + "\"email\":\"user@example.com\",\"creditCard\":{\"creditCardNumber\":\"1234567890123456\","
            + "\"creditCardCvv\":123,\"creditCardExpirationYear\":2025,\"creditCardExpirationMonth\":12}}";
        HttpResponse<String> response = postJson("/api/checkout", body);
        assertNoServerError(response);
    }
}