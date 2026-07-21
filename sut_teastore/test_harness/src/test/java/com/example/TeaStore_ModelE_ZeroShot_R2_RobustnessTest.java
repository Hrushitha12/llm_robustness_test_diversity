package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class TeaStore_ModelE_ZeroShot_R2_RobustnessTest extends TeaStoreBaseTest {

    @Test
    public void test_R1_GetRoot() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/");
        assertNoServerError(response);
    }

    @Test
    public void test_R2_GetCategory() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R3_GetProduct() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R4_GetCart() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/cart");
        assertNoServerError(response);
    }

    @Test
    public void test_R5_GetProfile() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/profile");
        assertNoServerError(response);
    }

    @Test
    public void test_R6_LoginActionValidCredentials() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=user&password=password");
        assertNoServerError(response);
    }

    @Test
    public void test_R7_LoginActionInvalidCredentials() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=wronguser&password=wrongpassword");
        assertNoServerError(response);
    }

    @Test
    public void test_R8_AddToCart() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R9_RemoveFromCart() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "removeProduct=&productid=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R10_ConfirmOrder() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/order", "confirm=");
        assertNoServerError(response);
    }

    @Test
    public void test_R11_GetCategoryInvalidId() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=abc");
        assertNoServerError(response);
    }

    @Test
    public void test_R12_GetProductInvalidId() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=def");
        assertNoServerError(response);
    }

    @Test
    public void test_R13_AddToCartInvalidProductId() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=ghi");
        assertNoServerError(response);
    }

    @Test
    public void test_R14_RemoveFromCartInvalidProductId() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "removeProduct=&productid=jkl");
        assertNoServerError(response);
    }

    @Test
    public void test_R15_LoginActionMissingCredentials() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "");
        assertNoServerError(response);
    }
}