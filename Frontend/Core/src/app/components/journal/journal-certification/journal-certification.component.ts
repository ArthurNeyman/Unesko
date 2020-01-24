import { SemesterNumberYear } from './../../../models/semesterNumberYear.model';
import { Component, Injectable, Input, OnInit } from '@angular/core';
import { Lesson } from "../../../models/shedule/lesson";
import { JournalService, Certification, CertificationValue } from '../../../services/journal.service';
import { CertificationReport, CertificationStudent } from '../../../models/journal/certificationReport.model';
import { DatePipe } from "@angular/common";


@Component({
    selector: 'journal-certification',
    templateUrl: './journal-certification.component.html',
    styleUrls: ['./journal-certification.component.css']
})

@Injectable()
export class JournalCertificationComponent implements OnInit {

    @Input() semesterNumberYear: SemesterNumberYear;
    @Input() lesson: Lesson;

    reportStartDate: any;
    reportEndDate: any;

    public datePipe = new DatePipe("ru");
    public certificationReport: CertificationReport;
    public ru: any;

    private createCertificationElementVisible: Boolean = false;

    constructor(private journalService: JournalService) {
    }

    ngOnInit(): void {
        this.ru = {
            firstDayOfWeek: 1,
            dayNames: ["Воскресенье", "Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота"],
            dayNamesShort: ["Вск", "Пн", "Вт", "СР", "Чт", "Пт", "Сб"],
            dayNamesMin: ["Вск", "Пн", "Вт", "СР", "Чт", "Пт", "Сб"],
            monthNames: ["Январь", "Февраль", "Март", "Апрель", "Май", "Июнь", "Июль", "Август", "Сентябрь", "Октябрь", "Ноябрь", "Декабрь"],
            monthNamesShort: ["Янв", "Фев", "Мар", "Апр", "Май", "Июн", "Июл", "Авг", "Сен", "Окт", "Ноя", "Дек"],
            today: 'Сегодня',
            clear: 'Очистить'
        };
    }

    getCertification() {
        if (this.reportStartDate && this.reportEndDate) {
            let start = this.datePipe.transform(this.reportStartDate, "yyyy-MM-dd");
            let end = this.datePipe.transform(this.reportEndDate, "yyyy-MM-dd");
            this.journalService.GetJournalCertificationReport(this.lesson.id, start, end, this.lesson.semesterNumberYear).subscribe(
                result => {
                    this.certificationReport = result.data;
                }, error => {
                }
            );

        }
    }

    show() {
        console.log(this.lesson)
    }

    getCertificationForSave() {
        return new Certification(
            this.lesson.group.id,
            this.reportStartDate,
            this.reportEndDate,
            this.semesterNumberYear.semester,
            this.semesterNumberYear.year,
            this.certificationReport.studentCertification.map((el) => {
                return new CertificationValue(
                    0,
                    el.student.id,
                    el.certificationValue,
                    el.missingHours)
            }),
            this.certificationReport.lesson.id)
    }

    changeCertification(certification: Certification) {

    }
}