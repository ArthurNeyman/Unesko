package com.unesco.core.dto.shedule;

import com.unesco.core.dto.account.StudentDTO;

public class StudentLessonsDTO {


    public StudentLessonsDTO() {}

    private long id;
    private int subgroup;
    private StudentDTO student;
    private LessonDTO lesson;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getSubgroup() {
        return subgroup;
    }

    public void setSubgroup(int subgroup) {
        this.subgroup = subgroup;
    }

    public StudentDTO getStudent() {
        return student;
    }

    public void setStudent(StudentDTO student) {
        this.student = student;
    }

    public LessonDTO getLesson() {
        return lesson;
    }

    public void setLesson(LessonDTO lesson) {
        this.lesson = lesson;
    }
}
