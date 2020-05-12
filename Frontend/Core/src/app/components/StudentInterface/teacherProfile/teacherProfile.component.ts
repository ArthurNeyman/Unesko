/**
 * Компонент просмотра профиля преподавателя
 * Разработал: Константинов Вадим М-164
 * Дата: 29.04.2020
 **/

import { Component, OnInit } from "@angular/core";
import { User } from "../../../models/account/user.model";
import {DictionaryService} from "../../../services/dictionary.service";
import {Dictionary} from "../../../models/admin/dictionary.model";
import {NotificationService} from "../../../services/notification.service";
import {Router} from "@angular/router";
import {ScheduleService} from "../../../services/schedule.service";
import {Professor} from "../../../models/account/professor";


@Component({
    selector: "teacher-profile",
    templateUrl: "./teacherProfile.component.html",
    styleUrls: ["./teacherProfile.component.css"]
})
export class TeacherProfileComponent implements OnInit {
    public user: User;
    public professors: Array<Professor> = [];
    public filteredProfessors: Array<Professor> = [];
    public currentProfessor: Professor;

    constructor(
                private dictionaryService: DictionaryService,
    ) {

        this.dictionaryService.Get(Dictionary.professors).subscribe(
            result => {
                if (result.content.length > 0) {
                    this.professors = result.content;
                }
                console.log('result', result);
            }
        );
    }

    ngOnInit() {
    }

    public getProfessors(event: any) {
        this.filteredProfessors = [];
        for (let info of this.professors) {
            if (info.user.userFIO.toLowerCase().indexOf(event.query.toLowerCase()) > -1) {
                this.filteredProfessors.push(info);
            }
        }
    }

}
