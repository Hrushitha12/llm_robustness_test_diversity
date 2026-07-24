package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class TeaStore_ModelE_fewshot_R2_RobustnessTest extends TeaStoreBaseTest {

    @Test
    void test_R1_category_negative_id() throws Exception {
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/category?id=-1");
        assertNoServerError(r);
    }

    @Test
    void test_R1_category_non_integer_id() throws Exception {
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/category?id=notanumber");
        assertNoServerError(r);
    }

    @Test
    void test_R1_product_negative_id() throws Exception {
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/product?id=-1");
        assertNoServerError(r);
    }

    @Test
    void test_R1_product_non_integer_id() throws Exception {
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/product?id=notanumber");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cart_add_negative_productid() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction", "addToCart=&productid=-1");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cart_remove_nonexistent_product() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction", "removeProduct=&productid=999999");
        assertNoServerError(r);
    }

    @Test
    void test_R1_login_empty_credentials() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/loginAction", "username=&password=");
        assertNoServerError(r);
    }

    @Test
    void test_R1_order_empty_body() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/order", "");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cartAction_non_booleanaddToCart() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction", "addToCart=maybe&productid=1");
        assertNoServerError(r);
    }

    @Test
    void test_R1_category_missing_id() throws Exception {
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/category?");
        assertNoServerError(r);
    }

    @Test
    void test_R1_product_missing_id() throws Exception {
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/product?");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cartAction_missingaddToCart() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction", "productid=1");
        assertNoServerError(r);
    }

    @Test
    void test_R1_login_empty_username() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/loginAction", "username=&password=password");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cart_add_existing_product() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction", "addToCart=true&productid=1");
        // assume product 1 exists
        assertNoServerError(r);
    }

    @Test
    void test_R1_order_empty_confirm() throws Exception {
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/order", "confirm=");
        assertNoServerError(r);
    }
}