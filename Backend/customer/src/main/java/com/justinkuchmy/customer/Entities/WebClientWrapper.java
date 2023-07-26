package com.justinkuchmy.customer.Entities;

import java.util.Arrays;
import java.util.List;

import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
public class WebClientWrapper {
    private WebClient webClient;
    public WebClientWrapper(ServiceInstance serviceInstance) {
    this.webClient = WebClient.builder().baseUrl("http://" +serviceInstance.getHost()+":" +serviceInstance.getPort()).build();
    }
     public List<Order> sendPostRequest(ServiceInstance serviceInstance, List<Order> orders) {
      var results =  webClient
          .post()
          .uri("/api/v1/order")
          .bodyValue(orders)
          .retrieve()
          .bodyToMono(Order[].class)
          .block();
        return Arrays.asList(results);
    }

}