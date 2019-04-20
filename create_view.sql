use market;

-- Onaylanmamış siparişleri görüntüle --
CREATE VIEW view_incompleted_orders AS
	SELECT
		orders.*,
		user.Username,
        (SELECT Count(*) FROM order_detail WHERE Order_ID=orders.ID) as OrderCount    
	FROM orders 
		INNER JOIN user ON user.ID = orders.User_ID
    WHERE NOT Is_Completed;
    
-- Order detaylarını görüntüle --
CREATE VIEW view_order_detail AS
	SELECT 
		od.Order_ID ,od.Product_ID,c.Category,p.ProductName, od.Price, od.KDV 
	FROM order_detail AS od
		INNER JOIN product as p ON  p.ID = od.Product_ID
		INNER JOIN category as c ON c.ID = p.Category_ID;
