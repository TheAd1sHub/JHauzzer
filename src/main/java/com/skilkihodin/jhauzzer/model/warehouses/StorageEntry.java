package com.skilkihodin.jhauzzer.model.warehouses;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "stored_products")
@Getter @Setter
public final class StorageEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name") // Formerly  product_id: int
    private String name;

    @Column(name = "type") // Formerly  product_type: ProductGroup
    @Enumerated(EnumType.STRING)
    private ProductGroup type;

    @Column(name = "quality")
    @Enumerated(EnumType.STRING)
    private ProductQuality quality;

    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private float price;

    @Column(name = "discount_per_cent")
    private float discount;

    @Column(name = "vip_discount_per_cent")
    private float vipDiscount;
}
