-- Create database
IF NOT EXISTS (SELECT * FROM sys.databases WHERE name = 'DA_1_Nhom4')
BEGIN
    CREATE DATABASE DA_1_Nhom4;
END
GO

USE DA_1_Nhom4;
GO

-- Drop existing table and constraints
IF EXISTS (SELECT * FROM sys.tables WHERE name = 'users')
BEGIN
    -- Drop foreign key constraints if any
    DECLARE @sql NVARCHAR(MAX) = '';
    SELECT @sql += 'ALTER TABLE users DROP CONSTRAINT ' + name + ';'
    FROM sys.key_constraints
    WHERE parent_object_id = OBJECT_ID('users');
    
    EXEC sp_executesql @sql;
    
    -- Drop the table
    DROP TABLE users;
END
GO

-- Create users table
CREATE TABLE users (
    id BIGINT IDENTITY(1,1) PRIMARY KEY,
    username NVARCHAR(255) NOT NULL UNIQUE,
    password NVARCHAR(255) NOT NULL,
<<<<<<< HEAD
    full_name NVARCHAR(255) NOT NULL,
    email NVARCHAR(255) NOT NULL UNIQUE,
    role NVARCHAR(255) NOT NULL DEFAULT 'USER',
    enabled BIT NOT NULL DEFAULT 1
);
GO

-- Insert default admin user
INSERT INTO users (username, password, full_name, email, role, enabled)
VALUES ('admin', 'admin123', 'Administrator', 'admin@example.com', 'ADMIN', 1);
=======
    email NVARCHAR(255) NOT NULL UNIQUE,
    role NVARCHAR(255) NOT NULL DEFAULT 'USER'
);
>>>>>>> a3a4757e22d988b4b31a734f7bfe77c847219a12
GO 