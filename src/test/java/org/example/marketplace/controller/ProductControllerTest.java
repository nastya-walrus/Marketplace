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

class ProductControllerTest extends BaseIntegrationTest {

    private final String BASE_PATH = "http://localhost:8085/";

    @Test
    void create() throws Exception {
        String requestJson = JsonHelper.getJson("/jsons/input/products/create.json");

        String actualResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_PATH + "products")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestJson))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals(
                JsonHelper.getJson("/jsons/expected/products/create.json"),
                actualResponse,
                new CustomComparator(
                        JSONCompareMode.STRICT,
                        new Customization("createdAt", (o1, o2) -> true)
                ));
    }

    @Test
    @DatabaseSetup("/dataset/input/products/createFiveProducts.xml")
    void get() throws Exception {
        String actualResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get(BASE_PATH + "products/4"))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals(
                JsonHelper.getJson("/jsons/expected/products/get.json"),
                actualResponse,
                JSONCompareMode.STRICT);
    }
}