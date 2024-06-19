package com.skilkihodin.dto;

import lombok.Data;

/**
 * Form factor to store data sent in a request to API
 */
@Data
public final class RawStorageEntryPost {

    private Integer id;
    private String  name;
    private String  type;
    private String  quality;
    private Integer warehouseId;
    private Integer quantity;
    private Float   price;
    private Float   discount;     //  %
    private Float   vipDiscount;  //  %

}
