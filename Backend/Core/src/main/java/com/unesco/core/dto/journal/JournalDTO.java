package com.unesco.core.dto.journal;

import com.unesco.core.dto.shedule.LessonDTO;

import java.util.List;

public class JournalDTO
{
   private LessonDTO lesson;
   private List<StudentJournalDTO> students;
   private List<ComparisonDTO> comparison;
   private List<PointDTO> journalCell;

   public JournalDTO() { }

   public LessonDTO getLesson() {
      return lesson;
   }
   public void setLesson(LessonDTO lesson) {
      this.lesson = lesson;
   }

   public List<StudentJournalDTO> getStudents() {
      return students;
   }
   public void setStudents(List<StudentJournalDTO> students) {
      this.students = students;
   }

   public List<ComparisonDTO> getComparison() {
      return comparison;
   }
   public void setComparison(List<ComparisonDTO> comparison) {
      this.comparison = comparison;
   }

   public List<PointDTO> getJournalCell() {
      return journalCell;
   }
   public void setJournalCell(List<PointDTO> journalCell) {
      this.journalCell = journalCell;
   }

}
