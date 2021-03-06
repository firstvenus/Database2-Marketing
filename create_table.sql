CREATE DATABASE IF NOT EXISTS market CHARACTER SET = utf8mb4 COLLATE = utf8mb4_turkish_ci;

use market;

CREATE TABLE user(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Firstname VARCHAR(50) NOT NULL,
    Lastname VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(20) NOT NULL,
    Address VARCHAR(200),
    City VARCHAR(50),
    Phone VARCHAR(12),
    Country VARCHAR(50),
    Email VARCHAR(100) NOT NULL UNIQUE,
    RegisterDate Datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=INNODB;

CREATE TABLE category(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Category VARCHAR(60) NOT NULL
) ENGINE=INNODB;

CREATE TABLE supplier(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    CompanyName VARCHAR(100) NOT NULL
);

CREATE TABLE product(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(200) NOT NULL,
    Category_ID INT NOT NULL,
    Image MEDIUMBLOB,
    Price FLOAT NOT NULL,
    Quantity INT NOT NULL DEFAULT 0,
    KDV_rate FLOAT NOT NULL,
    FOREIGN KEY (Category_ID) REFERENCES category(ID) ON DELETE RESTRICT
) ENGINE=INNODB;

CREATE TABLE orders(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    User_ID INT NOT NULL,
    Total_Price DOUBLE NOT NULL,
    Total_KDV DOUBLE NOT NULL,
    Order_Time Datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
    Is_Completed BOOL NOT NULL DEFAULT FALSE,
    FOREIGN KEY(User_ID) REFERENCES USER(ID) ON DELETE RESTRICT
) ENGINE=INNODB;

CREATE TABLE order_detail(
	Order_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Price DOUBLE NOT NULL,
    KDV DOUBLE NOT NULL,
    FOREIGN KEY(Product_ID) REFERENCES product(ID) ON DELETE RESTRICT,
    FOREIGN KEY(Order_ID) REFERENCES orders(ID) ON DELETE RESTRICT
) ENGINE=INNODB;

CREATE TABLE employee(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Firstname VARCHAR(50) NOT NULL,
    Lastname VARCHAR(50) NOT NULL,
    Username VARCHAR(50) NOT NULL UNIQUE,
    Password VARCHAR(20) NOT NULL,
    Address VARCHAR(200),
    City VARCHAR(50),
    Phone VARCHAR(12),
    Country VARCHAR(50),
    Email VARCHAR(100) NOT NULL UNIQUE,
    RegisterDate Datetime NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=INNODB;

CREATE TABLE purchase_type(
	ID SMALLINT AUTO_INCREMENT PRIMARY KEY,
    PurchaseType VARCHAR(30) NOT NULL
) ENGINE=INNODB;

CREATE TABLE purchase(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Order_ID INT NOT NULL,
    Purchase_Type_ID SMALLINT NOT NULL,
    FOREIGN KEY (Order_ID) REFERENCES orders(ID) ON DELETE RESTRICT,
    FOREIGN KEY (Purchase_Type_ID) REFERENCES purchase_type(ID) ON DELETE RESTRICT    
) ENGINE=INNODB;

CREATE TABLE stock_operation(
	ID INT AUTO_INCREMENT PRIMARY KEY,
    Employee_ID INT NOT NULL,
    Product_ID INT NOT NULL,
    Supplier_ID INT NOT NULL,
    Quantity INT NOT NULL,
    Op_Date DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    
    FOREIGN KEY (Employee_ID) REFERENCES employee(ID) ON DELETE RESTRICT,
    FOREIGN KEY (Product_ID) REFERENCES product(ID) ON DELETE RESTRICT,
    FOREIGN KEY (Supplier_ID) REFERENCES supplier(ID) ON DELETE RESTRICT
    
) ENGINE=INNODB;