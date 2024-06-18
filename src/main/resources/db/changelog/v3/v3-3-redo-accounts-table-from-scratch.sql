--liquibase formatted sql
--changeset ybryak:3-3-redo-accounts-table-from-scratch
DROP TABLE IF EXISTS accounts;

CREATE TABLE accounts (
   id               BIGINT         NOT NULL   PRIMARY KEY,
   login            VARCHAR(255) NOT NULL   DEFAULT "",
   password_hash    INT          NOT NULL,
   role             VARCHAR(255) NOT NULL   DEFAULT "CUSTOMER"
);