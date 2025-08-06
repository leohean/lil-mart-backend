ALTER TABLE products DROP COLUMN createdAt;
ALTER TABLE products DROP COLUMN lastUpdateAt;

ALTER TABLE products ADD COLUMN created_at DATE;
ALTER TABLE products ADD COLUMN last_update_at DATE;