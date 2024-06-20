package com.skilkihodin.jhauzzer.dto;

import lombok.Data;

import java.sql.Date;

/**
 * Form factor to store data received from API request
 */
@Data
public final class RawStorageEntryAnswer {

    private Integer id;
    private String  name;
    private String  type;
    private String  quality;
    private Integer quantity;
    private Float   price;
    private Date productionDate;
    private Integer lifetimeDays;

}
