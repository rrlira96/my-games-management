CREATE TABLE games (
  id serial PRIMARY KEY,
  name VARCHAR(255),
  platform VARCHAR(50),
  released_date DATE,
  genre VARCHAR(50),

  UNIQUE(name, released_date)
);