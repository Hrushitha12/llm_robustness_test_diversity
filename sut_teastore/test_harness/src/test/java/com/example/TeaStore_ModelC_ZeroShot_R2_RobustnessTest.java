package com.example;

import org.junit.jupiter.api.Test;
import java.net.http.HttpResponse;

public class TeaStore_ModelC_ZeroShot_R2_RobustnessTest extends TeaStoreBaseTest {

    @Test
    void test_R1_GetMainPage() throws Exception {
        HttpResponse<String> response = get("/");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GetCategoryValid() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=1");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GetCategoryInvalid() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=999");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GetProductValid() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=1");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GetProductInvalid() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=999");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GetCartUnauthenticated() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/cart");
        assertNoServerError(response);
    }

    @Test
    void test_R1_GetProfileUnauthenticated() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/profile");
        assertNoServerError(response);
    }

    @Test
    void test_R1_LoginValid() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=testuser&password=testpass");
        assertNoServerError(response);
    }

    @Test
    void test_R1_LoginInvalidPassword() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=testuser&password=wrongpass");
        assertNoServerError(response);
    }

    @Test
    void test_R1_LoginNonExistentUser() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=nonexistent&password=testpass");
        assertNoServerError(response);
    }

    @Test
    void test_R1_AddToCartValid() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=1");
        assertNoServerError(response);
    }

    @Test
    void test_R1_AddToCartInvalidProduct() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=999");
        assertNoServerError(response);
    }

    @Test
    void test_R1_RemoveProductValid() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "removeProduct=&productid=1");
        assertNoServerError(response);
    }

    @Test
    void test_R1_RemoveProductInvalid() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "removeProduct=&productid=999");
        assertNoServerError(response);
    }

    @Test
    void test_R1_ConfirmOrderEmptyCart() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/order", "confirm=");
        assertNoServerError(response);
    }

    @Test
    void test_R1_ConfirmOrderWithItems() throws Exception {
        post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=1");
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/order", "confirm=");
        assertNoServerError(response);
    }

    @Test
    void test_R1_LoginMissingUsername() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "password=testpass");
        assertNoServerError(response);
    }

    @Test
    void test_R1_LoginMissingPassword() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=testuser");
        assertNoServerError(response);
    }
}