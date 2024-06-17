--liquibase formatted sql
--changeset ybryak:3-2-change-warehouses-table
ALTER TABLE warehouses
CHANGE owner owner_id LONG;