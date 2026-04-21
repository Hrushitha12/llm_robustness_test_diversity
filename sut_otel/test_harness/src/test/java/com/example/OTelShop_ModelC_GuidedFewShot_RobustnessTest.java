package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class OTelShop_ModelC_GuidedFewShot_RobustnessTest extends OTelShopBaseTest {

    @Test
    public void test_R1_ProductNullId() throws Exception {
        // String mutation: replace by null value
        HttpResponse<String> response = get("/api/products/");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddFieldAbsentUserId() throws Exception {
        // Structural mutation: omit required field from JSON body
        String body = "{\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":2}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddInvalidUserId() throws Exception {
        // String mutation: replace by predefined string
        String body = "{\"userId\":\"invalid_user\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":2}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddLargeProductId() throws Exception {
        // String mutation: add characters to overflow the maximum expected size
        String productId = "A".repeat(256); // Assuming max length of 255 for productId
        String body = "{\"userId\":\"user-1\",\"item\":{\"productId\":\"" + productId + "\",\"quantity\":2}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddZeroQuantity() throws Exception {
        // Number mutation: replace by 0
        String body = "{\"userId\":\"user-1\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":0}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
 void test_R1_CartAddNegativeQuantity() throws Exception {
        // Number mutation: replace by -1
        String body = "{\"userId\":\"user-1\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":-2}}";
        HttpResponse<String> response = postJson("/api/cart", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddEmptyAddress() throws Exception {
        // Structural mutation: send empty JSON object where populated object is expected
        String body = "{\"userId\":\"user-1\",\"address\":{}}";
        HttpResponse<String> response = postJson("/api/checkout", body);
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartAddInvalidCreditCardNumber() throws Exception {
        // String mutation: replace by predefined string with nonprintable characters
        String body = "{\"userId\":\"user-1\",\"creditCard\":{\"creditCardNumber\":\"\\x01\",\"creditCardCvv\":123,\"creditCardExpirationYear\":2025,\"creditCardExpirationMonth\":9}}";
        HttpResponse<String> response = postJson("/api/checkout", body);
        assertNoServerError(response);
    }

    @Test
 public void test_R1_CurrencyAddNonPrintableCharacters() throws Exception {
        // String mutation: add nonprintable characters to the string
        String currencyCode = "USD\u0007";
        String body = "{\"from\":{\"currencyCode\":\"" + currencyCode + "\",\"units\":5,\"nanos\":500},\"toCode\":\"EUR\"}";
        HttpResponse<String> response = postJson("/api/currency", body);
        assertNoServerError(response);
    }

    @Test
 public void test_R1_ShippingAddInvalidAddress() throws Exception {
        // Structural mutation: send field with invalid values in the list
        String body = "{\"address\":{\"streetAddress\":\"invalid\",\"state\":\"invalid\",\"country\":\"invalid\",\"city\":\"invalid\",\"zipCode\":\"invalid\"},\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":2}]}";
        HttpResponse<String> response = postJson("/api/shipping", body);
        assertNoServerError(response);
    }

    @Test
 public void test_R1_RecommendationsEmptyProductIds() throws Exception {
        // List mutation: remove all elements from the list (empty list)
        String url = "/api/recommendations?productIds=&sessionId=abc&currencyCode=USD";
        HttpResponse<String> response = get(url);
        assertNoServerError(response);
    }

    @Test
 public void test_R1_AdsNullContextKeys() throws Exception {
        // Structural mutation: send field with null value where string is expected
        String url = "/api/ads?contextKeys=";
        HttpResponse<String> response = get(url);
        assertNoServerError(response);
    }
}