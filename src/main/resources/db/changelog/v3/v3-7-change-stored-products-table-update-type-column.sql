--liquibase formatted sql
--changeset ybryak:3-7-change-stored-products-table-update-type-column
ALTER TABLE stored_products
MODIFY type ENUM('NONE', 'DAIRY','FISH','GROCERY','MEAT','SPICES', 'LIQUID') DEFAULT 'NONE';
