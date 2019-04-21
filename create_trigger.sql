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

DELIMITER $$
CREATE TRIGGER after_insert_order_detail
	AFTER INSERT ON order_detail
    FOR EACH ROW
BEGIN
	UPDATE orders
		SET Total_Price = Total_Price + NEW.Price, Total_KDV= Total_KDV + NEW.KDV
	WHERE ID = NEW.Order_ID;
    UPDATE product
		SET Quantity = Quantity - 1
	WHERE ID = NEW.Product_ID;
END$$
DELIMITER ;
