package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class TeaStore_ModelD_ZeroShot_R2_RobustnessTest extends TeaStoreBaseTest {

    @Test
    public void test_R1_GetRoot() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_GetCategoryWithValidId() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_GetCategoryWithInvalidId() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=-1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_GetProductWithValidId() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_GetProductWithInvalidId() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=-1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_GetCart() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/cart");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_GetProfile() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/profile");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_LoginWithValidCredentials() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=admin&password=123456");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_LoginWithInvalidCredentials() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=invalid&password=wrong");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_AddProductToCartWithValidId() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_AddProductToCartWithInvalidId() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=-1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_RemoveProductFromCartWithValidId() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "removeProduct=&productid=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_RemoveProductFromCartWithInvalidId() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "removeProduct=&productid=-1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ConfirmOrder() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/order", "confirm=");
        assertNoServerError(response);
    }
}