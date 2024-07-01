package com.skilkihodin.jhauzzer.model.warehouses;

import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public enum ProductGroup {
    NONE(-1),
    GROCERY(1),
    DAIRY(2),
    MEAT(3),
    FISH(4),
    SPICES(5),
    LIQUID(6);

    public final int ID;

    ProductGroup(int id) {
        ID = id;
    }

    /**
     *
     * @param name name of the desired enum value
     * @return enum value with the given name or {@code ProductGroup.NONE} if the value with given name was not found
     */
    @Contract("_->_")
    public static ProductGroup fromString(@NotNull String name) {
        for (ProductGroup groupType : ProductGroup.values()) {
            if (groupType.name().equals(name)) {
                return groupType;
            }
        }

        return NONE;
    }
}