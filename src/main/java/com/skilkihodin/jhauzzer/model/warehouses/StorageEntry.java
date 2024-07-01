package com.skilkihodin.jhauzzer.model.warehouses;

import com.skilkihodin.jhauzzer.api.RawStorageEntryAnswer;
import com.skilkihodin.jhauzzer.api.RawStorageEntryPost;
import com.skilkihodin.jhauzzer.model.accounts.Account;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Example;

import java.sql.Date;

@Entity
@Table(name = "stored_products")
@Getter @Setter
public final class StorageEntry implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "name") // Formerly  product_id: int
    private String name;

    @Column(name = "type") // Formerly  product_type: ProductGroup
    @Enumerated(EnumType.STRING)
    private ProductGroup type;

    @Column(name = "quality")
    @Enumerated(EnumType.STRING)
    private ProductQuality quality;

    @Column(name = "warehouse_id")
    private Integer warehouseId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private Float price;

    @Column(name = "discount_per_cent")
    private Float discount;

    @Column(name = "vip_discount_per_cent")
    private Float vipDiscount;

    @Column(name = "production_date")
    private Date productionDate;

    @Column(name = "lifetime_days")
    private Integer lifetimeDays;


    public static StorageEntry fromRawStorageEntryPost(RawStorageEntryPost storageEntry) {
        StorageEntry entry = new StorageEntry();
        entry.id = storageEntry.getId();
        entry.name = storageEntry.getName();
        entry.type = ProductGroup.fromString(storageEntry.getType());
        entry.quality = ProductQuality.fromString(storageEntry.getQuality());
        entry.warehouseId = storageEntry.getWarehouseId();
        entry.quantity = storageEntry.getQuantity();
        entry.price = storageEntry.getPrice();
        entry.discount = storageEntry.getDiscount();
        entry.vipDiscount = storageEntry.getVipDiscount();
        entry.productionDate = storageEntry.getProductionDate();
        entry.lifetimeDays = storageEntry.getLifetimeDays();

        return entry;
    }

    public static Example<StorageEntry> getExampleSearchEntry(@NotNull RawStorageEntryAnswer storageEntry) {
        StorageEntry constructedEntry = new StorageEntry();

        constructedEntry.setName(storageEntry.getName());
        constructedEntry.setType(ProductGroup.fromString(storageEntry.getType()));
        constructedEntry.setQuality(ProductQuality.fromString(storageEntry.getQuality()));
        constructedEntry.setQuantity(storageEntry.getQuantity());

        return Example.of(constructedEntry);
    }

    public RawStorageEntryAnswer extractAnswerData() {
        return extractAnswerData(Account.Type.CUSTOMER);
    }

    public RawStorageEntryAnswer extractAnswerData(@NotNull Account.Type requesterRole) {

        RawStorageEntryAnswer rawData = new RawStorageEntryAnswer();

        rawData.setId(id);
        rawData.setName(name);
        rawData.setType(type.name());
        rawData.setQuality(quality.name());
        rawData.setQuantity(quantity);
        rawData.setProductionDate(productionDate);
        rawData.setLifetimeDays(lifetimeDays);

        float resultingPrice = calculateFinalPrice(requesterRole);
        rawData.setPrice(resultingPrice);

        return rawData;
    }

    @Contract(pure = true)
    public float calculateFinalPrice(@NotNull Account.Type requesterRole) {
        return switch (requesterRole) {
            case CUSTOMER -> price * (1 - discount/100);
            case VIP_CUSTOMER -> price * (1 - vipDiscount/100);
            default -> price;
        };
    }

    @Override
    @SuppressWarnings("all")
    public StorageEntry clone() {
        StorageEntry clone = new StorageEntry();

        clone.id = id;
        clone.name = name;
        clone.type = type;
        clone.quality = quality;
        clone.warehouseId = warehouseId;
        clone.quantity = quantity;
        clone.price = price;
        clone.discount = discount;
        clone.vipDiscount = vipDiscount;

        return clone;
    }
}
