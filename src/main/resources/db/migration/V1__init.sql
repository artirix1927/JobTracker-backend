-- =========================
-- USERS
-- =========================
CREATE TABLE app_user (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    role VARCHAR(50) NOT NULL DEFAULT 'USER',
    refresh_token TEXT
);

-- =========================
-- JOB POSTS
-- =========================
CREATE TABLE job_post (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    company VARCHAR(255),
    description TEXT,
    posted_by BIGINT,
    CONSTRAINT fk_job_post_user
        FOREIGN KEY (posted_by)
        REFERENCES app_user(id)
        ON DELETE SET NULL
);

-- =========================
-- JOB APPLICATIONS
-- =========================
CREATE TABLE job_application (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    job_post_id BIGINT NOT NULL,
    status VARCHAR(50) NOT NULL DEFAULT 'APPLIED',
    applied_at TIMESTAMP NOT NULL,

    CONSTRAINT fk_application_user
        FOREIGN KEY (user_id)
        REFERENCES app_user(id)
        ON DELETE CASCADE,

    CONSTRAINT fk_application_job_post
        FOREIGN KEY (job_post_id)
        REFERENCES job_post(id)
        ON DELETE CASCADE
);