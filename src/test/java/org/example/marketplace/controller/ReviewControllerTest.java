package org.example.marketplace.controller;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.example.marketplace.BaseIntegrationTest;
import org.example.marketplace.utils.JsonHelper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ReviewControllerTest extends BaseIntegrationTest {

    private final String BASE_PATH = "http://localhost:8085/";

    @Test
    @DatabaseSetup({
            "/dataset/input/buyers/createFiveBuyers.xml",
            "/dataset/input/products/createFiveProducts.xml",
            "/dataset/input/purchases/createOnePurchase.xml"
    })
    void review() throws Exception {
        String requestJson = JsonHelper.getJson("/jsons/input/reviews/review.json");

        String actualResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_PATH + "/review/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals(
                JsonHelper.getJson("/jsons/expected/reviews/review.json"),
                actualResponse,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("createdAt", (o1, o2) -> true)
                ));
    }

    @Test
    @DatabaseSetup({
            "/dataset/input/buyers/createFiveBuyers.xml",
            "/dataset/input/products/createFiveProducts.xml",
            "/dataset/input/purchases/createOnePurchase.xml"
    })
    void review_shouldFail_if() throws Exception {
        String requestJson = JsonHelper.getJson("/jsons/input/reviews/review.json");

        String actualResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_PATH + "/review/products/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals(
                JsonHelper.getJson("/jsons/expected/reviews/review.json"),
                actualResponse,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("createdAt", (o1, o2) -> true)
                ));
    }
}