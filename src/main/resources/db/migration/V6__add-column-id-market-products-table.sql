ALTER TABLE products
    add column id_market INTEGER REFERENCES markets(id);