module main {
    requires static lombok;

    requires api;
    requires newDTOs;

    requires spring.beans;
    requires spring.security.core;
    requires spring.web;
    requires spring.context;
    requires spring.boot;
    requires spring.data.jpa;
    requires spring.data.commons;

    requires jakarta.persistence;

    requires annotations;
    requires spring.webflux;
}