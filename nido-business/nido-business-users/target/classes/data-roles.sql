INSERT INTO roles ( name, descriptions ) VALUES ( 'ADMIN', 'Tienen todos los privilegios' );
INSERT INTO roles ( name, descriptions ) VALUES ( 'USER', 'No tienen privilegios' );

INSERT INTO user_in_role (
	user_id,
	role_id,
	grant_date,
	valid_maximum_date
) VALUES (
	1,
	1,
	'2017-05-02 00:00:00',
	'2037-05-02 00:00:00'
);

INSERT INTO user_in_role (
	user_id,
	role_id,
	grant_date,
	valid_maximum_date
) VALUES (
	1,
	2,
	'2017-05-02 00:00:00',
	'2037-05-02 00:00:00'
);

INSERT INTO user_in_role (
	user_id,
	role_id,
	grant_date,
	valid_maximum_date
) VALUES (
	2,
	2,
	'2017-05-02 00:00:00',
	'2037-05-02 00:00:00'
);

