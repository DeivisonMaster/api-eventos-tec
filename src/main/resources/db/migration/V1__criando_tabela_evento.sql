CREATE TABLE evento(
	id SERIAL not null primary key,
	titulo	varchar(100) not null,
	descricao varchar(250) not null,
	url_imagem varchar(150) not null,
	url_evento varchar(100) not null,
	data TIMESTAMP not null,
	remoto boolean not null
);
--create sequence evento_seq increment by 1 start with 1