/**
 * Компонент просмотра аттестации для студента
 * Разработал: Константинов Вадим М-164
 * Дата: 13.05.2020
 **/

import { Component, OnInit } from "@angular/core";
import { User } from "../../../models/account/user.model";
import {SemesterNumberYear} from "../../../models/semesterNumberYear.model";
import {AuthenticationService} from "../../../services/auth.service";
import {LessonCertificationService} from '../../../services/lessonCertification.service';

@Component({
    selector: "certification-student",
    templateUrl: "./certificationStudent.component.html",
    styleUrls: ["./certificationStudent.component.css"]
})
export class CertificationStudentComponent implements OnInit {
    public user: User;
    public semesterNumberYear: SemesterNumberYear = new SemesterNumberYear();
    public certificationLessonList: any;
    public selectedCertificationLesson: any;
    public display: boolean = false;
    public modalInfo: any;

    constructor(
        private authenticationService: AuthenticationService,
        private service: LessonCertificationService,
    ) {
        this.authenticationService.getUser().subscribe(res => {
            this.user = res.data;
            this.getLessonListWithCertification();
        });
    }

    ngOnInit() {}

    public getLessonListWithCertification() {
        this.service.GetLessonListWithCertificationForStudent(this.user.id).subscribe(res => {
            console.log('getLessonListWithCertification res = ', res.data);
            this.certificationLessonList = res.data;
        });
    }
    public changeCertificationLesson() {
        console.log('changeCertificationLesson event = ', event, this.selectedCertificationLesson);
    }

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

    showInformationModal(modalInfo) {
        this.modalInfo = modalInfo;
        this.modalInfo.statusGotPoints = this.calculatePercent(this.modalInfo.currentPoints, this.modalInfo.maxGotPoints, this.modalInfo.certificationTypeId);
        this.modalInfo.statusCertificationPoints = this.calculatePercent(this.modalInfo.currentCertificationPoints, this.modalInfo.maxCertificationPoints, this.modalInfo.certificationTypeId);
        this.display = true;
        console.log('showInformationModal modalInfo = ', this.modalInfo);
    }

}
