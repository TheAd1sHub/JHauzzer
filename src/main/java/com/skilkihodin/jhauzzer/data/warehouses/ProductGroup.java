package com.skilkihodin.jhauzzer.data.warehouses;

public enum ProductGroup {
    GROCERY(1),
    DAIRY(2),
    MEAT(3),
    FISH(4),
    SPICES(5);

    public final int ID;

    ProductGroup(int id) {
        ID = id;
    }
}