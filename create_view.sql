use market;

CREATE VIEW view_incompleted_orders AS
	SELECT
		orders.*,
		user.Username
	FROM orders 
		INNER JOIN user ON user.ID = orders.User_ID
    WHERE NOT Is_Completed;
