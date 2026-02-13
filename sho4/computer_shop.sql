CREATE DATABASE computer_shop;
USE computer_shop;

CREATE TABLE computers (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    cpu_maker VARCHAR(50),
    cpu_freq DOUBLE,
    video_card VARCHAR(255),
    ram INT,
    sound_card VARCHAR(255),
    hdd INT
);

USE computer_shop;
SELECT * FROM computers;

DROP DATABASE computer_shop;