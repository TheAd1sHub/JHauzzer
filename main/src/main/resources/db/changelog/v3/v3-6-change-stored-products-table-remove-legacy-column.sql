--liquibase formatted sql
--changeset ybryak:3-6-change-stored-products-table-remove-legacy-owner-column
ALTER TABLE warehouses
DROP COLUMN owner;