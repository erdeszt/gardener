create table seed_companies (
  id uuid not null primary key,
  name varchar(64)
);

insert into seed_companies (id, name) values
(gen_random_uuid(), 'Kiepenkerl'),
(gen_random_uuid(), 'Magrovet Group'),
(gen_random_uuid(), 'Moravo Seed'),
(gen_random_uuid(), 'Garafarm');
