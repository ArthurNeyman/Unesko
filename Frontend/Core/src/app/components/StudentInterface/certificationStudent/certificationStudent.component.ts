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

}
