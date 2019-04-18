use market;

DELIMITER $$
CREATE PROCEDURE RegisterUser 
	(
		IN Firstname VARCHAR(50),
		IN Lastname VARCHAR(50),
		IN Username VARCHAR(50),
		IN Password VARCHAR(20),
		IN Address VARCHAR(200),
		IN City VARCHAR(50),
		IN Phone VARCHAR(12),
		IN Country VARCHAR(50),
		IN Email VARCHAR(100)
	)
BEGIN
    INSERT INTO 
		user (Firstname,Lastname,Username,Password,Address,City,Phone,Country,Email) 
        VALUES (Firstname,Lastname,Username,Password,Address,City,Phone,Country,Email);
END $$
$$ DELIMITER ;

DELIMITER $$
CREATE PROCEDURE RegisterEmployee 
	(
		IN Firstname VARCHAR(50),
		IN Lastname VARCHAR(50),
		IN Username VARCHAR(50),
		IN Password VARCHAR(20),
		IN Address VARCHAR(200),
		IN City VARCHAR(50),
		IN Phone VARCHAR(12),
		IN Country VARCHAR(50),
		IN Email VARCHAR(100)
	)
BEGIN
    INSERT INTO 
		employee (Firstname,Lastname,Username,Password,Address,City,Phone,Country,Email) 
        VALUES (Firstname,Lastname,Username,Password,Address,City,Phone,Country,Email);
END $$
$$ DELIMITER ;
