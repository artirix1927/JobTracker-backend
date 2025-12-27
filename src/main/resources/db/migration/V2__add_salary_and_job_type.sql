-- =========================
-- JOB POSTS: add salary and job_type
-- =========================
ALTER TABLE job_post
ADD COLUMN salary NUMERIC(10,2);

ALTER TABLE job_post
ADD COLUMN job_type VARCHAR(50);