-- Create Users Table
CREATE TABLE IF NOT EXISTS app_user (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    correct_answers BIGINT DEFAULT 0,
    incorrect_answers BIGINT DEFAULT 0
);

-- Create Questions Table
CREATE TABLE IF NOT EXISTS questions (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    question_text VARCHAR(255) NOT NULL,
    option_a VARCHAR(255) NOT NULL,
    option_b VARCHAR(255) NOT NULL,
    option_c VARCHAR(255) NOT NULL,
    option_d VARCHAR(255) NOT NULL,
    correct_answer VARCHAR(255) NOT NULL
);

-- Create Quiz Session Table
CREATE TABLE IF NOT EXISTS quiz_session (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT UNIQUE NOT NULL,
    is_active BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES app_user(id)
);

-- Insert Users
INSERT INTO app_user (username) VALUES
('Omkar'),
('Mahesh');

-- Insert Questions
INSERT INTO questions (question_text, option_a, option_b, option_c, option_d, correct_answer) VALUES
('What is Java?', 'Programming Language', 'Coffee', 'Planet', 'Animal', 'Programming Language'),
('What is Spring Boot?', 'Java Framework', 'IDE', 'Database', 'Operating System', 'Java Framework');
