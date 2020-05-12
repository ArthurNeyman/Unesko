import { Lesson } from './../../../models/shedule/lesson';
import { LessonCertificationType, LessonCertification } from './../../../models/journal/certification.model';

import {Component, OnInit} from "@angular/core";
import { SemesterNumberYear } from "../../../models/semesterNumberYear.model";
import { Professor } from "../../../models/account/professor";
import { DictionaryService } from "../../../services/dictionary.service";
import { LazyLoadEvent } from "primeng/api";
import { Dictionary } from "../../../models/admin/dictionary.model";
import { isUndefined } from "util";
import { JournalService } from "../../../services/journal.service";
import { LessonCertificationService } from "../../../services/lessonCertification.service";

@Component({
    selector: 'lesson-certification-settings',
    templateUrl: "./lessonCertificationSettings.component.html",
    styleUrls: ["./lessonCertificationSettings.component.css"]
})

export class LessonCertificationSettingsComponent implements OnInit {
   
    public semesterNumberYear: SemesterNumberYear = new SemesterNumberYear();
    public currentProfessor: Professor=null;
    public profs: Array<Professor> = [];
    public lastprofsFilter: LazyLoadEvent = {globalFilter: "&7&"};
   
    public lessonList:Lesson[];
    public typeList:LessonCertificationType[];
    public lessonCertificationList:LessonCertification[];

    public show:boolean=false;
    
    constructor(
        private dictionaryService: DictionaryService,
        private journalService:JournalService,
        private service:LessonCertificationService
) {
}
    ngOnInit() {
        this.getLessonCertificationtypes()
    }

    public GetProfessors(event: any) {
        let query = event.query ? event.query : '';
        if (this.profs.length == 0 || this.lastprofsFilter.globalFilter != query.substring(0, 60)) {
            if (!isUndefined(query))
                this.lastprofsFilter = {globalFilter: query.substring(0, 60)};

            this.dictionaryService.Get(Dictionary.professors, this.lastprofsFilter).subscribe(
                result => {
                    if (result.content.length > 0)
                        this.profs = result.content;
                }
            );
        } else {
            this.profs = JSON.parse(JSON.stringify(this.profs));
        }
    }

    public getProfessorLessons(event:any){
        if (this.currentProfessor == null || this.currentProfessor.id == 0 || !this.semesterNumberYear)
            return;
            this.service.getLessonCertificationList(this.semesterNumberYear, this.currentProfessor.id.toString()).subscribe(
            result=>{
                this.lessonCertificationList=result.data
                this.show=true                 
            },error => {
                
            })
    }

    public getLessonCertificationtypes(){
        this.service.getLessonCertificationtypes().subscribe(
            result=>{
                this.typeList=result.data;
            },error=>{

            }
        )
    }

    setLessonCertificationtype(lessonCertification : LessonCertification){
       this.service.setLessonCertificationtype(lessonCertification).subscribe(
           result=>{
                lessonCertification.id=result.data.id
           }
       );
    }
    
}