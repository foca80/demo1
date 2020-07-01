INSERT INTO categories (name) VALUES ('Juegos');
INSERT INTO categories (name) VALUES ('Alimentos');
INSERT INTO categories (name) VALUES ('Artefactos');

INSERT INTO products (date_in, date_of, name, price, id_category) VALUES ('2020-01-01', '2020-05-01', 'Limon', 3, 1);
INSERT INTO products (date_in, date_of, name, price, id_category) VALUES ('2020-01-01', '2020-05-01', 'Papa', 2, 1);
INSERT INTO products (date_in, date_of, name, price, id_category) VALUES ('2020-01-01', '2020-05-01', 'Licuadora', 300, 3);
INSERT INTO products (date_in, date_of, name, price, id_category) VALUES ('2020-01-01', '2020-05-01', 'Camiza', '60', 2);

INSERT INTO customers (dni, first_name, last_name) VALUES ('8888888', 'Pedro', 'Torres');
INSERT INTO customers (dni, first_name, last_name) VALUES ('99999999', 'Luis', 'Garcia');

INSERT INTO users(enabled, password, username) VALUES (true, '$2a$10$/8bvuHnTQg1pj20PulPdxebex04QyjstsGEHpCXSI4VneYJbsPSlO', 'web');
INSERT INTO roles (rol, user_id) VALUES ('ROLE_ADMIN', 1);