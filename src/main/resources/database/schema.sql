DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS verification;

CREATE TABLE users
(
    id                  BIGINT      NOT NULL AUTO_INCREMENT,
    email               VARCHAR(30) NOT NULL,
    password            VARCHAR(20) NOT NULL,
    nickname            VARCHAR(20) NOT NULL,
    student_id          VARCHAR(10) NOT NULL,
    phone               VARCHAR(20) NOT NULL,
    image               VARCHAR(20),
    PRIMARY KEY (id),
    UNIQUE INDEX (email),
    UNIQUE INDEX (nickname)
) ENGINE=InnoDB;

CREATE TABLE verification
(
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    email              VARCHAR(255) NOT NULL,
    code               VARCHAR(6)   NOT NULL,
    state              BIT(1)       NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX (email)
) ENGINE=InnoDB;
