CREATE TABLE users
(
    id         SERIAL PRIMARY KEY,
    user_id    INT          NOT NULL UNIQUE,
    user_name  varchar(128) NOT NULL,
    first_name varchar(64)  NOT NULL,
    last_name  varchar(64)  NOT NULL
);
CREATE TABLE teams
(
    id            SERIAL PRIMARY KEY,
    figma_team_id varchar(128) NOT NULL,
    name          varchar(128) NOT NULL
);

CREATE TABLE user_team
(
    user_id INT REFERENCES users (id) NOT NULL,
    team_id INT REFERENCES teams (id) NOT NULL,
    PRIMARY KEY (user_id, team_id)
);

CREATE TABLE projects
(
    id               SERIAL PRIMARY KEY,
    figma_project_id varchar(128) NOT NULL,
    name             varchar(128) NOT NULL,
    team_id          INT          NOT NULL,
    FOREIGN KEY (team_id) REFERENCES teams (id) ON DELETE CASCADE
);
CREATE TABLE files
(
    id             SERIAL PRIMARY KEY,
    figma_file_key varchar(128)                                   NOT NULL,
    name           varchar(128)                                   NOT NULL,
    thumbnail_url  varchar(1000),
    last_modified  timestamp                                      NOT NULL,
    project_id     INT REFERENCES projects (id) ON DELETE CASCADE NOT NULL
);
CREATE TABLE chats
(
    id         SERIAL PRIMARY KEY,
    chat_type  varchar(64)                                    NOT NULL,
    chat_id    INT                                            NOT NULL,
    enabled    boolean default false,
    created_at timestamp                                      NOT NULL,
    project_id INT REFERENCES projects (id) ON DELETE CASCADE NOT NULL
)