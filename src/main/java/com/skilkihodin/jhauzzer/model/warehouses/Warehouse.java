package com.skilkihodin.jhauzzer.model.warehouses;


import com.skilkihodin.jhauzzer.dto.RawWarehouseData;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Entity
@Table(name = "warehouses")
@Getter @Setter
public final class Warehouse {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "products_group")
    @Enumerated(EnumType.STRING)
    private ProductGroup productsType;

    @Column(name = "address")
    private String address;

    @Column(name = "owner_id")
    private Integer ownerLogin;


    public RawWarehouseData extractRawData() {
        RawWarehouseData rawData = new RawWarehouseData();
        rawData.setId(id);
        rawData.setProductsType(productsType.name());
        rawData.setAddress(address);

        return rawData;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Warehouse warehouse = (Warehouse) o;
        return id == warehouse.id;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }
}
