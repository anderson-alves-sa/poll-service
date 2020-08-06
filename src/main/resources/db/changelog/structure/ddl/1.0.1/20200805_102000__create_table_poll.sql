--changeset anderson.alves:create_poll_table
CREATE TABLE poll_service.poll(
    id uuid NOT NULL PRIMARY KEY,
    created_on  timestamp without time zone DEFAULT now(),
    title        VARCHAR(255) NOT NULL,
    user_id uuid NOT NULL
);

CREATE UNIQUE INDEX poll_title_index ON poll_service.poll (title);
