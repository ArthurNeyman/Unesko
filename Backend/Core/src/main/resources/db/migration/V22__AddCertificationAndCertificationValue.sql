create sequence certification_sequence_gen start 1 increment 1;
create sequence certification_value_sequence_gen start 1 increment 1;

create table un_certification (id int8 not null, end_date timestamp, start_date timestamp, lesson_id int8, primary key (id));
create table un_certification_value (id int8 not null, certification_id int8, certification_value int4, missed_academic_hours int4, student_id int8, primary key (id));