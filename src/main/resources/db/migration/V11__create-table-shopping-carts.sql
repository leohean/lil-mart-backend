CREATE TABLE shopping_carts(
    id serial primary key,
    id_user integer not null,
    id_product integer not null,
    product_quantity real,
    createdAt date,
    lastUpdateAt date,

    FOREIGN KEY (id_user) REFERENCES users(id),
    FOREIGN KEY (id_product) REFERENCES products(id)
)