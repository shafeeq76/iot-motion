package com.iot.motion;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@EnableAutoConfiguration
@SpringBootApplication
public class IotMotionApplication {

	@RequestMapping("/iot-motion")
    @ResponseBody
    String home() {
        return "<h1><font color='GREEN'> Welcome to World of IOT Motion Detection Project!</font> </h1>";
    }
	
	public static void main(String[] args) {
		SpringApplication.run(IotMotionApplication.class, args);
	}
}
