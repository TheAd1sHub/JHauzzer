package com.skilkihodin.jhauzzer.model.warehouses;

import com.skilkihodin.dto.RawStorageEntry;
import com.skilkihodin.jhauzzer.model.accounts.Account;
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


    public RawStorageEntry extractRawData() {
        return extractRawData(Account.Type.CUSTOMER);
    }

    public RawStorageEntry extractRawData(Account.Type requesterRole) {

        RawStorageEntry rawData = new RawStorageEntry();

        rawData.setName(name);
        rawData.setType(type.name());
        rawData.setQuality(quality.name());
        rawData.setQuantity(quantity);

        float resultingPrice = calculateFinalPrice(requesterRole);

        rawData.setPrice(resultingPrice);

        return rawData;
    }

    public float calculateFinalPrice(Account.Type requesterRole) {
        return switch (requesterRole) {
            case CUSTOMER -> price * (1 - discount / 100);
            case VIP_CUSTOMER -> price * (1 - vipDiscount / 100);
            default -> price;
        };
    }
}
