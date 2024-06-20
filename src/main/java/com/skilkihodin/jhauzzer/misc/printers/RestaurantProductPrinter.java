package com.skilkihodin.jhauzzer.misc.printers;

import com.orioninc.ProjectRestaurants.DTO.product.ProductDTO;

public final class RestaurantProductPrinter implements Printer<ProductDTO> {


    @Override
    public void print(ProductDTO product) {
        System.out.println(
                String.format(
                        "Product: %s\nPrice: %f Eur\n%f units in stock at Restaurant #%d",
                        product.productName(),
                        product.productPrice(),
                        product.productBalance(),
                        product.restaurant()
                )
        );
    }
}
