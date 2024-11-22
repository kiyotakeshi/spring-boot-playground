CREATE TABLE playground.customer
(
    customer_id BIGINT PRIMARY KEY,
    name  VARCHAR(30) NOT NULL,
    age  INT NOT NULL,
    city VARCHAR(255) NOT NULL
);

CREATE TABLE playground.order
(
    order_id BIGINT PRIMARY KEY,
    customer_id BIGINT NOT NULL REFERENCES playground.customer,
    description VARCHAR(255) NOT NULL
);

CREATE TABLE playground.order_detail
(
    order_id BIGINT NOT NULL REFERENCES playground.order,
    product_code VARCHAR(30) NOT NULL,
    quantity INT NOT NULL,
    price FLOAT NOT NULL
);

INSERT INTO playground.customer (customer_id, name, age, city)
VALUES (1, 'mike', 30, 'tokyo'),
       (2, 'kevin', 25, 'new york'),
       (3, 'markus', 35, 'berlin'),
       (4, 'tristan', 40, 'london');

INSERT INTO playground.order (order_id, customer_id, description)
VALUES (1, 1, 'order 1'),
       (2, 1, 'order 2'),
       (3, 2, 'order 3'),
       (4, 3, 'order 4'),
       (5, 4, 'order 5');

INSERT INTO playground.order_detail (order_id, product_code, quantity, price)
VALUES (1, 'product 1', 10, 100.0),
       (1, 'product 2', 20, 200.0),
       (2, 'product 3', 30, 300.0),
       (3, 'product 4', 40, 400.0),
       (4, 'product 5', 50, 500.0),
       (5, 'product 6', 60, 600.0);
