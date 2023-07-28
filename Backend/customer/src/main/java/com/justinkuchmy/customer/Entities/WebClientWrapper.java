package com.justinkuchmy.customer.Entities;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientWrapper {
    private WebClient webClient;
    public WebClientWrapper() {}
     public Order sendPostRequest(ServiceInstance serviceInstance, Order order) {
      this.webClient = WebClient.builder().baseUrl("http://" +serviceInstance.getHost()+":" +serviceInstance.getPort()).build();
      var results =   this.webClient
          .post()
          .uri("/api/v1/order")
          .bodyValue(order)
          .retrieve()
          .bodyToMono(Order.class)
          .block();
        return results;
    }

}