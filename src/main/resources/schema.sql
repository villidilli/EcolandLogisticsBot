DROP TABLE IF EXISTS users, companies, passwords, bids;

CREATE TABLE IF NOT EXISTS users (
    user_id BIGINT PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name VARCHAR(255),
    user_name VARCHAR(255),
    user_role VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS companies (
    company_inn BIGINT PRIMARY KEY,
    user_id BIGINT REFERENCES users(user_id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS passwords (
    password VARCHAR(255) PRIMARY KEY,
    company_type VARCHAR(255)
);

INSERT INTO passwords (password, company_type)
VALUES ('111', 'DEVELOPER'),
       ('222', 'TRANSPORT_COMPANY'),
       ('333', 'ECOLAND_COMPANY');

CREATE TABLE IF NOT EXISTS bids (
    bid_id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    bid_description VARCHAR(255) NOT NULL,
    bid_status VARCHAR(255) NOT NULL
);