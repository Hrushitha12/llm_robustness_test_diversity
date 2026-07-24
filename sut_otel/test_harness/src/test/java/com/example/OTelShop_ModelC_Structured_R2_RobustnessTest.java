package com.example;

import org.junit.jupiter.api.Test;

import java.net.http.HttpResponse;

public class OTelShop_ModelC_Structured_R2_RobustnessTest extends OTelShopBaseTest {

    @Test
    void test_R1_GET_products_nonexistent() throws Exception {
        HttpResponse<String> response = get("/api/products/DOESNOTEXIST999");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GET_products_empty_path() throws Exception {
        HttpResponse<String> response = get("/api/products/");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GET_products_numeric_id() throws Exception {
        HttpResponse<String> response = get("/api/products/12345");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GET_products_long_id() throws Exception {
        HttpResponse<String> response = get("/api/products/" + "a".repeat(1000));
        assertNoServerError(response);
    }

    @Test
    void test_R1_GET_cart_empty_session_id() throws Exception {
        HttpResponse<String> response = get("/api/cart?sessionId=");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GET_cart_missing_session_id() throws Exception {
        HttpResponse<String> response = get("/api/cart");
        assertNoServerError(response);
    }

    @Test
    void test_R1_DELETE_cart_empty_session_id() throws Exception {
        HttpResponse<String> response = get("/api/cart?sessionId=");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_cart_empty_user_id() throws Exception {
        HttpResponse<String> response = postJson("/api/cart", "{\"userId\":\"\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_cart_empty_product_id() throws Exception {
        HttpResponse<String> response = postJson("/api/cart", "{\"userId\":\"test\",\"item\":{\"productId\":\"\",\"quantity\":1}}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_cart_quantity_zero() throws Exception {
        HttpResponse<String> response = postJson("/api/cart", "{\"userId\":\"test\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":0}}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_cart_quantity_negative() throws Exception {
        HttpResponse<String> response = postJson("/api/cart", "{\"userId\":\"test\",\"item\":{\"productId\":\"OLJCESPC7Z\",\"quantity\":-1}}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_cart_item_null() throws Exception {
        HttpResponse<String> response = postJson("/api/cart", "{\"userId\":\"test\",\"item\":null}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_checkout_empty_user_id() throws Exception {
        HttpResponse<String> response = postJson("/api/checkout", "{\"userId\":\"\",\"userCurrency\":\"USD\",\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_checkout_invalid_currency() throws Exception {
        HttpResponse<String> response = postJson("/api/checkout", "{\"userId\":\"test\",\"userCurrency\":\"INVALID\",\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_checkout_credit_card_empty() throws Exception {
        HttpResponse<String> response = postJson("/api/checkout", "{\"userId\":\"test\",\"userCurrency\":\"USD\",\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}],\"creditCard\":\"\"}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_checkout_expiration_past() throws Exception {
        HttpResponse<String> response = postJson("/api/checkout", "{\"userId\":\"test\",\"userCurrency\":\"USD\",\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}],\"creditCard\":\"1234 5678 9012 3456\",\"expiration\":\"2020-01\"}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_currency_fromcode_unknown() throws Exception {
        HttpResponse<String> response = postJson("/api/currency", "{\"fromCode\":\"XYZ\",\"toCode\":\"USD\",\"amount\":100}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_currency_tocode_unknown() throws Exception {
        HttpResponse<String> response = postJson("/api/currency", "{\"fromCode\":\"USD\",\"toCode\":\"XYZ\",\"amount\":100}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_currency_units_negative() throws Exception {
        HttpResponse<String> response = postJson("/api/currency", "{\"fromCode\":\"USD\",\"toCode\":\"EUR\",\"amount\":-100}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_POST_shipping_empty_address() throws Exception {
        HttpResponse<String> response = postJson("/api/shipping", "{\"address\":{\"streetAddress\":\"\",\"state\":\"\",\"country\":\"\",\"city\":\"\",\"zipCode\":\"\"},\"items\":[{\"productId\":\"OLJCESPC7Z\",\"quantity\":1}]}");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GET_recommendations_fake_productids() throws Exception {
        HttpResponse<String> response = get("/api/recommendations?productIds=FAKE999&sessionId=s1&currencyCode=USD");
        assertNoServerError(response);
    }
}
