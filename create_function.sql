use market;

DELIMITER $$
CREATE FUNCTION CalcKDV (price DOUBLE, kdvRate DOUBLE) 
RETURNS decimal
DETERMINISTIC
BEGIN 
  DECLARE kdv DOUBLE;
  SET kdv = ROUND(price * (kdvRate / 100),2);
  RETURN kdv;
END$$
DELIMITER ;
