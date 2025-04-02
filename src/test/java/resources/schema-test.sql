CREATE TABLE IF NOT EXISTS students (
    id IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    lastname VARCHAR(100) NOT NULL,
    state VARCHAR(50) NOT NULL CHECK (state IN ('activo','inactivo')),
    age INT CHECK(age>0)
);
