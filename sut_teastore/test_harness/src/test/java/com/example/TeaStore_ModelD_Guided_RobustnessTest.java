package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class TeaStore_ModelD_Guided_RobustnessTest extends TeaStoreBaseTest {

    @Test
    public void test_R1_CategoryIdNull() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CategoryIdEmpty() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CategoryIdNegativeOne() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=-1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CategoryIdZero() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=0");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CategoryIdOne() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/category?id=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ProductIdNull() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ProductIdEmpty() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ProductIdNegativeOne() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=-1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ProductIdZero() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=0");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_ProductIdOne() throws Exception {
        HttpResponse<String> response = get("/tools.descartes.teastore.webui/product?id=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_LoginActionUsernameNull() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "password=password");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_LoginActionUsernameEmpty() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=&password=password");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_LoginActionUsernameNonPrintable() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=\u0000&password=password");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_LoginActionUsernameOverflow() throws Exception {
        StringBuilder longString = new StringBuilder("a".repeat(1000));
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=" + longString.toString() + "&password=password");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_LoginActionPasswordNull() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=username");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_LoginActionPasswordEmpty() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/loginAction", "username=username&password=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartActionProductIdNull() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartActionProductIdEmpty() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartActionProductIdNegativeOne() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=-1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartActionProductIdZero() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=0");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_CartActionProductIdOne() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=1");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_OrderConfirmNull() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/order", "confirm=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_OrderConfirmEmpty() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/order", "confirm=");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_OrderConfirmNonPrintable() throws Exception {
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/order", "confirm=\u0000");
        assertNoServerError(response);
    }

    @Test
    public void test_R1_OrderConfirmOverflow() throws Exception {
        StringBuilder longString = new StringBuilder("a".repeat(1000));
        HttpResponse<String> response = post("/tools.descartes.teastore.webui/order", "confirm=" + longString.toString());
        assertNoServerError(response);
    }
}