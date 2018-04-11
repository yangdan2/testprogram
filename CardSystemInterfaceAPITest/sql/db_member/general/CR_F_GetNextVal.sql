create function nextval (v_seq_name VARCHAR(50))  
returns BIGINT  
begin  
    update at_sequence set current_val = current_val + increment_val  where seq_name = v_seq_name;  
    return currval(v_seq_name);  
end;  



