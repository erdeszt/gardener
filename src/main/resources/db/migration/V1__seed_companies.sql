create table seed_company (
  id bytea not null primary key,
  name varchar(64)
);

insert into seed_company (id, name) values
(convert_to('01HTG5RB3Y0MQ2ZQ4FN8FGSFKB', 'utf8'), 'Kiepenkerl'),
(convert_to('01HTG5TNTZ8PWYQGHH971C0R9Y', 'utf8'), 'Magrovet Group'),
(convert_to('01HTG5V0NHCK3DTGMGYEWFCQE9', 'utf8'), 'Moravo Seed'),
(convert_to('01HTG5W5AM7D7P5Q4CJRB8C2YS', 'utf8'), 'Garafarm');
