-- Insert a customer record
INSERT INTO customers (customer_id, name, email) VALUES('C001', 'Srikanth', 'Srikanth@example.com');

-- Insert transactions for the customer
INSERT INTO transactions (amount, date, customer_id) VALUES (120.0, '2025-06-15', 'C001');
INSERT INTO transactions (amount, date, customer_id) VALUES (85.0, '2025-07-20', 'C001');
INSERT INTO transactions (amount, date, customer_id) VALUES (45.0, '2025-08-01', 'C001');
INSERT INTO transactions (amount, date, customer_id) VALUES (150.0, '2025-08-10', 'C001');