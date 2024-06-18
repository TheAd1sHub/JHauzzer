--liquibase formatted sql
--changeset ybryak:3-1-change-accounts-table
--ALTER TABLE accounts
--DROP PRIMARY KEY;

ALTER TABLE accounts
--CHANGE type role VARCHAR(255),
ADD id BIGINT NOT NULL PRIMARY KEY;
--CONSTRAINT accounts PRIMARY KEY (id);