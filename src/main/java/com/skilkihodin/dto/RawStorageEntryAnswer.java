package com.skilkihodin.dto;

import lombok.Data;

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

}
