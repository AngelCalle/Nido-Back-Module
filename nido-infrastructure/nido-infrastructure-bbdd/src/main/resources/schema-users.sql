CREATE TABLE users (
	id INTEGER SERIAL PRIMARY KEY NOT NULL UNIQUE,
	
	code varchar(100) PRIMARY KEY NOT NULL UNIQUE,
    created_on_date TIMESTAMP NOT NULL,
    user_name varchar(20) NOT NULL UNIQUE,
    name varchar(30) DEFAULT NULL,

    sur_names varchar(60) DEFAULT NULL,
    telephone varchar(30) DEFAULT NULL UNIQUE,
    mail varchar(100) NOT NULL UNIQUE,
	
    language varchar(3) DEFAULT NULL,
    cookies_policy boolean DEFAULT FALSE NOT NULL,
    password varchar(60) NOT NULL,
    password_date_update TIMESTAMP NOT NULL,
	
    confirmation_of_registration int DEFAULT 0 NOT NULL,
    attempts int DEFAULT 0 NOT NULL,	
    is_active boolean DEFAULT TRUE NOT NULL,
    reset_token varchar(60) DEFAULT NULL,
	
    last_login_date TIMESTAMP NOT NULL,
	last_access_ip varchar(100) DEFAULT NULL,
    locked boolean DEFAULT FALSE NOT NULL,
    locked_reason varchar(500) DEFAULT NULL,
	
	locked_date TIMESTAMP DEFAULT NULL,    
    disable boolean DEFAULT FALSE NOT NULL,
    disable_date TIMESTAMP DEFAULT NULL,
	PRIMARY KEY (id, user_id),
    
);