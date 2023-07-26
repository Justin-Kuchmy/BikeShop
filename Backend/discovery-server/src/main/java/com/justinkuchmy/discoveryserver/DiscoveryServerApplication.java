package com.justinkuchmy.discoveryserver;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@EnableEurekaServer
public class DiscoveryServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(DiscoveryServerApplication.class, args);
	}

}

@RestController
class ServiceInstanceRestController {

	@Autowired
	private DiscoveryClient discoveryClient;

    @RequestMapping("/get-service/{applicationName}")
	public int serviceInstancesByApplicationName(@PathVariable String applicationName) {
        //Used to simulate balance loader logic, if there are multiple
        //ports and instances of the same service.
		var res = this.discoveryClient.getInstances(applicationName);
        int selectedPort = 0;
        if(!res.isEmpty())
        {
            Random rand = new Random();
            int randIndex  = rand.nextInt(res.size());
            selectedPort = res.get(randIndex).getPort();
        }
        return selectedPort;
	}

    @RequestMapping("/all-services")
	public List<String> serviceInstancesList() {
        return this.discoveryClient.getServices();
	}

    
}
