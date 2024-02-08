CREATE TABLE  Customers (
                       Id INT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(25),
                       email VARCHAR(25) UNIQUE
);

CREATE TABLE  Books (
                        Id INT AUTO_INCREMENT PRIMARY KEY,
                        title VARCHAR(50),
                        author VARCHAR(50),
                        isbn VARCHAR(5) UNIQUE,
                        genre VARCHAR(25),
                        publishedyear INT
);

CREATE TABLE  borrow_record (
                               Id INT AUTO_INCREMENT PRIMARY KEY,
                               customer_id BIGINT,
                               book_id BIGINT,
                               borrow_date DATE NOT NULL,
                               return_date DATE,
                               is_past_due BOOLEAN,
                               FOREIGN KEY (customer_id) REFERENCES Customers(id),
                               FOREIGN KEY (book_id) REFERENCES Books(id)
);

CREATE TABLE Inventory (
                           id BIGINT PRIMARY KEY AUTO_INCREMENT,
                           book_id BIGINT UNIQUE,
                           available BOOLEAN DEFAULT TRUE,
                           FOREIGN KEY (book_id) REFERENCES Books(id)
);
CREATE TABLE browsed_history (
                                 id BIGINT AUTO_INCREMENT PRIMARY KEY,
                                 book_id BIGINT,
                                 customer_id BIGINT,
                                 browsed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (book_id) REFERENCES books(id),
                                 FOREIGN KEY (customer_id) REFERENCES customers(id)
);

