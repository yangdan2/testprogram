--会员开通rollback
delete from m_r_member_card where member_no in (select member_no from db_member.m_e_member where member_name = '二期自动化');
delete from m_e_member where  member_name = '二期自动化';