create table users(
    id serial primary key,
    name text,
    email text,
    password text,
    birth date,
    cpf text,
    createdAt date,
    lastUpdateAt date
);