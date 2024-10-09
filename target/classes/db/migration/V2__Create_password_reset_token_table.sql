CREATE TABLE password_reset_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    token VARCHAR(255) NOT NULL,
    user_id BIGINT NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    CONSTRAINT fk_user
        FOREIGN KEY (user_id) REFERENCES users(id)
        ON DELETE CASCADE
);