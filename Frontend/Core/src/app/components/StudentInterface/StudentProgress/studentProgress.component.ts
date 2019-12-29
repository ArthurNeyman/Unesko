/**
 * Компонент просомтра баллов 
 * Разработал: Константинов Вадим М-164
 * Дата: 26.12.2019
**/

import {Component, OnInit } from "@angular/core";

@Component({
  selector: 'student-progress',
  templateUrl: "./studentProgress.component.html",
  styleUrls: ['./studentProgress.component.css']
})

export class StudentProgressComponent implements OnInit {

  public disciplins = [
    {
      id_discipline: 12,
      name_discipline: 'Иследование операций',
      current_points: 56,
      current_points_all: 100,
      attestation_points: 0,
      attestation_points_all: 40,
      finish_points: 56,
      appraisal: 'Хорошо'
    },
    {
      id_discipline: 13,
      name_discipline: 'Тестирование программного обеспечения',
      current_points: 52,
      current_points_all: 90,
      attestation_points: 11,
      attestation_points_all: 50,
      finish_points: 45,
      appraisal: 'Неудовлетворительно'
    },

  ];
  
  constructor() {
  }

  ngOnInit() {

  }
}