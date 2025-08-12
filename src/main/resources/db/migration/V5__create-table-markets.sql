create table markets(
    id serial primary key,
    name text,
    email text,
    password text,
    cnpj text,
    role text not null,
    created_at date,
    last_update_at date
);