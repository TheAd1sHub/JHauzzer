--liquibase formatted sql
--changeset ybryak:2-1-change-stored-products-table
ALTER TABLE stored_products
CHANGE product_id name VARCHAR(255),
CHANGE product_type type INT,
ADD discount_per_cent FLOAT DEFAULT 0,
ADD vip_discount_per_cent FLOAT DEFAULT 0;