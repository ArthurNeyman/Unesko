create sequence lesson_certification_sequence_gen start 1 increment 1;
create sequence lesson_certification_result_sequence_gen start 1 increment 1;

create table un_intermediate_certification(
    id int8 not null,
    lesson_id int8 not null,
    lesson_certification_type_id int8 not null,
    max_certification_score int4,
    primary key (id)
);


create table un_intermediate_certification_type(
    id int8 not null,
    name varchar(255),
    primary key (id)
);

create table un_intermediate_certification_value(
    id int8 not null,
    lesson_certification_id int8 not null,
    student_id int8 not null,
    certification_score int4,
    total_score int4,
    absence boolean,
    rating_date timestamp,
    primary key (id)
);

insert into un_intermediate_certification_type(id,name) values(1,'Экзамен');
insert into un_intermediate_certification_type(id,name) values(2,'Зачет');
insert into un_intermediate_certification_type(id,name) values(3,'Зачет с оценкой');

alter table un_intermediate_certification add constraint lessonIdConstraint foreign key (lesson_id) references un_lesson;
alter table un_intermediate_certification add constraint intermediateCertificationTypeConstraint foreign key (intermediate_certification_type_id) references un_intermediate_certification_type;

alter table un_intermediate_certification_value add constraint intermediateCertificationConstraint foreign key (intermediate_certification_id) references un_intermediate_certification;
alter table un_intermediate_certification_value add constraint intermediateCertificationStudentConstraint foreign key (student_id) references un_student;

