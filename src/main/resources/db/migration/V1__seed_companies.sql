create table seed_company (
  id uuid not null primary key,
  name varchar(64)
);

insert into seed_company (id, name) values
(gen_random_uuid(), 'Kiepenkerl'),
(gen_random_uuid(), 'Magrovet Group'),
(gen_random_uuid(), 'Moravo Seed'),
(gen_random_uuid(), 'Garafarm');
