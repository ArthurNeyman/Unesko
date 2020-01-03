import {Component, Injectable, OnInit} from '@angular/core';
import { JournalService } from '../../services/journal.service';
import {SemesterNumberYear} from "../../models/semesterNumberYear.model";
import {Lesson} from "../../models/shedule/lesson";
import { ExcelService } from '../../services/excelService.service';


export interface ReportLesson{
    allevemntValue:number;
    allHours:number;
    lesson:Lesson;
    studentCertification:any;
}

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

    constructor(private journalService:JournalService,private excelService:ExcelService) {}
    ngOnInit(): void { 
        this.load();
    }

    load(){
        this.show=false;
        this.journalService.getAcademiPerformanceReport(this.semesterNumberYear).subscribe(
            result=>{
                this.data=result.data;
                this.show=true;
            },error => {
                
            }
            );
    }

    public exportAsXLSX(lesson){
        this.excelService.exportAsExcelFile(this.getLessonsForExcel(lesson));
    }

    public getLessonsForExcel(lesson){
        let ar:any=[];
        let list=[];
        for(let k=0;k<lesson.length;k++){
            ar=[]
            for (let i=0;i<lesson[k].studentCertification.length;i++)
            {
                ar.push({
                    "Студент":lesson[k].studentCertification[i].student.user.userFIO,
                    "Пропущено академических часов":lesson[k].studentCertification[i].missingHours,
                    "Аттестационный балл":lesson[k].studentCertification[i].certificationValue
                })
            }
            list[lesson[k].lesson.group.name+'_'+lesson[k].lesson.discipline.name]=ar
        }
        return list
    }
}

