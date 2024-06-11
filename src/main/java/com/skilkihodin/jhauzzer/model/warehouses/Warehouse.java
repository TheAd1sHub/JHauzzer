package com.skilkihodin.jhauzzer.model.warehouses;


import jakarta.persistence.*;

@Entity
@Table(name = "warehouses")
public final class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "products_group")
    @Enumerated(EnumType.STRING)
    private ProductGroup products;

    @Column(name = "address")
    private String address;


    public int getId() {
        return id;
    }
    public ProductGroup getProducts() {
        return products;
    }
    public String getAddress() {
        return address;
    }

    public void setId(int id) {
        this.id = id;
    }
    public void setProducts(ProductGroup products) {
        this.products = products;
    }
    public void setAddress(String address) {
        this.address = address;
    }

}
