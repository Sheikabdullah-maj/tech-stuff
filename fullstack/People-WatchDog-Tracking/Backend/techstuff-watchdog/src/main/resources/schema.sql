CREATE TABLE employee_tracker (
        id BIGINT AUTO_INCREMENT PRIMARY KEY,
        name VARCHAR(255),
        desk_number VARCHAR(255),
        date DATE,
        tap_in TIMESTAMP,
    tap_out TIMESTAMP);