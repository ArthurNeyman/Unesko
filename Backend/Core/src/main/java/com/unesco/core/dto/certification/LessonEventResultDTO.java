package com.unesco.core.dto.certification;

import com.unesco.core.dto.journal.LessonEventDTO;

import java.util.List;


public class LessonEventResultDTO {

    private LessonEventDTO lessonEventDTO;
    private List<LessonEventResultValueDTO> result;

    public LessonEventResultDTO(LessonEventDTO lessonEventDTO, List<LessonEventResultValueDTO> result) {
        this.lessonEventDTO = lessonEventDTO;
        this.result = result;
    }

    public LessonEventDTO getLessonEventDTO() {
        return lessonEventDTO;
    }

    public void setLessonEventDTO(LessonEventDTO lessonEventDTO) {
        this.lessonEventDTO = lessonEventDTO;
    }

    public List<LessonEventResultValueDTO> getResult() {
        return result;
    }

    public void setResult(List<LessonEventResultValueDTO> result) {
        this.result = result;
    }
}

