INSERT INTO orders (customer_id, total_amount) VALUES ('cust_001', 150.50);
INSERT INTO orders (customer_id, total_amount) VALUES ('cust_002', 89.99);
INSERT INTO orders (customer_id, total_amount) VALUES ('cust_001', 200.00);

-- Order Items for Order 1
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (1, 101, 'Wireless Mouse', 1, 25.50);
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (1, 102, 'Mechanical Keyboard', 1, 125.00);

-- Order Items for Order 2
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (2, 103, 'USB-C Cable', 2, 15.00);
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (2, 104, 'Monitor Stand', 1, 59.99);

-- Order Items for Order 3
INSERT INTO order_items (order_id, product_id, product_name, quantity, price) VALUES (3, 105, 'Gaming Headset', 1, 200.00);
