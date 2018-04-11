CREATE FUNCTION F_CR_Member (V_place_no VARCHAR(32),V_member_state TINYINT(4),V_abnormal_status TINYINT(4))
RETURNS VARCHAR(20)
BEGIN

DECLARE V_mem_no VARCHAR(20);

SELECT CONCAT('9527666',nextval('seq_member_no')) INTO  V_mem_no ;


INSERT INTO `db_member`.`m_e_member` (
	`member_no`,
	`member_name`,
	`cert_no`,
	`mobile_no`,
	`birthday`,
	`gender`,
	`address`,
	`place_no`,
	`email`,
	`joindate`,
	`member_rank`,
	`member_state`,
	`abnormal_status`,
	`remark`,
	`update_date`,
	`editor_id`
)
VALUES
	(
		 V_mem_no,
		'想静静',
     CONCAT('952766696903',nextval('seq_cert_no')),
		 CONCAT('15924',nextval('seq_mobile')),
		'1978-03-02',
		'女',
		'云南省昆明市静海湖',
		V_place_no,
		'www@qq.com',
		date_sub(now(),INTERVAL 1 DAY),
		NULL,
		V_member_state,
		V_abnormal_status,
		'无',
		date_sub(now(),INTERVAL 1 DAY),
		'000008'
	);

RETURN V_mem_no;

END


