INSERT INTO countries(name, short_code) VALUES
  ('Ukraine', 'UA'),
  ('United States', 'USA');

INSERT INTO roles(name) VALUES
  ('ADMIN'),
  ('USER');

INSERT INTO users (name, email, country_id, creation_date) VALUES
  ('Admin', 'admin@example.com', 1, '2005-01-01'),
  ('User', 'user@example.com', 1, '2020-01-01');

INSERT INTO users_roles_map(user_id, role_id) VALUES
  (1, 1),
  (1, 2),
  (2, 2);