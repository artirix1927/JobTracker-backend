-- =========================
-- JOB POSTS: add location field
-- =========================

ALTER TABLE job_post
ADD COLUMN location VARCHAR(255);