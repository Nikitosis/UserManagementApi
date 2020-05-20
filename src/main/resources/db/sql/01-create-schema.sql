CREATE TABLE countries(
  id          BIGSERIAL     PRIMARY KEY,
  name        TEXT          NOT NULL,
  short_code  TEXT          NOT NULL
);

CREATE TABLE roles(
  id          BIGSERIAL     PRIMARY KEY,
  name        TEXT          NOT NULL
);

CREATE TABLE users(
  id              BIGSERIAL      PRIMARY KEY,
  name            TEXT           NOT NULL,
  email           TEXT           NOT NULL,
  country_id      BIGINT,
  creation_date   DATE           NOT NULL,

  CONSTRAINT users_country_fk FOREIGN KEY(country_id)
    REFERENCES countries(id)
);

CREATE TABLE users_roles_map(
  id            BIGSERIAL       PRIMARY KEY,
  user_id       BIGINT          NOT NULL,
  role_id       BIGINT          NOT NULL,

  CONSTRAINT users_roles_map_user_fk FOREIGN KEY(user_id)
    REFERENCES users(id),
  CONSTRAINT users_roles_map_role_fk FOREIGN KEY(role_id)
    REFERENCES roles(id)
);