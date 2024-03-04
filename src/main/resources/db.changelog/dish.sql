--liquibase formatted sql

--changeset Klyshnikov Mikhail:1
create table dish
(
    id           int primary key,
    name         varchar(255),
    price        float,
    timeToCook   int,
    description  varchar(1023)
);
--rollback drop table product;