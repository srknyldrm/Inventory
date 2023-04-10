CREATE TABLE category
(
    id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL
);

CREATE TABLE product
(
    id          UUID PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    description VARCHAR(255),
    price       DECIMAL(10, 2) NOT NULL,
    category_id BIGINT         NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE warehouse
(
    id     BIGINT PRIMARY KEY AUTO_INCREMENT,
    name   VARCHAR(255) NOT NULL,
    adress VARCHAR(255) NOT NULL,
    region VARCHAR(255) NOT NULL,
    city   VARCHAR(255)
);

CREATE TABLE inventory
(
    id           SERIAL PRIMARY KEY,
    product_id   UUID         NOT NULL,
    warehouse_id BIGINT       NOT NULL,
    quantity     INTEGER      NOT NULL,
    region       VARCHAR(255) NOT NULL,
    city         VARCHAR(255),
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse (id)
);

CREATE TABLE product_history
(
    id             BIGINT PRIMARY KEY AUTO_INCREMENT,
    product_id     CHAR(36)     NOT NULL,
    product_name   VARCHAR(255) NOT NULL,
    description    VARCHAR(255),
    price          DOUBLE       NOT NULL,
    category_id    BIGINT       NOT NULL,
    date           TIMESTAMP    NOT NULL,
    operation_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (category_id) REFERENCES category (id)
);

CREATE TABLE inventory_history
(
    id             BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id     BIGINT       NOT NULL,
    warehouse_id   BIGINT       NOT NULL,
    quantity       INT          NOT NULL,
    date           DATETIME     NOT NULL,
    region         VARCHAR(255) NOT NULL,
    city           VARCHAR(255) NOT NULL,
    operation_type VARCHAR(255) NOT NULL,
    FOREIGN KEY (product_id) REFERENCES product (id),
    FOREIGN KEY (warehouse_id) REFERENCES warehouse (id)
);

INSERT INTO inventory_db.warehouse (adress, city, name, region)
VALUES ('Istanbul, Turkey', 'ISTANBUL', 'Warehouse 1', 'MARMARA'),
       ('Izmir, Turkey', 'IZMIR', 'Warehouse 2', 'EGE'),
       ('Ankara, Turkey', 'ANKARA', 'Warehouse 3', 'AKDENIZ');

INSERT INTO inventory_db.category (name)
VALUES ('Electronics'),
       ('Home Appliances'),
       ('Books');

INSERT INTO inventory_db.product (id, description, name, price, category_id)
VALUES (0xA23C4A1D3AAA412FA403C0BC38E35680, 'Smartphone', 'iPhone 114', 1000.0, 1),
       (0xC2D821B225DC412891129313E1AB4C74, 'Smartphone', 'Samsung Galaxy S21', 800.0, 1),
       (0xE7E5027111064B80964C73B1859002EC, 'Smart TV', 'LG OLED TV', 500.0, 2),
       (0xB359CCE584DA4E69AA6475A838485A23, ' Dishwasher', 'Bosch Dishwasher', 1500.0, 2),
       (0xC2D2A0E068E3467EB4C8F9A3D5E6C8DA, '1984', 'George Orwell', 20.0, 3),
       (0xC56A4A78BF624351B8DFB7ABD0476AA8, 'Dostoyevsky', 'Crime and Punishment', 20.0, 3);

INSERT INTO inventory_db.inventory (city, quantity, region, product_id, warehouse_id)
VALUES ('IZMIR', 50, 'EGE', 0xA23C4A1D3AAA412FA403C0BC38E35680, 1),
       ('IZMIR', 50, 'EGE', 0xA23C4A1D3AAA412FA403C0BC38E35680, 2),
       ('IZMIR', 50, 'EGE', 0xC2D821B225DC412891129313E1AB4C74, 1),
       ('IZMIR', 50, 'EGE', 0xC2D821B225DC412891129313E1AB4C74, 2),
       ('IZMIR', 50, 'EGE', 0xE7E5027111064B80964C73B1859002EC, 3),
       ('IZMIR', 50, 'EGE', 0xE7E5027111064B80964C73B1859002EC, 1),
       ('IZMIR', 50, 'EGE', 0xB359CCE584DA4E69AA6475A838485A23, 3),
       ('IZMIR', 50, 'EGE', 0xC2D2A0E068E3467EB4C8F9A3D5E6C8DA, 2),
       ('IZMIR', 50, 'EGE', 0xC2D2A0E068E3467EB4C8F9A3D5E6C8DA, 1);
