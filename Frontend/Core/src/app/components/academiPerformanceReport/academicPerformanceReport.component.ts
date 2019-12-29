import {Component, Injectable, OnInit} from '@angular/core';
import { JournalService } from '../../services/journal.service';
import {SemesterNumberYear} from "../../models/semesterNumberYear.model";
import {Lesson} from "../../models/shedule/lesson";


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

    constructor(private journalService:JournalService) {}
    ngOnInit(): void { 
        this.load();
    }

    load(){
        this.show=false;
      this.journalService.getAcademiPerformanceReport(this.semesterNumberYear).subscribe(
        result=>{
            this.data=result.data;
            this.show=true;
            console.log(result);
        },error => {
            
        }
        );
    }

    onClick(lesson: Lesson){
        console.log(lesson);
    }

    public getLessons(){
        let list=[]
        for(let i=0;i<this.data.lessonList.length;i++)
            list.push(this.data.lessonList[i].lesson)
        return list
    }
}

