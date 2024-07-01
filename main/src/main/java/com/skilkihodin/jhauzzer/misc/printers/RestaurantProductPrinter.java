package com.skilkihodin.jhauzzer.misc.printers;

import com.orioninc.ProjectRestaurants.DTO.product.ProductDTO;

public final class RestaurantProductPrinter implements Printer<ProductDTO> {


    @Override
    public void print(ProductDTO product) {
        System.out.printf(
                "Product: %s\nPrice: %.2f Eur\n%.2f units in stock at Restaurant #%d%n",
                product.productName(),
                product.productPrice(),
                product.productBalance(),
                product.restaurant()
        );
    }
}
