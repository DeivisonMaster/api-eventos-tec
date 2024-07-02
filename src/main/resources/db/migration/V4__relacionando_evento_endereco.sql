alter table eventostec.public.evento add endereco bigint;
alter table eventostec.public.evento add foreign key(endereco) references endereco(id);