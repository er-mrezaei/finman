CREATE TABLE user
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    username  VARCHAR(255) NOT NULL,
    password  VARCHAR(255) NOT NULL,
    user_type VARCHAR(50)  NOT NULL
);

CREATE TABLE ministration
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255) NOT NULL,
    cost_per_use DOUBLE NOT NULL,
    max_usage INT          NOT NULL,
    is_active BOOLEAN      NOT NULL
);

CREATE TABLE permission
(
    id              BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id         BIGINT,
    ministration_id BIGINT,
    granted         BOOLEAN NOT NULL,
    usage_count     INT     NOT NULL,
    CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT fk_ministration FOREIGN KEY (ministration_id) REFERENCES ministration (id)
);
