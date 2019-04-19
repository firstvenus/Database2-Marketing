use market;

DELIMITER $$
CREATE TRIGGER after_stock_operation
	AFTER INSERT ON stock_operation
    FOR EACH ROW
BEGIN
	UPDATE product
		SET Quantity = Quantity + NEW.Quantity
	WHERE ID = NEW.Product_ID;
END$$
DELIMITER ;
