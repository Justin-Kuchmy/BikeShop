package com.justinkuchmy.order.Entities;

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
     public List<Orderitem> sendPostRequest(ServiceInstance serviceInstance, List<Orderitem> orderItems) {
      var res =  webClient
          .post()
          .uri("/api/v1/orderitem")
          .bodyValue(orderItems)
          .retrieve()
          .bodyToMono(Orderitem[].class)
          .block();
        return Arrays.asList(res);
    }

}
