delete from marks;
delete from subjects;
delete from students_lecturers;

insert into students_lecturers (id, name, birth_date, dtype) values(123, 'Vasya', '2000-01-01', 'Student');
insert into students_lecturers (id, name, birth_date, dtype) values(124, 'Sara', '2000-01-01', 'Student');
insert into students_lecturers (id, name, birth_date, dtype) values(125, 'Yosef', '2000-01-01', 'Student');
insert into students_lecturers (id, name, birth_date, dtype) values(126, 'David', '2000-01-01', 'Student');
insert into students_lecturers (id, name, birth_date, dtype) values(127, 'Rivka', '2000-01-01', 'Student');

insert into students_lecturers (id, name, birth_date, dtype) values(321, 'Vasily Abramovich', '1960-01-01', 'Lecturer');
insert into students_lecturers (id, name, birth_date, dtype) values(421, 'Sara Abramovna', '1960-01-01', 'Lecturer');

insert into subjects (id, name, hours, type, lecturer_id) values('S1', 'Java Core', 100, 'BACK_END', 321);
insert into subjects (id, name, hours, type, lecturer_id) values('S2', 'Java Technologies', 100, 'BACK_END', 321);
insert into subjects (id, name, hours, type, lecturer_id) values('S3', 'JS', 100, 'FRONT_END', 421);
insert into subjects (id, name, hours, type, lecturer_id) values('S4', 'React', 100, 'FRONT_END', 421);

insert into marks (student_id, subject_id, mark) values(123, 'S1', 100);
insert into marks (student_id, subject_id, mark) values(123, 'S2', 100);
insert into marks (student_id, subject_id, mark) values(123, 'S3', 100);
insert into marks (student_id, subject_id, mark) values(123, 'S4', 100);

insert into marks (student_id, subject_id, mark) values(127, 'S1', 90);
insert into marks (student_id, subject_id, mark) values(127, 'S2', 90);
insert into marks (student_id, subject_id, mark) values(127, 'S3', 90);

insert into marks (student_id, subject_id, mark) values(125, 'S1', 80);
insert into marks (student_id, subject_id, mark) values(125, 'S2', 80);

insert into marks (student_id, subject_id, mark) values(124, 'S1', 20);
insert into marks (student_id, subject_id, mark) values(124, 'S2', 20);
insert into marks (student_id, subject_id, mark) values(124, 'S3', 20);