---Employee course management--------
create table employee(
id number constraint employee_id_pk primary key,
firstname varchar2(50) not null,
lastname varchar2(50),
phonenumber varchar2(10) not null,
email varchar2(100) constraint email_uk unique not null,
password varchar2(20) not null,
department_id number check(department_id>0),
job_id number check(job_id>0),
status number check(status=0 or status=1) not null,
isadmin number check(isadmin=0 or isadmin=1) not null,
ismanager number check(ismanager=0 or ismanager=1) not null,
manager_id number default null,
createdon timestamp,
createdby number,
modifiedon timestamp default null,
modifiedby number default null,
constraint manager_id_fk foreign key(manager_id) references employee(id),
constraint employee_department_fk foreign key(department_id) references employee_department(id),
constraint job_fk foreign key(job_id) references job(id)
);--over



insert into employee(id,firstname,lastname,phonenumber,email,password,department_id,job_id,status,isadmin,ismanager) 
values(employee_id_seq.nextval,'Kaviyarasan','S','9688272004','kavi@gmail.com','123',1,2,1,1,0);

--update employee set ismanager=1 where id=1;

select * from employee;

create sequence employee_id_seq start with 1 increment by 1;--over

create table employee_department(
id number primary key,
name varchar2(100) not null
);--over

create sequence employee_department_id_seq start with 1 increment by 1;--over

insert into employee_department values (employee_department_id_seq.nextval,'Appload');
insert into employee_department values (employee_department_id_seq.nextval,'DBA');
select * from employee_department;


create table job(
id number primary key,
name varchar2(100) not null
);--over

create sequence job_id_seq start with 1 increment by 1;--over

insert into job values (job_id_seq.nextval,'Manager');
insert into job values (job_id_seq.nextval,'IT Admin');



create table courses(id number primary key,
name varchar2(100) not null unique,
status number check(status=0 or status=1) not null,
createdon timestamp,
createdby number,
modifiedon timestamp default null,
modifiedby number default null);--over
select * from courses;


create sequence courses_id_seq start with 1 increment by 1;--over

create table topics(
id number primary key,
name varchar2(100) not null,
courses_id number ,
status number check(status=0 or status=1) not null,
createdon timestamp,
createdby number,
modifiedon timestamp default null,
modifiedby number default null,
constraint courses_fk foreign key(courses_id) references courses(id)
);--over

create sequence topics_id_seq start with 1 increment by 1;--over

create table projects(
id number primary key,
name varchar2(100),
department_id number ,
manager_id number,
createdon timestamp,
createdby number,
modifiedon timestamp default null,
modifiedby number default null,
constraint pro_managerid_fk foreign key(manager_id) references employee(id),
constraint employeedepartment_fk foreign key(department_id) references employee_department(id)
);--over



create sequence project_id_seq start with 1 increment by 1;--over

create table status(
id int primary key,
name varchar(100) 
);--over

create sequence status_id_seq start with 1 increment by 1;--over

create table employee_topic(
id int primary key,
emp_id int not null,
topics_id int not null,
status_id int not null,
createdon timestamp,
createdby number,
modifiedon timestamp default null,
modifiedby number default null,
constraint employeetopic_empid_fk foreign key(emp_id) references employee(id),
constraint topic_fk foreign key(topics_id) references topics(id),
constraint status_fk foreign key(status_id) references status(id)
);--over


create sequence employee_topic_id_seq start with 1 increment by 1;--over
---Employee course management--------
