create table oim_ws_configuration(
	id number(10) not null,
	name varchar2(500) not null,
	value varchar2(1000),
	data_type varchar2(100) default 'String',
	target_system varchar2(500),
	
	constraint oim_ws_configuration_pk primary key (id)
);