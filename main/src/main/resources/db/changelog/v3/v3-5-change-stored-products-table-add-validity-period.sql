--liquibase formatted sql
--changeset ybryak:3-5-change-stored-products-table-add-validity-period
ALTER TABLE stored_products
ADD production_date DATE NULL DEFAULT (CURRENT_DATE),
ADD lifetime_days INT NULL;