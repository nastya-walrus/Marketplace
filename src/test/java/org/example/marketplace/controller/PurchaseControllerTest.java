package org.example.marketplace.controller;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.example.marketplace.BaseIntegrationTest;
import org.example.marketplace.utils.JsonHelper;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PurchaseControllerTest extends BaseIntegrationTest {

    private final String BASE_PATH = "http://localhost:8085/";

    @Test
    @DatabaseSetup({
            "/dataset/input/buyers/createFiveBuyers.xml",
            "/dataset/input/products/createFiveProducts.xml"
    })
    void buy() throws Exception {

        String actualResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_PATH + "/purchase/products/1/buy/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        JSONAssert.assertEquals(
                JsonHelper.getJson("/jsons/expected/purchases/buy.json"),
                actualResponse,
                JSONCompareMode.STRICT);
    }

    @Test
    @DatabaseSetup({
            "/dataset/input/buyers/createFiveBuyers.xml",
            "/dataset/input/products/createFiveProducts.xml"
    })
    void buy_shouldFail_ifNotEnoughMoneyOrBuyerNotFound() throws Exception {

        String actualResponse = mockMvc.perform(MockMvcRequestBuilders
                        .post(BASE_PATH + "/purchase/products/4/buy/5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isInternalServerError())
                .andReturn()
                .getResponse()
                .getContentAsString(StandardCharsets.UTF_8);
        assertEquals(
                "Not enough money or buyer not found",
                actualResponse);
    }
}