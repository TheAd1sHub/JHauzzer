--liquibase formatted sql
--changeset ybryak:3-1-change-accounts-table
ALTER TABLE accounts
ADD id INT NOT NULL,
ADD PRIMARY KEY (id),
CHANGE type role VARCHAR(255);