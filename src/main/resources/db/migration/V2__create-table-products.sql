create table products(
    id serial primary key,
    name text,
    category text,
    description text,
    unit_measurement text,
    stock_quantity real,
    createdAt date,
    lastUpdateAt date
);