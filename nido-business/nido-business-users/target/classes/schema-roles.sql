CREATE TABLE roles (
	id INTEGER SERIAL PRIMARY KEY NOT NULL UNIQUE,
	name varchar(30) NOT NULL UNIQUE,
	descriptions varchar(100) NOT NULL
);

CREATE TABLE user_in_role (
	id INTEGER SERIAL PRIMARY KEY NOT NULL UNIQUE,
  	user_id INT NOT NULL,
  	role_id INT NOT NULL,
  	grant_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	valid_maximum_date TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
  	PRIMARY KEY (user_id, role_id),
  	FOREIGN KEY (role_id)
     	REFERENCES roles (id),
  	FOREIGN KEY (user_id)
    	REFERENCES users (id)
);