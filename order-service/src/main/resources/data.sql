-- Insert 10 Orders
INSERT INTO orders (status, total_price) VALUES
('PENDING', 1250.00),
('COMPLETED', 300.00),
('CANCELLED', 75.00),
('PROCESSING', 180.00),
('COMPLETED', 2200.00),
('PENDING', 999.99),
('PROCESSING', 450.00),
('COMPLETED', 700.00),
('CANCELLED', 60.00),
('PENDING', 130.00);

-- Insert OrderItems (linked to orders above)
-- Order 1
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(1, 1, 1),
(2, 2, 1),
(3, 1, 1);

-- Order 2
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(4, 1, 2),
(5, 2, 2);

-- Order 3
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(6, 1, 3);

-- Order 4
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(7, 1, 4),
(8, 1, 4);

-- Order 5
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(9, 2, 5),
(10, 1, 5),
(11, 1, 5);

-- Order 6
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(12, 1, 6);

-- Order 7
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(13, 3, 7),
(14, 1, 7);

-- Order 8
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(15, 2, 8);

-- Order 9
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(16, 1, 9);

-- Order 10
INSERT INTO order_item (product_id, quantity, order_id) VALUES
(17, 1, 10),
(18, 2, 10);
