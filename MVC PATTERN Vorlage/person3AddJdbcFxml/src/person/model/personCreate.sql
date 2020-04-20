drop table person;
create table person (
  svnr char(10) primary key,
  nname varchar (32) not null,
  vname varchar (32),
  gebDat date not null,
  groesse decimal(3,2),
  geschlecht char(1) not null
)