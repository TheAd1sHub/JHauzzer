package com.skilkihodin.jhauzzer.model.warehouses;

import lombok.Data;

@Data
public final class GoodsFilterDto {

    private String goodName;
    private String goodType;
    private String goodQuality;
    private Integer warehouseId;
    private Integer quantity;

}
