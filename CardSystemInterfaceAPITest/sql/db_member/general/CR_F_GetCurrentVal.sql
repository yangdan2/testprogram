create function currval(v_seq_name VARCHAR(50))   
returns BIGINT    
begin        
    declare t_value BIGINT;         
    set t_value = 0;         
    select current_val into t_value from at_sequence where seq_name = v_seq_name;   
   return t_value;   
end;