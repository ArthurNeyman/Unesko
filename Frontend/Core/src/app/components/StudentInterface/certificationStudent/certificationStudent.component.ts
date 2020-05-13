/**
 * Компонент просмотра аттестации для студента
 * Разработал: Константинов Вадим М-164
 * Дата: 13.05.2020
 **/

import {Component, OnInit} from "@angular/core";
import {User} from "../../../models/account/user.model";
import {SemesterNumberYear} from "../../../models/semesterNumberYear.model";
import {AuthenticationService} from "../../../services/auth.service";
import {LessonCertificationService} from '../../../services/lessonCertification.service';
import {forEach} from "@angular/router/src/utils/collection";

@Component({
    selector: "certification-student",
    templateUrl: "./certificationStudent.component.html",
    styleUrls: ["./certificationStudent.component.css"]
})
export class CertificationStudentComponent implements OnInit {
    public user: User;
    public semesterNumberYear: SemesterNumberYear = new SemesterNumberYear();
    public certificationLessonList: any = [];
    public defaultList: any = [];
    public selectedCertificationLesson: any;
    public display: boolean = false;
    public modalInfo: any;
    public statusCertification: any = 3;
    public countBadCertification: any = 0;
    public filterBadCertification: any = false;
    public searchText: String = '';

    constructor(
        private authenticationService: AuthenticationService,
        private service: LessonCertificationService,
    ) {
        this.authenticationService.getUser().subscribe(res => {
            this.user = res.data;
            this.getLessonListWithCertification();
        });
    }

    ngOnInit() {
    }

    // Получить список занятий с аттестацией
    public getLessonListWithCertification() {
        this.service.GetLessonListWithCertificationForStudent(this.user.id).subscribe(res => {
            this.defaultList = res.data;
            this.certificationLessonList = res.data;
            this.certificationLessonList.forEach(lesson => {
                if (lesson.statusCertificationId < 3) this.countBadCertification++;
                if (this.statusCertification > lesson.statusCertificationId) {
                    this.statusCertification = lesson.statusCertificationId;
                }
            });

            this.certificationLessonList.sort((a, b) => {
                if (a.statusCertificationId < 3 || b.statusCertificationId < 3) {
                    if (a.statusCertificationId < b.statusCertificationId) {
                        return -1;
                    }
                    if (a.statusCertificationId > b.statusCertificationId) {

                    }
                    return 1;

                    if (a.lesson.semesterNumberYear.year < b.lesson.semesterNumberYear.year) {
                        return 1;
                    } else {
                        return -1;
                    }
                }
                if (a.lesson.semesterNumberYear.year < b.lesson.semesterNumberYear.year) {
                    return 1;
                } else {
                    return -1;
                }


            });
        });
    }

    // Расчитать процент баллов
    calculatePercent(cur, max, certificationTypeId) {
        let onePercent = max / 100;
        let percent = 0;
        if (onePercent > 0) {
            percent = cur / onePercent; // процент успеваемости студента по предмету
        }
        let status = 0;
        // Определяем статус аттестации
        if (certificationTypeId == 2) {
            status = percent > 50 ? 6 : 1;
        } else {
            status = 2;
            if (percent > 85) {
                status = 5;
            } else if (percent > 65) {
                status = 4;
            } else if (percent > 50) {
                status = 3;
            }
        }
        return status;
    }

    // Показать модалку
    showInformationModal(modalInfo) {
        this.modalInfo = modalInfo;
        this.modalInfo.statusGotPoints = this.calculatePercent(this.modalInfo.currentPoints, this.modalInfo.maxGotPoints, this.modalInfo.certificationTypeId);
        this.modalInfo.statusCertificationPoints = this.calculatePercent(this.modalInfo.currentCertificationPoints, this.modalInfo.maxCertificationPoints, this.modalInfo.certificationTypeId);
        this.display = true;
    }

    // Фильтрация предметов по не сданным
    filterByBadCertification() {
        this.filterBadCertification = true;

        // Клонируем без ссылкы
        this.certificationLessonList = JSON.parse(JSON.stringify(this.defaultList));
        this.certificationLessonList = this.certificationLessonList.filter(lesson => {
            return lesson.statusCertificationId < 3 ? true : false;
        });
    }

    // Сбросить фильтрацию списка
    resetList() {
        // Клонируем без ссылкы
        this.certificationLessonList = JSON.parse(JSON.stringify(this.defaultList));

        this.filterBadCertification = false;
    }

    public startSearch() {
        if (this.filterBadCertification) {
            this.filterByBadCertification();
        } else {
            this.resetList();
        }
        if (this.searchText) {
            this.certificationLessonList = this.certificationLessonList.filter(item => {
                return (
                    item.lesson.discipline.name.toLowerCase().indexOf(this.searchText.toLowerCase()) > -1 ||
                    item.lesson.semesterNumberYear.year.toString().toLowerCase().indexOf(this.searchText.toLowerCase()) > -1 ||
                    item.lesson.semesterNumberYear.semester.toString().toLowerCase().indexOf(this.searchText.toLowerCase()) > -1 ||
                    item.statusCertification.toLowerCase().indexOf(this.searchText.toLowerCase()) > -1
                );
            });
        }
    }

    public cleanSearch() {
        this.searchText = '';
        if (this.filterBadCertification) {
            this.filterByBadCertification();
        } else {
            this.resetList();
        }
    }

}
