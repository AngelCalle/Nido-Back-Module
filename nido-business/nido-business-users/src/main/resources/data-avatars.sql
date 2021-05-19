INSERT INTO avatars ( user_id, upload_date, profile_image ) VALUES ( 1, '2037-05-02 00:00:00', null );
INSERT INTO avatars ( user_id, upload_date, profile_image ) VALUES ( 2, '2037-05-02 00:00:00', null );
INSERT INTO avatars ( user_id, upload_date, profile_image ) VALUES ( 3, '2037-05-02 00:00:00', null );

INSERT INTO user_in_avatar (
	user_id,
	avatar_id,
	grant_date
) VALUES (
	1,
	1,
	null
);

INSERT INTO user_in_avatar (
	user_id,
	avatar_id,
	grant_date
) VALUES (
	2,
	2,
	null
);

INSERT INTO user_in_avatar (
	user_id,
	avatar_id,
	grant_date
) VALUES (
	3,
	3,
	null
);
