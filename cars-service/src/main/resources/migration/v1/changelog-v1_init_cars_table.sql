--liquibase formatted sql

--changeset dev:2
INSERT INTO cars (car_uid, brand, model, registration_number, power, price, type, availability)
VALUES ('109b42f3-198d-4c89-9276-a7520a7120ab', 'Mercedes Benz', 'GLA 250', 'ЛО777Х799', 249, 3500, 'SEDAN', true)
