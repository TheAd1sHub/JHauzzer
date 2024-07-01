package com.skilkihodin.jhauzzer.config;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

//@Configuration
public class RestaurantProductMappingConfig {

    @Value("10.1.11.25")
    private String serverIp;

    @Value("${serverIp}/api/restaurants/")
    private String restarauntsAddressBaseUrl = "";

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }


    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .baseUrl(restarauntsAddressBaseUrl)
                .build();
    }
}
