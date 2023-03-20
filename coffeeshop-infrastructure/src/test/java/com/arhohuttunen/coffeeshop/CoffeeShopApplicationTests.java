package com.arhohuttunen.coffeeshop;

import com.arhohuttunen.coffeeshop.application.CoffeeMachine;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CoffeeShopApplicationTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private CoffeeMachine coffeeMachine;

    @Test
    void processNewOrder() throws Exception {
        var orderId = createOrder();
        payOrder(orderId);
        prepareOrder(orderId);
        readReceipt(orderId);
        getReceipt(orderId);
    }

    @Test
    void cancelOrderBeforePayment() throws Exception {
        var orderId = createOrder();
        cancelOrder(orderId);
    }

    private UUID createOrder() throws Exception {
        var location = mockMvc.perform(post("/order")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "location": "IN_STORE",
                            "items": [{
                                "drink": "LATTE",
                                "quantity": 1,
                                "milk": "WHOLE",
                                "size": "LARGE"
                            }]
                        }
                        """))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getHeader(HttpHeaders.LOCATION);

        return location == null ? null : UUID.fromString(location.substring(location.lastIndexOf("/") + 1));
    }

    private void cancelOrder(UUID orderId) throws Exception {
        mockMvc.perform(delete("/order/{id}", orderId))
                .andExpect(status().isNoContent());
    }

    private void payOrder(UUID orderId) throws Exception {
        mockMvc.perform(put("/payment/{id}", orderId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content("""
                        {
                            "cardHolderName": "Michael Faraday",
                            "cardNumber": "11223344",
                            "expiryMonth": 12,
                            "expiryYear": 2023
                        }
                        """))
                .andExpect(status().isOk());
    }

    private void prepareOrder(UUID orderId) {
        coffeeMachine.startPreparingOrder(orderId);
        coffeeMachine.finishPreparingOrder(orderId);
    }

    private void readReceipt(UUID orderId) throws Exception {
        mockMvc.perform(get("/receipt/{id}", orderId))
                .andExpect(status().isOk());
    }

    private void getReceipt(UUID orderId) throws Exception {
        mockMvc.perform(delete("/receipt/{id}", orderId))
                .andExpect(status().isOk());
    }
}
