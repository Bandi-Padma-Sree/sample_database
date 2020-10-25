use sample;
create table company(
	coid varchar(10),
    coname varchar(20),
    coloc varchar(10),
    primary key(coid)
);
create table product(
	pid varchar(10),
	pname varchar(20), 
	pcost varchar(7),
    coid varchar(10),
    primary key(pid),
    foreign key(coid) references company(coid)
);
create table customer(
	cid varchar(10),
    cname varchar(20),
    cadd varchar(30),
    cno varchar(10),
    cmail varchar(30),
    primary key(cid)
);
create table transaction(
	tid varchar(10),
    pid varchar(10),
    cid varchar(10),
    dob date,
    dod date,
    primary key(tid),
    foreign key(pid) references product(pid),
    foreign key(cid) references customer(cid)
);
alter table product add image mediumblob;
commit;