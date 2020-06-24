import { LogInComponent } from './../../components.page/login/login.component';
import {Component, Injectable, OnInit,Input} from '@angular/core';
import { JournalService } from '../../services/journal.service';
import {SemesterNumberYear} from "../../models/semesterNumberYear.model";
import { ExcelService } from '../../services/excelService.service';
import {MessageService} from 'primeng/api';
import {User} from "../../models/account/user.model";
import { LessonCertificationService } from '../../services/lessonCertification.service';
import { fromPromise } from 'rxjs/internal-compatibility';

@Component({
    selector: "academicPerformanceReport-component",
    templateUrl: "./academicPerformanceReport.component.html",
    styleUrls: ["./academicPerformanceReport.component.css"]
})

@Injectable()
export class AcademicPerformanceReportComponent implements OnInit{

    public semesterNumberYear: SemesterNumberYear = new SemesterNumberYear();
    public data:any;
    private show=false;
    private selectAll: boolean=false;
    private selectedLessons:boolean[];
    private ckeck=false;
    public field:string='report-choose';
    cols: any[];
    public testData:any[]

    constructor( private service: LessonCertificationService,
        private messageService:MessageService,  
        private journalService:JournalService,
        private excelService:ExcelService) {}
    @Input() user:User;

    ngOnInit(): void { 
        this.testData=[]
        this.load();
        this.cols = [
            { field: 'student', header: 'Студент' },
            { field: 'visitation', header: 'Посещено занятий' },
            { field: 'value', header: 'Получено баллов' }
        ];
    }

    load(){
        this.show=false;
        this.journalService.getAcademiPerformanceReport(this.semesterNumberYear, this.user.id).subscribe(
            result=>{        
                this.data=result.data;
                this.show=true;
                this.selectedLessons=new Array(result.data.lessonList.length).fill(false)                
            },error => {
                
            }
            );
    }

    public exportAsXLSX(){    

       this.excelService.exportAsExcelFile(this.getLessonsForExcel(
        this.data.lessonList.filter((element)=>{
            return this.selectedLessons[this.data.lessonList.indexOf(element)]})))
    }

    public exportOneAsXLSX(lesson){

       this.excelService.exportAsExcelFile(this.getLessonsForExcel(lesson));
    }

    private getLessonsForExcel(lesson){
        let ar:any=[];
        let list=[];
        
        for(let k=0;k<lesson.length;k++){
            ar=[]
            for (let i=0;i<lesson[k].infoList.length;i++)
            {
                ar.push({
                    "Студент":lesson[k].infoList[i].student.user.userFIO,
                    "Посещено занятий": lesson[k].infoList[i].visitationValue,
                    "Пропущено занятий":lesson[k].maxVisitationValue-lesson[k].infoList[i].visitationValue,
                    "Получено баллов":lesson[k].infoList[i].eventValue,
                    "Макс. баллов":lesson[k].maxEventValue
                })
            }
            list[lesson[k].lessonDTO.group.name+'_'+lesson[k].lessonDTO.discipline.name]=ar
        }
        return list
    }

    private chooseAll(){
        this.selectedLessons.fill(this.selectAll);
    }

    private changeField(value){        
                
        for(let i in this.selectedLessons){
            if(this.selectedLessons[i])
                this.testData.push(this.data[i])
        }
              
        switch(value){
            case("create-online-report"):{
                if(this.selectedLessons.indexOf(true)==-1)
                    this.messageService.add({severity:'error', summary:'Ошибка', detail:'Не выбраны данные для отчетности'});
                else       
                    this.field=value;
                break;
            }
            case("report-choose"):{
                this.selectAll=false
                this.chooseAll()
                this.field=value;
                break;
            }
        }
    }
}

