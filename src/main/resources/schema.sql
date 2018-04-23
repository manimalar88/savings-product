CREATE TABLE T_PRODUCT (
    id   INTEGER      NOT NULL,
    name VARCHAR(128) NOT NULL,
    type VARCHAR(128),
    PRIMARY KEY (id)
);

CREATE TABLE T_CUSTOMER (
	id   INTEGER      NOT NULL,
    name VARCHAR(128) NOT NULL,
    email VARCHAR(128),
    PRIMARY KEY (id)
);


CREATE TABLE t_customer_product (
	customer_id   INTEGER      NOT NULL,
	product_id    INTEGER      NOT NULL
);

