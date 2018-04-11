CREATE FUNCTION F_CR_Member_Card (V_mem_no VARCHAR(20),V_account_no varchar(32),V_status TINYINT(4),V_merchant_no VARCHAR(20),V_open_place_no VARCHAR(32),V_card_type TINYINT(4),V_start INT,V_end INT)
RETURNS VARCHAR(30)
BEGIN

-- DECLARE  V_account_no VARCHAR(32);

-- SELECT CONCAT('9527666000000',nextval('seq_account_no')) INTO V_account_no ;

INSERT INTO `db_member`.`m_r_member_card` (
	`member_no`,
	`account_no`,
	`status`,
	`open_date`,
	`expire_date`,
	`merchant_no`,
	`open_place_no`,
	`card_type`,
	`remark`
)
VALUES
	(
		V_mem_no,
		V_account_no,
		V_status,
		 date_add(now(),INTERVAL V_start day),
		 date_add(now(),INTERVAL V_end day),
		V_merchant_no,
		V_open_place_no,
		V_card_type,
		NULL
	);
RETURN V_account_no;

END


