package com.skilkihodin.jhauzzer.dto;

import lombok.Data;

import java.sql.Date;

/**
 * Form factor to store data sent in a request to API
 */
@Data
public final class RawStorageEntryPost {

    private Integer id              = null;
    private String  name            = null;
    private String  type            = null;
    private String  quality         = null;
    private Integer warehouseId     = null;
    private Integer quantity        = null;
    private Float   price           = null;
    private Float   discount        = null;  //  %
    private Float   vipDiscount     = null;  //  %
    private Date    productionDate  = null;
    private Integer lifetimeDays    = null;

}