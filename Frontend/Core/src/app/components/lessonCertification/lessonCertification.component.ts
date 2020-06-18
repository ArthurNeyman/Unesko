import { SemesterNumberYear } from './../../models/semesterNumberYear.model';
import { SelectItem, MessageService } from 'primeng/api';
import { Student } from './../../models/account/student';
import { LessonCertification, LessonCertificationValue } from './../../models/journal/certification.model';
import { Component, OnInit, Input, Directive, HostListener, ElementRef, Output, EventEmitter } from '@angular/core';
import { User } from '../../models/account/user.model';
import { LessonCertificationService } from '../../services/lessonCertification.service';
import { AccountService } from '../../services/account.service';
import { OverlayPanel } from 'primeng/overlaypanel';
import { ExcelService } from '../../services/excelService.service';
import { isNumber } from 'util';

@Directive({
    selector: '[listenInputCertification]',
    host: {
        '(input)': 'update($event)' // I changed it to input to see the changes immediatly
    }
})
export class ListenInputCertification  {

    constructor(private el: ElementRef) {
    }

    @Input("maxValue") maxValue: number;
    @Output() valueChange = new EventEmitter();

    update(event){
        
        if (this.el.nativeElement.value < 0 ) {
            this.el.nativeElement.value = 0;
            this.valueChange.emit(0);
        }

        if (this.el.nativeElement.value > this.maxValue) { 
            this.el.nativeElement.value = this.maxValue;    
            this.valueChange.emit(this.maxValue);
        }
    }
}

@Component({
    selector: 'lesson-certification',
    templateUrl: "./lessonCertification.component.html",
    styleUrls: ["./lessonCertification.component.css"]
})

export class LessonCertificationComponent implements OnInit {

    public lessonCertificationResultList: LessonCertificationValue[];
    public newMaxCertificationScore : number;
    public showLoader: boolean = true;
    public lessonCertificationCurrentOnly: boolean = false;
    public semesterNumberYear: SemesterNumberYear = new SemesterNumberYear();
    public selectedLessonCertification: LessonCertification;
    public lessonCertificationList: LessonCertification[];
    public lessonCertificationListForMenu: SelectItem[];
    public eventList: any[];
    public maxVisitValue: number;

    public ru: any; //руссификатор календаря

    public selectStud;//Выделеный при наведении на лупу студент

    public cols: any[];
    public brands : any[]

    ngOnInit(): void {

  
        this.getProfessorLessons();

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

        this.cols=[
            {field: 'student', header: 'Студент',width:'30%'},
            {field: 'currentScore', header: 'Текущий балл',width:'10%'},
            {field: 'certificationScore', header: 'Аттестационный балл',width:'15%'},
            {field: 'totalScore', header: 'Общий балл',width:'7%'},
            {field: 'ratingDate', header: 'Дата',width:'13%'},
            {field: 'absence', header: 'Неявка',width:'8%'},
            {field: 'mark', header: 'Оценка',width:'17%'},
        ]
    }

    @Input() user: User;

    constructor(
        private excelService: ExcelService,
        private messageService: MessageService,
        private service: LessonCertificationService,
        private accauntService: AccountService,) {
    }

    //Получить список дисциплин для преподавателя в выбраном учебном периоде по предмету
    public getProfessorLessons() {
        this.selectedLessonCertification = null;
        this.accauntService.GetProfessorByUser(this.user.id).subscribe(
            result => {
                return this.service.getLessonCertificationList(this.semesterNumberYear, result.data.id.toString()).subscribe(
                    res => {
                        console.log(res);
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
                    }, error => {

                    });
            }
        );


    }

    //Сохранить максимальный аттестационный балл
    public setMaxScore() {
        let lessonCertification = {
            ...this.selectedLessonCertification,
            maxCertificationScore: this.newMaxCertificationScore,
        }

        if (lessonCertification.lessonCertificationType.id != 0)
            this.service.setMaxScore(lessonCertification).subscribe(res => {
                if (res.status == "OK") {
                    this.selectedLessonCertification.maxCertificationScore = this.newMaxCertificationScore;
                    this.changeCurrentOnly()
                    this.messageService.add({ severity: 'success', summary: 'Успешно', detail: 'Максимальный аттестационный балл успешно изменен' });
                }
            });
    }

    //Получить данные аттестации для конкретной дисциплины-группы
    public loadData(item: LessonCertification) {

        this.showLoader = true;
        this.selectedLessonCertification = item;

        this.brands = this.selectedLessonCertification.lessonCertificationType.id == 2 ?  [
            { label: 'Все оценки', value: null },
            { label: 'Зачтено', value: 'Зачтено' },
            { label: 'Не зачтено', value: 'Не зачтено' }
        ]: [ 
        { label: 'Все оценки', value: null },
        { label: 'Отлично', value: 'Отлично' },
        { label: 'Хорошо', value: 'Хорошо' },
        { label: 'Удовлетворительно', value: 'Удовлетворительно' },
        { label: 'Неудовлетворительно', value: 'Неудовлетворительно' }

    ]
        this.newMaxCertificationScore = item.maxCertificationScore
        this.getEvents();
        this.getLessonCertificationResults()

    }

    public getLessonCertificationResults() {
        this.showLoader = true;
        this.service.getLessonCertificationResult(this.selectedLessonCertification, this.semesterNumberYear, this.lessonCertificationCurrentOnly).subscribe(
            res => {               
                this.lessonCertificationResultList = res.data;
                this.showLoader = false;
            }
        )
    }
    //Получить список событий(посещаемость,лабы и тд)
    public getEvents() {
        this.service.getLessonEvents(this.selectedLessonCertification.lesson.id, this.semesterNumberYear).subscribe(
            res => {
                this.eventList = [];
                let total
                for (let key in res.data) {
                    if (key != "Общий балл")
                        this.eventList.push({
                            "event": key,
                            "value": res.data[key]
                        });
                    if (key == "Посещение") {
                        this.maxVisitValue = res.data[key]
                    }
                    else
                        total = {
                            "event": key,
                            "value": res.data[key]
                        }
                }
                this.eventList.push(total)
            });
    }

    //Сохранение текущей аттестации
    public save() {
        this.service.saveLessonCertificationResult(this.lessonCertificationResultList).subscribe(
            res => {
                if (res.status === "OK") {
                    this.lessonCertificationResultList = res.data;
                    this.messageService.add({ severity: 'success', summary: 'Успешно', detail: 'Аттестация сохранена' });
                } else {
                    this.messageService.add({ severity: 'arror', summary: 'Ощибка', detail: 'Аттестация не сохранена' });
                }
            }
        );
    }

    //Выбор и загрузка данных студента при наведении на лупу
    public selectStudent(event, student: Student, overlaypanel: OverlayPanel) {
        this.service.getStudentCertification(this.selectedLessonCertification.lesson.id, student.id, this.lessonCertificationCurrentOnly).subscribe(
            res => {
                this.selectStud = res.data;
                overlaypanel.toggle(event);
            }
        );
    }

    //Действие при изменение параметра расчета аттестации (только по текущему баллу)
    public changeCurrentOnly() {
        this.lessonCertificationResultList.map(el => {
            this.update(el);
        })
    }

    //Обновление аттестации конкретного студента
    public update(lessonCertificationResult: LessonCertificationValue) {

        lessonCertificationResult.certificationScore =
            lessonCertificationResult.certificationScore <= this.selectedLessonCertification.maxCertificationScore ?
                lessonCertificationResult.certificationScore : this.selectedLessonCertification.maxCertificationScore;

        this.service.UpdateTotalScore(lessonCertificationResult, this.selectedLessonCertification.lesson.id,
            this.semesterNumberYear, this.lessonCertificationCurrentOnly).subscribe(
                res => {
                    lessonCertificationResult.totalScore = res.data.totalScore
                    lessonCertificationResult.mark = res.data.mark
                }
            )
    }

    //Прямой экпорт в excel
    public excelExport() {

        let list = []
        let resultListForExcel: any = []
        
        this.lessonCertificationResultList.forEach(el => {
            resultListForExcel.push({
                "Студент": el.studentDTO.user.userFIO,
                "Текущий балл": el.currentScore,
                "Аттестационный балл": el.certificationScore,
                "Общий балл": el.totalScore,
                "Оценка": el.mark
            })
        })

        list[this.selectedLessonCertification.lesson.group.name + "_|_" + this.selectedLessonCertification.lesson.discipline.name] = resultListForExcel

        this.excelService.exportAsExcelFile(list, this.selectedLessonCertification.lesson.group.name + "_|_" + this.selectedLessonCertification.lesson.professor.user.userFIO + "_|_" + this.selectedLessonCertification.lesson.discipline.name)
    }

    public excelExport2() {
        let data = [];
        let ar = []

        this.lessonCertificationResultList.forEach(el => {
            this.service.getStudentCertification(this.selectedLessonCertification.lesson.id, el.studentDTO.id, this.lessonCertificationCurrentOnly).subscribe(
                res => {
                    let obg: any = {}
            
                    obg["Студент"] = el.studentDTO.user.userFIO
                    
                    for (let k in res.data){       
                        for(let i in res.data[k])                                                               
                           obg[res.data[k][i].event.comment] = res.data[k][i].value
                    }
                    ar.push(obg)
                }
            );
        })    
        let list = []
        
        list[this.selectedLessonCertification.lesson.group.name + "_|_" + this.selectedLessonCertification.lesson.discipline.name] = ar

        console.log(list);
        
        this.excelService.exportAsExcelFile(list, this.selectedLessonCertification.lesson.group.name + "_|_" + this.selectedLessonCertification.lesson.professor.user.userFIO + "_|_" + this.selectedLessonCertification.lesson.discipline.name)
               
    }
}