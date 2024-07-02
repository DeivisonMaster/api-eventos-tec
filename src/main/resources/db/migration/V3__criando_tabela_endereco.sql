create table endereco(
	id SERIAL not null primary key,
	cidade varchar(100) not null,
	uf varchar(100) not null,
	id_evento bigint,
	foreign key(id_evento) references evento(id) on delete cascade
);

--create sequence endereco_seq increment by 1 start with 1