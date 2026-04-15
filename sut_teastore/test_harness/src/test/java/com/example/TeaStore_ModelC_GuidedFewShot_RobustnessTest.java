package com.example;

import java.net.http.HttpResponse;
import org.junit.jupiter.api.Test;

public class TeaStore_ModelC_GuidedFewShot_RobustnessTest extends TeaStoreBaseTest {

    @Test
    void test_R1_category_nullId() throws Exception {
        // Number mutation: replace by null value (omit the parameter key entirely)
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/category");
        assertNoServerError(r);
    }

    @Test
    void test_R1_product_emptyStringId() throws Exception {
        // Number mutation: replace by empty string
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/product?id=");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cartAdd_nonprintableProductId() throws Exception {
        // String mutation: add nonprintable characters to the string
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction",
            "addToCart=\u0000\u0001&productid=1");
        assertNoServerError(r);
    }

    @Test
    void test_R1_login_emptyStringPassword() throws Exception {
        // String mutation: replace by empty string
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/loginAction",
            "username=test&password=");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cartRemove_keyAbsentProductId() throws Exception {
        // Structural mutation: omit required parameter key entirely
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction",
            "removeProduct=");
        assertNoServerError(r);
    }

    @Test
    void test_R1_product_invalidIdOverflow() throws Exception {
        // Number mutation: replace by maximum value plus one (overflow)
        HttpResponse<String> r = get("/tools.descartes.teastore.webui/product?id=" + Integer.MAX_VALUE + 1);
        assertNoServerError(r);
    }

    @Test
    void test_R1_login_invalidUsername() throws Exception {
        // String mutation: replace by predefined string
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/loginAction",
            "username=invalid&password=test");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cartAdd_negativeOneProductId() throws Exception {
        // Number mutation: replace by -1
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction",
            "addToCart=1&productid=-1");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cartRemove_zeroProductId() throws Exception {
        // Number mutation: replace by 0
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction",
            "removeProduct=1&productid=0");
        assertNoServerError(r);
    }

    @Test
    void test_R1_login_nullPassword() throws Exception {
        // String mutation: replace by null value (omit the parameter key entirely)
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/loginAction",
            "username=test");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cartAdd_maxValueProductId() throws Exception {
        // Number mutation: replace by maximum number valid for the type
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction",
            "addToCart=1&productid=" + Integer.MAX_VALUE);
        assertNoServerError(r);
    }

    @Test
    void test_R1_profile_nonprintableUsername() throws Exception {
        // String mutation: replace by string with nonprintable characters
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/loginAction",
            "username=\u0000\u0001&password=test");
        assertNoServerError(r);
    }

    @Test
    void test_R1_cartRemove_keyPresentProductId() throws Exception {
        // Structural mutation: send a parameter with only its key and no value
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/cartAction",
            "removeProduct&productid=1");
        assertNoServerError(r);
    }

    @Test
    void test_R1_login_longUsername() throws Exception {
        // String mutation: add characters to overflow max size
        String longStr = "A".repeat(5000);
        HttpResponse<String> r = post("/tools.descartes.teastore.webui/loginAction",
            "username=" + longStr + "&password=test");
        assertNoServerError(r);
    }

}