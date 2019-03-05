create table employee(
id int primary key,
firstname varchar(50),
lastname varchar(50),
phonenumber varchar(10),
email varchar(100) constraint email_uk unique,
password varchar(20) ,
department_id int  ,
job_id int,
status int,
constraint employee_department_fk foreign key(department_id) references employee_department(id),
constraint job_fk foreign key(job_id) references job(id)
);--over

create sequence employee_id_seq start with 1 increment by 1;--over


create table job(
id int primary key,
name varchar(100)
);--over

create sequence job_id_seq start with 1 increment by 1;--over

create table employee_department(
id int primary key,
name varchar(100)
);--over


create sequence employee_department_id_seq start with 1 increment by 1;--over


create table topics(
id int primary key,
name varchar(100),
courses_id int ,
constraint courses_fk foreign key(courses_id) references courses(id)
);--over

alter table topics add status int;

create sequence topics_id_seq start with 1 increment by 1;--over


create table courses(
id int primary key,
name varchar(100)
);--over


alter table courses add status int;

create sequence courses_id_seq start with 1 increment by 1;--over

create table status(
id int primary key,
name varchar(100)
);--over

create sequence status_id_seq start with 1 increment by 1;--over

create table employee_topic(
id int primary key,
name varchar(100),
emp_id int ,
topics_id int,
status_id int,
constraint employeetopic_empid_fk foreign key(emp_id) references employee(id),
constraint topic_fk foreign key(topics_id) references topics(id),
constraint status_fk foreign key(status_id) references status(id)
);--over


alter table employee_topic drop column name;

create sequence employee_topic_id_seq start with 1 increment by 1;--over