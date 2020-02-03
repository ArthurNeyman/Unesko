import { SemesterNumberYear } from './../../../models/semesterNumberYear.model';
import { Component, Injectable, Input, OnInit } from '@angular/core';
import { Lesson } from "../../../models/shedule/lesson";
import { JournalService } from '../../../services/journal.service';
import { CertificationReport, CertificationStudent } from '../../../models/journal/certificationReport.model';
import { DatePipe } from "@angular/common";
import { MessageService } from 'primeng/api';
import { Certification, CertificationValue } from '../../../models/journal/certification.model';


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

    public certificationList: Certification[]
    public selectedCertification: Certification

    constructor(private messageService: MessageService, private journalService: JournalService) {
    }

    ngOnInit(): void {
        this.getCertificationList()

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
                    if (result.data.certificationValueDTOList)
                        result.data.certificationValueDTOList.sort((first, second) => {
                            if (first.student.user.userFIO > first.student.user.userFIO)
                                return 1

                            if (first.student.user.userFIO < first.student.user.userFIO)
                                return -1

                            return 0
                        })
                    this.selectedCertification = this.getCertificationForSave(result.data)
                    
                }, error => {

                }
            );

        }
    }

    getCertificationList() {
        this.journalService.getCertificationList(this.lesson.id).subscribe(
            result => {
                this.certificationList = result.data
                this.certificationList = [...this.certificationList.map((el): Certification => {
                    el['name'] = this.datePipe.transform(el.startDate, "dd.MM.yyyy") + "-" + this.datePipe.transform(el.endDate, "dd.MM.yyyy");
                    return el
                })]
            }, error => {
            }
        );

    }

    getCertificationForSave(certification) {
        return new Certification(
            this.datePipe.transform(this.reportStartDate, "yyyy-MM-dd"),
            this.datePipe.transform(this.reportEndDate, "yyyy-MM-dd"),
            certification.studentCertification.map((el) => {
                return new CertificationValue(
                    0,
                    el.student,
                    el.certificationValue,
                    el.missingHours)
            }),
            this.lesson)
    }

    saveCertification() {
        // let id=this.selectedCertification.id
        // this.selectedCertification.certificationValueDTOList.map((el)=>{el.certificationId=id})
                
        this.journalService.saveCertification(this.selectedCertification).subscribe(
            (result) => {                
                this.selectedCertification = result.data
                console.log(this.selectedCertification)
                if (result.status == "OK") {
                    this.successSaveCertification()
                    this.getCertificationList()
                }
            }
        )
    }

    deleteCertification() {
        this.journalService.deleteCertification(this.selectedCertification).subscribe(result => {
            if (result.status == "OK") {
                this.getCertificationList();
                this.selectedCertification = null
                this.successDeleteCertification();
            }
        });
    }

    successSaveCertification() {
        this.messageService.add({ severity: 'success', summary: 'Успешно', detail: 'Аттестация сохранена' });
    }

    successDeleteCertification() {
        this.messageService.add({ severity: 'success', summary: 'Успешно', detail: 'Аттестация удалена' });
    }
}