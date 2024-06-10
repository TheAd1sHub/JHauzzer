package com.skilkihodin.jhauzzer.data.warehouses;

import jakarta.persistence.*;

@Entity
@Table(name = "stored_products")
public final class StoragesContents {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "product_id")
    private int productId;

    @Column(name = "product_type")
    private String productType;

    @Column(name = "warehouse_id")
    private int warehouseId;

    @Column(name = "quality")
    @Enumerated(EnumType.STRING)
    private ProductQuality quality;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "price")
    private float price;


    public int getId() {
        return id;
    }
    public int getProductId() {
        return productId;
    }
    public String getProductType() {
        return productType;
    }
    public int getWarehouseId() {
        return warehouseId;
    }
    public int getQuantity() {
        return quantity;
    }
    public ProductQuality getQuality() {
        return quality;
    }
    public float getPrice() {
        return price;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setProductId(int productId) {
        this.productId = productId;
    }
    public void setProductType(String productType) {
        this.productType = productType;
    }
    public void setWarehouseId(int warehouseId) {
        this.warehouseId = warehouseId;
    }
    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
    public void setQuality(ProductQuality quality) {
        this.quality = quality;
    }
    public void setPrice(float price) {
        this.price = price;
    }

}
