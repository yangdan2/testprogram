drop table if exists at_sequence;     
create table at_sequence (         
seq_name        VARCHAR(50) NOT NULL, -- 序列名称         
current_val     BIGINT      NOT NULL, -- 当前值         
increment_val   INT         NOT NULL    DEFAULT 1, -- 步长(跨度)         
PRIMARY KEY (seq_name)   );   

