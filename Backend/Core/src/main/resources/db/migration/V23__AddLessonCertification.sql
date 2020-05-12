create sequence lesson_certification_sequence_gen start 1 increment 1;
create sequence lesson_certification_result_sequence_gen start 1 increment 1;

create table un_lesson_certification(
    id int8 not null,
    lesson_id int8 not null,
    lesson_certification_type_id int8 not null,
    max_certification_score int4,
    primary key (id)
);


create table un_lesson_certification_type(
    id int8 not null,
    name varchar(255),
    primary key (id)
);

create table un_lesson_certification_value(
    id int8 not null,
    lesson_certification_id int8 not null,
    student_id int8 not null,
    certification_score int4,
    total_score int4,
    absence boolean,
    rating_date timestamp,
    primary key (id)
);

insert into un_lesson_certification_type(id,name) values(1,'Экзамен');
insert into un_lesson_certification_type(id,name) values(2,'Зачет');

alter table un_lesson_certification add constraint lessonIdConstraint foreign key (lesson_id) references un_lesson;
alter table un_lesson_certification add constraint lessonCertificationTypeConstraint foreign key (lesson_certification_type_id) references un_lesson_certification_type;

alter table un_lesson_certification_value add constraint lessonCertificationConstraint foreign key (lesson_certification_id) references un_lesson_certification;
alter table un_lesson_certification_value add constraint lessonCertificationStudentConstraint foreign key (student_id) references un_student;

