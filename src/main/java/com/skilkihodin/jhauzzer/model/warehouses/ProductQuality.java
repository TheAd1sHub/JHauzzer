package com.skilkihodin.jhauzzer.model.warehouses;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum ProductQuality {
    NONE(-1),
    PREMIUM(0),
    NORMAL(1),
    LOW_GRADE(2);

    public final int ID;

    ProductQuality(int id) {
        ID = id;
    }

    /**
     *
     * @param name name of the desired enum value
     * @return enum value with the given name or {@code ProductQuality.NONE} if the value with given name was not found
     */
    @Contract("_->_")
    public static ProductQuality fromString(@NotNull String name) {
        for (ProductQuality qualityType : ProductQuality.values()) {
            if (qualityType.name().equals(name)) {
                return qualityType;
            }
        }

        return NONE;
    }
}
