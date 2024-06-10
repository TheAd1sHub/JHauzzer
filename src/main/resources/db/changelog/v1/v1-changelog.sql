--liquibase formatted sql
--changeset ybryak:1-1-create-accounts-table
CREATE TABLE accounts (
   login VARCHAR(255) NOT NULL,
   password_hash INT NOT NULL,
   type VARCHAR(255) NOT NULL,
   CONSTRAINT pk_accounts PRIMARY KEY (login)
);
--rollback DROP TABLE accounts;

--changeset ybryak:1-2-create-warehouses-table
CREATE TABLE warehouses (
  id INT AUTO_INCREMENT NOT NULL,
   products_group VARCHAR(255) NOT NULL,
   address VARCHAR(255) NOT NULL,
   CONSTRAINT pk_warehouses PRIMARY KEY (id)
);
--rollback DROP TABLE warehouses;

--changeset ybryak:1-3-create-stored-products-table
CREATE TABLE IF NOT EXISTS stored_products (
  id INT AUTO_INCREMENT NOT NULL,
   product_id INT NOT NULL,
   product_type VARCHAR(255) NOT NULL,
   warehouse_id INT NOT NULL,
   quality VARCHAR(255) NOT NULL,
   quantity INT NOT NULL,
   price FLOAT NOT NULL,
   CONSTRAINT pk_storages_contents PRIMARY KEY (id)
);
--rollback DROP TABLE stored_products;