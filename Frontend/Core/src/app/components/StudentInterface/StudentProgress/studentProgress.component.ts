/**
 * Компонент просомтра баллов
 * Разработал: Константинов Вадим М-164
 * Дата: 26.12.2019
 **/

import { Component, OnInit } from "@angular/core";
import { User } from "../../../models/account/user.model";
import { AuthenticationService } from "../../../services/auth.service";
import { Router } from "@angular/router";
import { HttpClient, HttpParams } from "@angular/common/http";
import "rxjs/add/operator/catch";
import { catchError, map } from "rxjs/operators";
import { HandelErrorService } from "../../../services/handelError.service";
import { JournalService } from "../../../services/journal.service";

@Component({
  selector: "student-progress",
  templateUrl: "./studentProgress.component.html",
  styleUrls: ["./studentProgress.component.css"]
})
export class StudentProgressComponent implements OnInit {
  public user: User;
  public listLessons: any;
  public gotList: any;
  public yearsStudy: any;
  public selectSemester: any;

  constructor(
    private JournalService: JournalService,
    private http: HttpClient,
    private handleError: HandelErrorService,
    private authenticationService: AuthenticationService,
    private router: Router
  ) {
    this.listLessons = [];
    this.authenticationService.getUser().subscribe(res => {
      this.user = res.data;
      this.getPoint();
    });
  }

  ngOnInit() {}

  public getPoint() {
    this.JournalService.GetStudentPerformance(this.user.id).subscribe(res => {
      this.gotList = res.data;
      this.yearsStudy = [];
      this.setList(res.data);
    });
  }

  setList(list) {
    this.listLessons = [];
    for (let item of list) {
      if (this.yearsStudy.indexOf(item.lesson.semesterNumberYear.year) < 0) {
        this.yearsStudy.push(item.lesson.semesterNumberYear.year);
      }
      let onePercent = item.maxValue / 100;
      let percent = 0;
      if (onePercent) {
        percent = item.value / onePercent;
      }
      let mark = "Неудовлетворительно";
      if (percent > 85) {
        mark = "Отлично";
      } else if (percent > 65) {
        mark = "Хорошо";
      } else if (percent > 50) {
        mark = "Удовлетворительно";
      }

      this.listLessons.push({
        max_value: item.maxValue,
        value: item.value,
        name_discipline: item.lesson.discipline.name,
        year: item.lesson.semesterNumberYear.year,
        semester: item.lesson.semesterNumberYear.semester,
        percent: percent,
        mark: mark
      });
    }
  }

  filterByYear(numberYear) {
    this.setList(this.gotList);
    if (numberYear == 0) {
      this.setList(this.gotList);
      return;
    }
    this.listLessons = this.listLessons.filter(item => {
      return item.year == numberYear;
    });
  }

  filterBySemester(numberSemester) {
    this.setList(this.gotList);
    this.listLessons = this.listLessons.filter(item => {
      if (numberSemester == 0) return true;
      return item.semester == numberSemester;
    });
  }

  consoleLog(event) {
    console.log("consoleLog = ", event);
  }
}
