package com.example.dcbot.Controller;

import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class TwitterParserController {

    @GetMapping("/TwitterParser")
    public String TwitterParser() {
        String twitterUrl = "https://twitter.com/patrietta/status/1759138849429860583";
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.exchange(twitterUrl, HttpMethod.POST, null, String.class);
        String responseHeader = response.getHeaders().toString();

        return responseHeader;
    }

}
