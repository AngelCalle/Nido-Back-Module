CREATE TABLE avatars (
	id INTEGER SERIAL PRIMARY KEY UNIQUE,
	user_id varchar(100) PRIMARY KEY NOT NULL UNIQUE,
 	upload_date TIMESTAMP DEFAULT NULL,
 	profile_image BYTEA DEFAULT NULL UNIQUE,
 	PRIMARY KEY (id, user_id)
);

CREATE TABLE user_in_avatar (
	id INTEGER SERIAL PRIMARY KEY UNIQUE,
  	user_id INT NOT NULL UNIQUE,
  	avatar_id INT NOT NULL UNIQUE,
  	grant_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  	PRIMARY KEY (user_id, avatar_id),
  	FOREIGN KEY (avatar_id)
  		REFERENCES avatars (id),
 	FOREIGN KEY (user_id)
    	REFERENCES users (id)
);