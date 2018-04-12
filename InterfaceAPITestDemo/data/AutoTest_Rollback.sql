DELETE FROM m_r_member_card WHERE account_no LIKE '9527666%';
DELETE FROM m_e_member WHERE address LIKE '云南省昆明市静海湖%';



UPDATE at_sequence SET current_val=100000 WHERE seq_name='seq_account_no';
UPDATE at_sequence SET current_val=100000 WHERE seq_name='seq_cert_no';
UPDATE at_sequence SET current_val=10000 WHERE seq_name='seq_member_no';
UPDATE at_sequence SET current_val=100000 WHERE seq_name='seq_mobile';