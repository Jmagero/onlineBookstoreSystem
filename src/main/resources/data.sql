INSERT INTO customers (username, email)
VALUES ('Joe Doe', 'joedoe@email.com');

INSERT INTO customers (username, email)
VALUES ('Bob Smith', 'bob.smith@email.com');

INSERT INTO customers (username, email)
VALUES ('John Smith', 'johnsmith@example.com');


INSERT INTO Books (title, author, isbn, genre, publishedyear)
VALUES ('The Great Gatsby', 'Bob Smith', 'ISBN1', 'FICTION', 1925);

INSERT INTO books (title, author, isbn, genre, publishedyear)
VALUES ('Animal Farm', 'John Smith','54747' , 'FICTION', 1949);

INSERT INTO books (title, author, isbn, genre, publishedyear)
VALUES ('Pride and Prejudice', 'Joe Doe', '12348', 'ROMANCE', 1949);

INSERT INTO borrow_record (customer_id, book_id, borrow_date, return_date, is_past_due)
VALUES (1, 1, '2023-01-15', '2023-02-05', true);

INSERT INTO borrow_record (customer_id, book_id, borrow_date, return_date, is_past_due)
VALUES (2, 2, '2023-02-01', '2023-03-01', false);

INSERT INTO borrow_record (customer_id, book_id, borrow_date, return_date, is_past_due)
VALUES (3, 3, '2023-03-10', '2023-03-01', false);

INSERT INTO Inventory (book_id, available) VALUES (1,false);
INSERT INTO Inventory (book_id, available) VALUES (2, false);
INSERT INTO Inventory (book_id, available) VALUES (3, false);

INSERT INTO browsed_history (book_id, customer_id, browsed_at) VALUES (1, 1, '2023-01-15 10:30:00');
INSERT INTO browsed_history (book_id, customer_id, browsed_at) VALUES (2, 2, '2023-02-01 15:45:00');
INSERT INTO browsed_history (book_id, customer_id, browsed_at) VALUES (3, 3, '2023-03-10 08:00:00');




