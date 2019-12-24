import {Component, Injectable, Input, OnInit} from '@angular/core';
import {ScheduleService} from "../../../services/schedule.service";
import {NotificationService} from "../../../services/notification.service";
import {JournalService} from "../../../services/journal.service";
import {LessonEvent} from "../../../models/journal/lessonEvent.model";
import {Comparison, PointType} from "../../../models/journal/journal.model";
import {DictionaryService} from "../../../services/dictionary.service";
import {UtilsService} from "../../../services/utils.service";
import {isUndefined} from "util";
import {StatusType} from "../../../models/statusType.model";
import {DatePipe} from "@angular/common";
import {Lesson} from "../../../models/shedule/lesson";
import {VisitationConfig} from "../../../models/journal/visitationConfig.model";
import {Observable} from "rxjs/Observable";
import {Dictionary} from "../../../models/admin/dictionary.model";
import {Pair} from "../../../models/shedule/pair";
import {SemesterNumberYear} from "../../../models/semesterNumberYear.model";

@Component({
    selector: 'lesson-configurator',
    templateUrl: './lesson-configurator.component.html',
    styleUrls: ['./lesson-configurator.component.css']
})
@Injectable()
export class LessonConfiguratorComponent implements OnInit {

    @Input() lesson: Lesson;
    @Input() semesterNumberYear: SemesterNumberYear;
    public dates: Array<Date> = [];
    public events: Array<LessonEvent> = [];
    public eventTypes: Array<PointType> = [];
    public model: LessonEvent = new LessonEvent();
    public visitationConfig: VisitationConfig = new VisitationConfig();
    public dataVisitation: Array<DataVisitation> = [];
    public ru;
    public allDisabledDates: Array<Date>;
    public disabledDates: Array<Date>;
    public datePipe = new DatePipe("en");
    public findsPairsForDate: Array<Pair> = [];
    public selectPair: Array<Pair> = [];
    public selectAllPair: boolean = false;

    public editMode: boolean = false;
    public editVisitable: boolean = false;

    public loadingEventList: boolean = true;

    constructor(private utilsService: UtilsService,
                private ScheduleService: ScheduleService,
                private journalService: JournalService,
                private dictionaryService: DictionaryService,
                private notificationService: NotificationService) {
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
        this.disableUnusedDays().subscribe(
            result => {
                this.getVisitationConfig();
            }
        );
        this.getPointsType();
        this.getEvents();
        this.model.lesson = this.lesson;
        this.visitationConfig.lesson = this.lesson;
    }

    getVisitationConfig() {
        this.journalService.GetVisitation(this.lesson.id)
            .subscribe(result => {
                if (!isUndefined(result) && !isUndefined(result.data) && result.data != null) {
                    this.visitationConfig = result.data;
                    for (let date of this.visitationConfig.dates) {
                        this.dataVisitation.forEach(function (a) {
                            if (a.date === date) a.active = true;
                        });
                    }
                    if (this.visitationConfig.autoGenerated !== true && this.visitationConfig.value !== 1)
                        this.editVisitable = true;
                }
            });
    }

    getEvents() {
        this.journalService.GetEvents(this.lesson.id)
            .subscribe(result => {
                this.loadingEventList = false;
                this.events = result.data;
            });
    }

    updateDisabledDates(event) {
        /* Прибавление еденицы к месяцу обаснованно тем,
        * что объект Date() возвращает месяц с нуля, а
        * календарь начиная с нуля.
        */
        let daysInMonth = new Date(event.year, event.month, 0).getDate();
        this.disabledDates = [];
        // Отключаем даты в текущем месяце
        for (let i = 1; i < daysInMonth + 1; i++) {
            let find = this.allDisabledDates.find(function (element) {
                let bool = element.getFullYear() === event.year
                    && (element.getMonth() + 1) === (event.month)
                    && element.getDate() === i;
                return bool;
            });
            if (isUndefined(find))
                this.disabledDates.push(new Date(event.year, event.month - 1, i));
        }
    }

    changeDate(event: Date) {
        this.journalService.GetJournal(this.lesson.id, event.getMonth(), this.semesterNumberYear).subscribe(
            result => {
                let comparison: Array<Comparison> = result.data.comparison;
                let find = comparison.find(x =>
                    this.createDate(x.date).getFullYear() == this.createDate(event).getFullYear()
                    && this.createDate(x.date).getMonth() == this.createDate(event).getMonth()
                    && this.createDate(x.date).getDate() == this.createDate(event).getDate());
                this.findsPairsForDate = find.points.filter(x => x.type.id == 0).map(x => x.pair);
            }
        );
    }

    disableUnusedDays(): Observable<any> {
        return this.journalService.GetJournalDates(this.lesson.id, this.semesterNumberYear).do(
            result => {
                this.dates = result.data;
                this.allDisabledDates = [];
                for (let date of this.dates) {
                    this.dataVisitation.push(new DataVisitation(date));
                    let temp = new Date(this.datePipe.transform(date, "yyyy-MM-ddTHH:mm:ss"));
                    this.allDisabledDates.push(temp);
                }
                let date: Date = new Date();
                this.updateDisabledDates({year: date.getFullYear(), month: date.getMonth() + 1});
            }
        );
    }

    getPointsType() {
        this.dictionaryService.Get(Dictionary.pointTypes)
            .subscribe(result => {
                this.eventTypes = result.content;
                let deleteItem = this.eventTypes.findIndex(i => i.name === "Посещение");
                this.eventTypes.splice(deleteItem, 1);
                this.model.type = this.eventTypes[0];
            });
    }

    Save() {
        if (this.selectPair.length == 0 && this.findsPairsForDate.length > 1 && !this.selectAllPair) {
            this.notificationService.Error("Укажите занятие для создания контрольной точки.");
            return;
        }
        if (this.selectPair.length != 0)
            this.model.pairs = this.selectPair;

        this.journalService.SaveEvent(this.model).subscribe(
            result => {
                this.selectPair = [];
                this.findsPairsForDate = [];
                this.selectAllPair = false;
                if (result.status === StatusType.OK.toString()) {
                    this.getEvents();
                    this.CancelEdit();
                }
                this.notificationService.FromStatus(result);
            }, error1 => {
                this.selectPair = [];
                this.findsPairsForDate = [];
                this.selectAllPair = false;
            }
        );
    }

    SaveVisitation() {
        this.visitationConfig.dates = this.dataVisitation.filter(function (a) {
            if (a.active) return a;
        }).map(x => x.date);
        this.journalService.SaveVisitation(this.visitationConfig).subscribe(
            result => {
                if (result.status === StatusType.OK.toString()) {
                    this.getVisitationConfig();
                }
                this.notificationService.FromStatus(result);
            }
        );
    }

    Remove(id: number) {
        this.CancelEdit();
        this.journalService.EventDelete(id).subscribe(
            result => {
                if (result.status === StatusType.OK.toString())
                    this.getEvents();
                this.notificationService.FromStatus(result);
            }
        );
    }

    Edit(model: LessonEvent) {
        if (model.date) {
            let tempDate = new Date(this.datePipe.transform(model.date, "yyyy-MM-ddTHH:mm:ss"));
            model.date = tempDate;
        }
        this.editMode = true;
        this.model.maxValue = model.maxValue;
        this.model.type = model.type;
        this.model.comment = model.comment;
        this.model.date = model.date;
        this.model.id = model.id;
        this.model.lesson = model.lesson;
        this.model.pairs = [];
        this.selectPair = [];
        for (let p of model.pairs) {
            this.selectPair.push(p);
            this.model.pairs.push(p);
        }
        if (this.model.pairs.length == this.findsPairsForDate.length) this.selectAllPair = true;

        this.changeDate(model.date);

    }

    CancelEdit() {
        this.editMode = false;
        this.model = new LessonEvent();
        this.model.lesson = this.lesson;
        this.model.type = this.eventTypes[0];

        this.selectAllPair = false;
        this.selectPair = [];
        this.findsPairsForDate = [];
    }

    changeVisiationAutoGenerated() {
        if (this.visitationConfig.autoGenerated) {
            this.visitationConfig.dates = [];
            this.dataVisitation.forEach(function (a) {
                a.active = false;
            });
        }
    }

    getSummForVisitation() {
        if (this.visitationConfig.autoGenerated) {
            return this.dates.length * this.visitationConfig.value;
        } else {
            return this.visitationConfig.dates.length * this.visitationConfig.value;
        }
    }

    getSumm() {
        let summ = 0;
        for (let event of this.events) {
            summ += event.maxValue;
        }
        summ += this.getSummForVisitation();
        return summ;
    }

    clearSelect() {
        if (!this.selectAllPair)
            this.selectPair = [];
        if (this.selectAllPair) {
            for (let p of this.findsPairsForDate)
                this.selectPair.push(p);
        }
    }

    createDate(date: Date): Date {
        return new Date(this.datePipe.transform(date, "yyyy-MM-ddTHH:mm:ss"));
    }

    changeSelectPair(p) {
        if (!this.pairInSelect(p)) {
            this.selectPair.push(p);
            if (this.selectPair.length == this.findsPairsForDate.length) this.selectAllPair = true;
        }
        else {
            this.selectPair.splice(this.selectPair.map(x => x.id).indexOf(p.id), 1);
            this.selectAllPair = false;
        }
    }

    pairInSelect(pair: Pair) {
        for (let p of this.selectPair) {
            if (p.id == pair.id) return true;
        }
        return false;
    }

}

class DataVisitation {
    date: Date;
    active: boolean;

    constructor(date: Date) {
        this.date = date;
        this.active = false;
    }
}





