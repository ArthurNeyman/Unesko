/**
 * Архив отметок
 * Разработал: Константинов Вадим М-164
 * Дата: 15.03.2020
 **/

import {Component, OnInit} from "@angular/core";
import {User} from "../../../models/account/user.model";
import {AuthenticationService} from "../../../services/auth.service";
import "rxjs/add/operator/catch";
import {JournalService} from "../../../services/journal.service";
import {NotificationService} from "../../../services/notification.service";
import {Location} from '@angular/common';


@Component({
    selector: "archive-points",
    templateUrl: "./archivePoints.component.html",
    styleUrls: ["./archivePoints.component.css"]
})
export class ArchivePointsComponent implements OnInit {
    public user: User;
    public archivePoints: Array<any> = [];
    public filteredArchivePoints: Array<any> = [];
    public ru: any;
    public dateStart: any;
    public dateEnd: any;
    public yearPeriod: any;
    public searchText: String = '';

    constructor(
        private JournalService: JournalService,
        private authenticationService: AuthenticationService,
        private notification: NotificationService,
        private _location: Location
    ) {
        this.archivePoints = [];
        this.authenticationService.getUser().subscribe(res => {
            this.user = res.data;
            this.getArchivePoints();
        });
    }

    ngOnInit() {
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
        let currentYear: number = new Date().getFullYear();
        this.yearPeriod = (currentYear - 10) + ":" + (currentYear + 10);
        let numberEndDay: number = new Date(new Date().getFullYear(), new Date().getMonth() + 1, 0).getDate();
        this.dateStart = new Date(new Date().setDate(1));
        this.dateEnd = new Date(new Date().setDate(numberEndDay));
    }

    public getArchivePoints() {

        let dateStart: String = this.dateStart.getFullYear() + '-'
            + ((this.dateStart.getMonth() + 1) < 10 ? '0' + (this.dateStart.getMonth() + 1) : (this.dateStart.getMonth() + 1)) + '-'
            + (this.dateStart.getDate() < 10 ? '0' + this.dateStart.getDate() : this.dateStart.getDate())
            + ' 00:00:00';

        let dateEnd: String = this.dateEnd.getFullYear() + '-'
            + ((this.dateEnd.getMonth() + 1) < 10 ? '0' + (this.dateEnd.getMonth() + 1) : (this.dateEnd.getMonth() + 1)) + '-'
            + (this.dateEnd.getDate() < 10 ? '0' + this.dateEnd.getDate() : this.dateEnd.getDate())
            + ' 23:59:59';

        this.JournalService.GetArchivePoint(this.user.id, dateStart, dateEnd).subscribe(res => {
            this.archivePoints = res.data;
            this.filteredArchivePoints = JSON.parse(JSON.stringify(res.data));
        });
    }

    public startSearch() {
        this.filteredArchivePoints = JSON.parse(JSON.stringify(this.archivePoints));
        if (this.searchText) {
            this.filteredArchivePoints = this.filteredArchivePoints.filter(item => {
                return (
                    item.lessonTitle.toLowerCase().indexOf(this.searchText.toLowerCase()) > -1 ||
                    item.typePairTitle.toLowerCase().indexOf(this.searchText.toLowerCase()) > -1 ||
                    item.value.toString().toLowerCase().indexOf(this.searchText.toLowerCase()) > -1 ||
                    item.typePointTitle.toLowerCase().indexOf(this.searchText.toLowerCase()) > -1 ||
                    item.dayOfweek.toLowerCase().indexOf(this.searchText.toLowerCase()) > -1 ||
                    item.roomTitle.toLowerCase().indexOf(this.searchText.toLowerCase()) > -1
                );
            });
        }
    }

    public changePeriod() {
        if (this.dateEnd < this.dateStart) {
            this.notification.Warning("Дата окончания не может раньше даты начала", 'Ошибка при выборе периода');
            return;
        }
        this.getArchivePoints();
    }

    public cleanSearch() {
        this.searchText = '';
        this.filteredArchivePoints = JSON.parse(JSON.stringify(this.archivePoints));
    }

    comeBack() {
        this._location.back();
    }
}