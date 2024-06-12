--liquibase formatted sql
--changeset ybryak:2-2-change-warehouses-table
ALTER TABLE warehouses
ADD owner VARCHAR(255) NOT NULL;