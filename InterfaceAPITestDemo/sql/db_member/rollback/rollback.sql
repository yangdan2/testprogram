--��Ա��ͨrollback
delete from m_r_member_card where member_no in (select member_no from db_member.m_e_member where member_name = '�����Զ���');
delete from m_e_member where  member_name = '�����Զ���';