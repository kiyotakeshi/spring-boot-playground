CREATE TABLE playground.customer
(
    customer_id BIGINT PRIMARY KEY,
    first_name  VARCHAR(255) NOT NULL,
    last_name   VARCHAR(255) NOT NULL,
    email       VARCHAR(255) NOT NULL UNIQUE
);

INSERT INTO playground.customer (customer_id, first_name, last_name, email)
VALUES (1, 'Michael', 'Bull', 'michael@example.com'),
       (2, 'Kevin', 'Herron', 'kevin@example.com'),
       (3, 'Markus', 'Padourek', 'markus@example.com'),
       (4, 'Tristan', 'Hamilton', 'tristan@example.com');
