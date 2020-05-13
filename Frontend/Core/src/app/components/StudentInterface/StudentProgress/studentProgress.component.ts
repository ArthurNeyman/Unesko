/**
 * Компонент просмотров баллов
 * Разработал: Константинов Вадим М-164
 * Дата: 26.12.2019
 **/

import {Component, OnInit} from "@angular/core";
import {User} from "../../../models/account/user.model";
import {AuthenticationService} from "../../../services/auth.service";
import {Router} from "@angular/router";
import {HttpClient} from "@angular/common/http";
import "rxjs/add/operator/catch";
import {HandelErrorService} from "../../../services/handelError.service";
import {JournalService} from "../../../services/journal.service";
import {Location} from '@angular/common';


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
    public dates: Array<Date> = [];
    public showDetail: Array<any> = [];
    public numberYear: String = '0';
    public numberSemestr: String = '0';
    public searchText: String = '';

    constructor(
        private JournalService: JournalService,
        private http: HttpClient,
        private handleError: HandelErrorService,
        private authenticationService: AuthenticationService,
        private router: Router,
        private _location: Location
    ) {
        this.listLessons = [];
        this.authenticationService.getUser().subscribe(res => {
            this.user = res.data;
            this.getPoint();
        });
    }

    ngOnInit() {
    }

    comeBack() {
        this._location.back();
    }


    public startSearch() {
        this.setList(this.gotList);
        if (this.searchText) {
            this.listLessons = this.listLessons.filter(item => {
                return (
                    item.name_discipline.toLowerCase().indexOf(this.searchText.toLowerCase()) > -1
                );
            });
        }
    }

    cleanSearch() {
        this.searchText = '';
        this.setList(this.gotList);
    }


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

            let percentStr = percent.toString();

            if (percentStr.indexOf(".")) {
                percentStr = percent.toFixed(2);
            }

            let events = [];
            for (let eventId in item.eventsPairWithPoints) {
                let event = item.eventsPairWithPoints[eventId];
                events.push(event);
            }

            this.listLessons.push({
                max_value: item.maxValue,
                value: item.value,
                name_discipline: item.lesson.discipline.name,
                year: item.lesson.semesterNumberYear.year,
                semester: item.lesson.semesterNumberYear.semester,
                percent: percentStr,
                mark: mark,
                events: events
            });
        }
    }

    public startFilter() {
        this.setList(this.gotList);
        if (this.numberYear == '0' && this.numberSemestr == '0') {
            return;
        }
        this.listLessons = this.listLessons.filter(item => {
            let flagYear: Boolean = false, flagSemestr: Boolean = false;
            if (this.numberYear == '0' || this.numberYear == item.year) {
                flagYear = true;
            }
            if (this.numberSemestr == '0' || this.numberSemestr == item.semester) {
                flagSemestr = true;
            }
            return flagYear && flagSemestr;
        });
    }

    changeFilterYear(numberYear) {
        this.numberYear = numberYear;
        this.startFilter();
    }

    changeFilterSemestYear(numberSemester) {
        this.numberSemestr = numberSemester;
        this.startFilter();
    }

    changeShow(index) {
        if (this.showDetail[index]) {
            this.showDetail[index] = false;
        } else {
            this.showDetail[index] = true;
        }
    }
}
