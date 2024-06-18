package com.skilkihodin.dto;

import lombok.Data;

@Data
public class RawStorageEntry {

    private String name;
    private String type;
    private String quality;
    private int quantity;
    private float price;

}
