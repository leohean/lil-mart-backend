ALTER TABLE shopping_carts DROP COLUMN createdAt;
ALTER TABLE shopping_carts DROP COLUMN lastUpdateAt;

ALTER TABLE shopping_carts ADD COLUMN created_at DATE;
ALTER TABLE shopping_carts ADD COLUMN last_update_at DATE;