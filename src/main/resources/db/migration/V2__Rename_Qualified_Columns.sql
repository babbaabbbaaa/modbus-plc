update plc.plc_data set qualified = '' where qualified is null;

alter table plc.plc_data change qualified barcode_qualify varchar(10) not null default '';

alter table plc.plc_data add column qualified bit not null default b'1';