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
        this.journalService.getAcademiPerformanceReport(this.semesterNumberYear, this.user.id.toString()).subscribe(
            result=>{
                this.data=result.data;
                this.show=true;
                this.selectedLessons=new Array(result.data.lessonList.length).fill(false)                
            },error => {
                
            }
            );
    }

    public exportAsXLSX(){    

        // this.excelService.exportAsExcelFile(this.getLessonsForExcelTest(this.testData))

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
            for (let i=0;i<lesson[k].studentCertification.length;i++)
            {
                ar.push({
                    "Студент":lesson[k].studentCertification[i].student.user.userFIO,
                    "Посещено занятий": lesson[k].studentCertification[i].visitationValue/2,
                    "Макс. занятий":(lesson[k].studentCertification[i].missingHours+lesson[k].studentCertification[i].visitationValue)/2,
                    "Получено баллов":this.getSumValue(lesson[k].studentCertification[i]),
                    "Макс. баллов":lesson[k].allEventValue
                })
            }
            list[lesson[k].lesson.group.name+'_'+lesson[k].lesson.discipline.name]=ar
        }
        return list
    }

    private chooseAll(){
        this.selectedLessons.fill(this.selectAll);
    }

    private changeField(value){        
                
         this.selectedLessons.filter(el=>el).forEach(el=>{
            this.service.getStudResults(this.data.lessonList[this.selectedLessons.indexOf(el)].lesson).subscribe(
                res=>{
                    this.testData.push(res.data);
                }
            )
        })
       
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

    private getSumValue(cert){
        let sum=0;
        for(let i=0;i<cert.eventValue.length;i++)
            sum+=Number(cert.eventValue[i].value)
        return sum;
    }

    // private getLessonsForExcelTest(lesson){
    //     let ar:any=[];
    //     let list=[];
    //     let obj:any=[];
    //     let names:any

    //     names={ "Студент":[]}

    //     for(let k=0;k<lesson.length;k++){
    //         ar=[]
    //         let array=lesson[k]
    //         for(let i=0;i<array.length;i++){
                
    //             names={ ...names,
    //                [array[i].lessonEventDTO.comment] : [] 
    //             }
    //         }
    //         }
    //         list["test"]=ar        
    //         console.log(names);
            
    //     return list
    // }
}

