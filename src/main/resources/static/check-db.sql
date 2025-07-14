-- Check database state
USE bankappdb;

-- Check if tables exist
SHOW TABLES;

-- Check account table structure
DESCRIBE account;

-- Check transaction table structure
DESCRIBE transaction;

-- Check if there are any accounts
SELECT * FROM account;

-- Check if there are any transactions
SELECT * FROM transaction;

-- Check transactions with account information
SELECT 
    t.id,
    t.amount,
    t.type,
    t.timestamp,
    t.account_id,
    a.username
FROM transaction t
JOIN account a ON t.account_id = a.id
ORDER BY t.timestamp DESC;

-- Check transaction count by account
SELECT 
    a.username,
    COUNT(t.id) as transaction_count,
    SUM(t.amount) as total_amount
FROM account a
LEFT JOIN transaction t ON a.id = t.account_id
GROUP BY a.id, a.username; 