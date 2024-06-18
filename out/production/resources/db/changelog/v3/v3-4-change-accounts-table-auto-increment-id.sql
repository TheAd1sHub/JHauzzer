--liquibase formatted sql
--changeset ybryak:3-4-change-accounts-table-auto-increment-id
ALTER TABLE accounts
CHANGE id   id  BIGINT  NOT NULL     AUTO_INCREMENT;