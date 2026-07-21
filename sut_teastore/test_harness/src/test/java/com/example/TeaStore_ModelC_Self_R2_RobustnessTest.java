package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class TeaStore_ModelC_Self_R2_RobustnessTest extends TeaStoreBaseTest {

    @Test
    void test_R1_01() throws Exception {
        HttpResponse<String> response = get("/?invalid=abc");
        assertNoServerError(response);
    }

    @Test
    void test_R1_02() throws Exception {
        HttpResponse<String> response = get("/category?id=abc");
        assertNoServerError(response);
    }

    @Test
    void test_R1_03() throws Exception {
        HttpResponse<String>响应 = get("/product?id=abc");
        assertNoServerError(响应);
    }

    @Test
    void test_R1_04() throws Exception {
        HttpResponse<String> response = get("/cart?invalid=abc");
        assertNoServerError(response);
    }

    @Test
    void test_R1_05() throws Exception {
        HttpResponse<String> response = get("/profile?invalid=abc");
        assertNoServerError(response);
    }

    @Test
    void test_R1_06() throws Exception {
        HttpResponse<String> response = post("/loginAction", "username=&password=");
        assertNoServerError(response);
    }

    @Test
    void test_R1_07() throws Exception {
        HttpResponse<String> response = post("/loginAction", "username=abc&password=123");
        assertNoServerError(response);
    }

    @Test
    void test_R1_08() throws Exception {
        HttpResponse<String> response = post("/cartAction", "addToCart=&productid=abc");
        assertNoServerError(response);
    }

    @Test
    void test_R1_09() throws Exception {
        HttpResponse<String> response = post("/cartAction", "removeProduct=&productid=abc");
        assertNoServerError(response);
    }

    @Test
    void test_R1_10() throws Exception {
        HttpResponse<String> response = post("/order", "confirm=abc");
        assertNoServerError(response);
    }

    @Test
    void test_R1_11() throws Exception {
        HttpResponse<String> response = get("/category?id=-1");
        assertNoServerError(response);
    }

    @Test
    void test_R1_12() throws Exception {
        HttpResponse<String> response = get("/product?id=-1");
        assertNoServerError(response);
    }

    @Test
    void test_R1_13() throws Exception {
        HttpResponse<String> response = post("/loginAction", "username=" + "a".repeat(1000) + "&password=" + "a".repeat(1000));
        assertNoServerError(response);
    }

    @Test
    void test_R1_14() throws Exception {
        HttpResponse<String> response = post("/cartAction", "addToCart=&productid=0");
        assertNoServerError(response);
    }

    @Test
    void test_R1_15() throws Exception {
        HttpResponse<String> response = post("/cartAction", "removeProduct=&productid=0");
        assertNoServerError(response);
    }

    @Test
    void test_R1_16() throws Exception {
        HttpResponse<String> response = post("/order", "confirm=");
        assertNoServerError(response);
    }

    @Test
    void test_R1_17() throws Exception {
        HttpResponse<String> response = post("/loginAction", "username=abc");
        assertNoServerError(response);
    }

    @Test
    void test_R1_18() throws Exception {
        HttpResponse<String> response = post("/loginAction", "password=123");
        assertNoServerError(response);
    }
}

