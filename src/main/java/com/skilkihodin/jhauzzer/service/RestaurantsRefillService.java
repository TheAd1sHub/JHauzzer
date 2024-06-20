package com.skilkihodin.jhauzzer.service;

import com.orioninc.ProjectRestaurants.DTO.product.ProductDTO;
//import com.orioninc.ProjectRestaurants.DTO.product.ProductDTOMapper;
//import com.orioninc.ProjectRestaurants.DTO.product.ProductRequestDTOMapper;
import com.skilkihodin.jhauzzer.misc.CatFact;
import com.skilkihodin.jhauzzer.misc.printers.CatFactPrinter;
import com.skilkihodin.jhauzzer.misc.printers.Printer;
import com.skilkihodin.jhauzzer.misc.printers.RestaurantProductPrinter;
import lombok.AllArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;

@Service
@AllArgsConstructor
public final class RestaurantsRefillService {

    private final String restaurantsAddressBaseUrl = "http://10.1.11.25:8080"; //"http://10.1.11.25";
    private final WebClient.Builder builder = WebClient.builder();

    @Scheduled(cron = "*/10 * * * * *")
    public void tryGetProductsState() {
        try {
            getProductsState();
        } catch (WebClientRequestException ex) {
            System.out.println("Unable to access the restaurants server."); // TODO: Change to normal logging
        }
    }

    public void getProductsState() throws WebClientRequestException {
        List<ProductDTO> products = builder
                .build()
                .get()
                .uri("http://10.1.11.25:8080/api/restaurants/2/products/get")
                //.uri("http://localhost:8080/api/v1/accounts/")
                .retrieve()
                .bodyToFlux(ProductDTO.class)
                .collectList()
                .block();

        if (products == null) {

            System.out.println("Nothing was received");
            return;
        }

        Printer<ProductDTO> printer = new RestaurantProductPrinter();
        System.out.println("-------------");
        products.forEach(printer::print);
        System.out.println("-------------");
    }

    //@Scheduled(cron = "*/15 * * * * *")
    public void getCatFacts() {
        CatFact fact = builder.build()
                .get()
                .uri("https://catfact.ninja/fact")
                .retrieve()
                .bodyToMono(CatFact.class)
                .block();

        if (fact == null) {

            System.out.println("No fact was received");
            return;
        }

        new CatFactPrinter().print(fact);
    }
}
