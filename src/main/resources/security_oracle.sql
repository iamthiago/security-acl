<!-- Users -->

create table users(
    username varchar2(50 char) not null,
    password varchar2(50 char) not null,
    enabled number(2) not null,
    constraint username_pk primary key(username));

create table authorities (
    username varchar2(50 char) not null,
    authority varchar2(50 char) not null,
    constraint fk_authorities_users foreign key(username) references users(username),
    constraint ix_auth_username unique(username, authority));

<!-- Groups -->
create table groups (
  id number(19) not null,
  group_name varchar2(50 char) not null,
  constraint groups_id_pk primary key(id),
  constraint group_name_inx unique(group_name)
);
 
create sequence groups_seq
	increment by 1
	start with 100
	minvalue 1
	nocache
	noorder;

create or replace trigger groups_trg before insert
	on groups for each row
		declare
			integrity_error		exception;
			errno				integer;
			errmsg				char(200);
			dummy				integer;
			found				boolean;
			
	begin
		select groups_seq.nextval into :new.id from dual;
	exception
		when integrity_error then
			raise_application_error(errno, errmsg);
	end;

create table group_authorities (
  group_id number(19) not null,
  authority varchar2(50 char) not null,
  constraint fk_group_authorities_group foreign key(group_id) references groups(id),
  constraint group_auth_inx unique(group_id, authority));

create table group_members (
  id number(19) not null,
  username varchar2(50 char) not null,
  group_id number(19) not null,
  constraint fk_group_members_group foreign key(group_id) references groups(id),
  constraint group_members_inx unique(username, group_id));
  
create sequence group_members_seq
	increment by 1
	start with 100
	minvalue 1
	nocache
	noorder;

create or replace trigger group_members_trg before insert
	on group_members for each row
		declare
			integrity_error		exception;
			errno				integer;
			errmsg				char(200);
			dummy				integer;
			found				boolean;
			
	begin
		select group_members_seq.nextval into :new.id from dual;
	exception
		when integrity_error then
			raise_application_error(errno, errmsg);
	end;

<!-- ACL -->

create table acl_sid(
	id 			    number(19) 			    not null,
	principal 	number(1) 			    not null,
	sid 		    varchar2(100 char) 	not null,
	constraint acsi_pk primary key (id),
	constraint acsi_uk unique(sid, principal)
);

alter table acl_sid add constraint principal_boolean_chk check (principal in (1,0));

create sequence acl_sid_seq
	increment by 1
	start with 100
	minvalue 1
	nocache
	noorder;
	
create table acl_class(
	id 		  number(19) 			    not null,
	class 	varchar2(100 char) 	not null,
	constraint acl_class_pk primary key (id),
	constraint acl_class_unique_class unique(class)
);

create sequence acl_class_seq
	increment by 1
	start with 100
	minvalue 1
	nocache
	noorder;
 
create table acl_object_identity(
	id 					        number(19) 	 not null,
	object_id_class 	  number(19) 	 not null,
	object_id_identity 	number(19) 	 not null,
	parent_object 		  number(19),
	owner_sid 			    number(19),
	entries_inheriting 	number(1) 	 not null,
	constraint acoi_pk primary key (id),
	constraint acoi_uk unique(object_id_class,object_id_identity)
);

alter table acl_object_identity add constraint inheriting_boolean_chk check (entries_inheriting in (1,0));

alter table acl_object_identity
	add constraint acoi_acoi_fk foreign key (parent_object)
		references acl_object_identity (id)
		on delete cascade;
		
alter table acl_object_identity
	add constraint acoi_accl_fk foreign key (object_id_class)
		references acl_class (id)
		on delete cascade;
		
alter table acl_object_identity
	add constraint acoi_acsi_fk foreign key (owner_sid)
		references acl_sid (id)
		on delete cascade;
		
create sequence acl_object_identity_seq
	increment by 1
	start with 100
	minvalue 1
	nocache
	noorder;

create table acl_entry(
	id 					number(19) 	not null,
	acl_object_identity number(19) 	not null,
	ace_order 			number(10) 	not null,
	sid 				number(19) 	not null,
	mask 				number(10) 	not null,
	granting 			number(1) 	not null,
	audit_success		number(1) 	not null,
	audit_failure 		number(1) 	not null,
	constraint acen_pk primary key (id),
	constraint acen_uk unique (acl_object_identity,ace_order)
);

alter table acl_entry add constraint granting_boolean_chk check (granting in (1,0));
alter table acl_entry add constraint audit_success_boolean_chk check (audit_success in (1,0));
alter table acl_entry add constraint audit_failure_boolean_chk check (audit_failure in (1,0));

alter table acl_entry
	add constraint acen_acoi_fk foreign key (acl_object_identity)
		references acl_object_identity (id)
		on delete cascade;

alter table acl_entry
	add constraint acel_acsi_fk foreign key (sid)
		references acl_sid (id)
		on delete cascade;
		
create sequence acl_entry_seq
	increment by 1
	start with 100
	minvalue 1
	nocache
	noorder;
	
create or replace trigger acl_sid_trg before insert
	on acl_sid for each row
		declare
			integrity_error		exception;
			errno				integer;
			errmsg				char(200);
			dummy				integer;
			found				boolean;
			
	begin
		select acl_sid_seq.nextval into :new.id from dual;
	exception
		when integrity_error then
			raise_application_error(errno, errmsg);
	end;
  
create or replace trigger acl_class_trg before insert
	on acl_class for each row
		declare
			integrity_error		exception;
			errno				integer;
			errmsg				char(200);
			dummy				integer;
			found				boolean;
			
	begin
		select acl_class_seq.nextval into :new.id from dual;
	exception
		when integrity_error then
			raise_application_error(errno, errmsg);
	end;
  
create or replace trigger acl_object_identity_trg before insert
	on acl_object_identity for each row
		declare
			integrity_error		exception;
			errno				integer;
			errmsg				char(200);
			dummy				integer;
			found				boolean;
			
	begin
		select acl_object_identity_seq.nextval into :new.id from dual;
	exception
		when integrity_error then
			raise_application_error(errno, errmsg);
	end;
    
create or replace trigger acl_entry_trg before insert
	on acl_entry for each row
		declare
			integrity_error		exception;
			errno				integer;
			errmsg				char(200);
			dummy				integer;
			found				boolean;
			
	begin
		select acl_entry_seq.nextval into :new.id from dual;
	
	exception
		when integrity_error then
			raise_application_error(errno, errmsg);
	end;