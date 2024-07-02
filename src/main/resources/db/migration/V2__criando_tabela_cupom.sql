create table cupom(
	id SERIAL not null primary key,
	codigo 	varchar(100) not null,
	desconto	int not null,
	validade timestamp not null,
	id_evento bigint,
	foreign key(id_evento) references evento(id) on delete cascade
);

--create sequence cupom_seq increment by 1 start with 1