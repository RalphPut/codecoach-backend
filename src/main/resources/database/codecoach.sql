SET SCHEMA 'codecoach';
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE users(
    user_id uuid DEFAULT  uuid_generate_v4() PRIMARY KEY,
    first_name text NOT NULL,
    last_name text NOT NULL,
    email text NOT NULL UNIQUE,
    password text,
    company text,
    image_url text
);

CREATE TABLE roles(
    role_id  SMALLSERIAL PRIMARY KEY ,
    name  text
);

CREATE TABLE user_roles(
    user_id_fk uuid REFERENCES users(user_id),
    role_id_fk smallserial REFERENCES roles(role_id)
    );

CREATE TABLE coach_details(
  user_id_fk uuid REFERENCES users(user_id),
  introduction text,
  available_time text,
  coach_xp numeric
);

CREATE TABLE topic(
    topic_id  SMALLSERIAL PRIMARY KEY ,
    name  text
);

CREATE TABLE coach_topic(
    user_id_fk  uuid REFERENCES users (user_id),
    topic_id_fk smallserial REFERENCES topic (topic_id),
    coach_knowledge_level numeric
);

CREATE TABLE feedback
(
    feedback_id  uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    feedback_coach text,
    feedback_coachee text
);


CREATE TABLE sessions
(
    id                uuid DEFAULT uuid_generate_v4() PRIMARY KEY,
    coachee_id        uuid REFERENCES users (user_id),
    coach_id          uuid REFERENCES users (user_id),
    feedback_id       uuid REFERENCES feedback (feedback_id),

    topic_id          smallserial REFERENCES topic (topic_id),
    session_date_time date,
    session_type      text,
    remarks           text,
    session_status    text
);




