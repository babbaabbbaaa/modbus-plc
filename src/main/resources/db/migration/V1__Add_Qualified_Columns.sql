alter table plc.plc_data add column qualified varchar(10);

alter table plc.plc_pattern_config add column qualified_start int not null default 1;

alter table plc.plc_pattern_config add column qualified_end int not null default 1;