create database if not exists testdb;

create table if not exists testdb.employees(
    empid int primary key,
    fname varchar(20) not null,
    lname varchar(20),
    dept varchar(10),
    email varchar(50),
    dob datetime
);