package com.unesco.core.dto.certification;

import com.unesco.core.dto.journal.StudentJournalDTO;

public class LessonEventResultValueDTO {

    private StudentJournalDTO studentDTO;
    private Integer value;

    public void setStudentDTO(StudentJournalDTO studentDTO) {
        this.studentDTO = studentDTO;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public StudentJournalDTO getStudentDTO() {
        return studentDTO;
    }

    public Integer getValue() {
        return value;
    }
}
