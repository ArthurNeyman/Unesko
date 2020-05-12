import {MenuItem, SelectItem} from 'primeng/api';
import {Student} from './../../models/account/student';
import {JournalService} from './../../services/journal.service';
import {LessonCertification, LessonCertificationValue} from './../../models/journal/certification.model';
import {Component, OnInit, Input, Directive, HostListener, ElementRef} from '@angular/core';
import {User} from '../../models/account/user.model';
import {SemesterNumberYear} from '../../models/semesterNumberYear.model';
import {LessonCertificationService} from '../../services/lessonCertification.service';
import {AccountService} from '../../services/account.service';


@Directive({
    selector: '[listenInputCertification]'
})
export class ListenInputCertification {

    constructor(private el: ElementRef) {
    }

    @Input("maxValue") maxValue: number;

    @HostListener('document:keyup', ['$event']) onKeyupHandler(event: KeyboardEvent) {
        if (this.el.nativeElement.value > this.maxValue) {
            this.el.nativeElement.value = this.maxValue;
            return false;
        }
    }

    @HostListener('document:keydown', ['$event']) onKeydownHandler(event: KeyboardEvent) {
        if (this.el.nativeElement.value > this.maxValue) {
            this.el.nativeElement.value = this.maxValue;
            return false;
        }
    }
}

@Component({
    selector: 'lesson-certification',
    templateUrl: "./lessonCertification.component.html",
    styleUrls: ["./lessonCertification.component.css"]
})

export class LessonCertificationComponent implements OnInit {

    lessonCertificationResultList: any;
    lessonCertificationCurrentOnly: boolean = false;
    certificationList: any;
    maxEventValue: number;
    allHours: number;

    private show: boolean = false;
    public semesterNumberYear: SemesterNumberYear = new SemesterNumberYear();
    public lessonCertificationList: LessonCertification[];
    private lessonCertificationListForMenu: SelectItem[];
    public selectedLessonCertification: LessonCertification;
    private eventList: any[];


    ngOnInit(): void {
        this.getProfessorLessons();
    }

    @Input() user: User;

    constructor(
        private service: LessonCertificationService,
        private accauntService: AccountService,
        private journalService: JournalService) {
    }

    //Получить список дисциплин для преподавателя в выбраном учебно периоде
    public getProfessorLessons() {
        this.selectedLessonCertification = null;
        this.accauntService.GetProfessorByUser(this.user.id).subscribe(
            result => {
                return this.service.getLessonCertificationList(this.semesterNumberYear, result.data.id.toString()).subscribe(
                    res => {
                        this.lessonCertificationList = res.data;
                        this.lessonCertificationListForMenu = [];
                        this.lessonCertificationList.forEach(el => {
                            this.lessonCertificationListForMenu.push(
                                {
                                    "label": el.lesson.discipline.name,
                                    "value": el,
                                    "disabled": el.lessonCertificationType.id == 0
                                }
                            );
                        });
                        this.show = true;
                    }, error => {

                    });
            }
        );


    }


    public setMaxScore() {
        if (this.selectedLessonCertification.lessonCertificationType.id != 0)
            this.service.setMaxScore(this.selectedLessonCertification).subscribe();
    }

    //Получить данные аттестации для конкретной дисциплины-группы
    public loadData(item: LessonCertification) {
        this.selectedLessonCertification = item;

        this.journalService.getAcademiPerformanceReport(this.semesterNumberYear, this.user.id.toString()).subscribe(
            result => {
                let certification = result.data.lessonList.filter(el => {
                    return el.lesson.id == this.selectedLessonCertification.lesson.id;
                })[0];
                this.certificationList = certification.studentCertification;
                this.maxEventValue = certification.allEventValue;
                this.allHours = certification.allHours / 2;
                this.getEvents();

                this.service.getLessonCertificationResult(this.selectedLessonCertification).subscribe(
                    res => {
                        this.lessonCertificationResultList = res.data;
                        this.lessonCertificationResultList.forEach(element => {
                            element.totalScore = this.getTotalScore(element);
                            element.currentScore = this.getCurrentScore(element.studentDTO);
                        });
                    }
                );
            }, error => {

            }
        );


    }

    public getCurrentScore(student: Student) {
        if (this.certificationList) {
            let value = 0;
            let stud = this.certificationList.filter(el => {
                return el.student.id == student.id;
            })[0];
            if (stud) {
                stud.eventValue.forEach(element => {
                    value += element.value;
                });
                value += stud.visitationValue / 2;
            }
            return value;
        } else return 0;
    }

    public getTotalScore(item: LessonCertificationValue) {
        let res = (100 / (this.maxEventValue +
            (!this.lessonCertificationCurrentOnly ? this.selectedLessonCertification.maxCertificationScore : 0) + this.allHours)) *
            (
                (item.certificationScore <= this.selectedLessonCertification.maxCertificationScore ?
                    (!this.lessonCertificationCurrentOnly ? item.certificationScore : 0) : this.selectedLessonCertification.maxCertificationScore)
                + this.getCurrentScore(item.studentDTO));
        item.totalScore = Math.round(res);
        return Math.round(res);
    }

    public getMark(totalScore: number) {
        if (totalScore > 51)
            if (totalScore > 66)
                if (totalScore > 85)
                    return "5";
                else
                    return "4";
            else
                return "3";
        else
            return "2";
    }

    public getEvents() {
        this.service.getLessonEvents(this.selectedLessonCertification.lesson.id).subscribe(
            res => {
                this.eventList = [];
                for (let key in res.data) {
                    this.eventList.push({
                        "event": key,
                        "value": res.data[key]
                    });
                }
                this.eventList.push({
                    "event": "Посещение",
                    "value": this.allHours
                });
                this.eventList.push({
                    "event": "Общий балл",
                    "value": this.allHours + this.maxEventValue
                });
            });
    }

    public test() {
        this.service.saveLessonCertificationresult(this.lessonCertificationResultList).subscribe(
            res => {
                this.lessonCertificationResultList = res.data;
            }
        );
    }
}