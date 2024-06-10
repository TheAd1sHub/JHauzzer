package com.skilkihodin.jhauzzer.data.warehouses;

public enum ProductQuality {
    PREMIUM(0),
    NORMAL(1),
    LOW_GRADE(2);

    public final int ID;

    ProductQuality(int id) {
        ID = id;
    }
}
