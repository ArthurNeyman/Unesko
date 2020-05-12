import { LessonCertificationType, LessonCertificationValue } from './../models/journal/certification.model';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Injectable } from "@angular/core";
import { ApiRouteConstants } from '../bootstrap/app.route.constants';
import { ResponseStatus } from '../models/additional/responseStatus';
import { map } from 'rxjs/operators';
import { LessonCertification } from '../models/journal/certification.model';
import { SemesterNumberYear } from '../models/semesterNumberYear.model';

@Injectable()
export class LessonCertificationService{

    constructor(
        private http: HttpClient,
    ) {
    }

    public getLessonCertificationtypes(){
        return this.http.get(ApiRouteConstants.LessonCertification.getLessonCertificationTypes).pipe(
            map((res: ResponseStatus) => res)
        );
    }

    public setLessonCertificationtype(lessonCertification:LessonCertification){
        let params = new HttpParams();        
        return this.http.post(ApiRouteConstants.LessonCertification.setLessonCertificationType,lessonCertification,{ params: params }).pipe(
            map((res: ResponseStatus) => res)
        );
    }

    public getLessonCertificationList(semesterNumberYear: SemesterNumberYear, professorId: string){
        let params = new HttpParams();
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());
        return this.http.get(ApiRouteConstants.LessonCertification.getLessonCertificationList
            .replace(":professorId", professorId), { params: params })
            .pipe(
                map((res: ResponseStatus) => res)
            );
    }

    public setMaxScore(lessonCertification:LessonCertification){      
        return this.http.post(ApiRouteConstants.LessonCertification.setMaxScore,lessonCertification,{params:new HttpParams()}).pipe(
            map((res: ResponseStatus) => res)
        );
    }

    public getLessonCertificationResult(lessonCertification:LessonCertification){
        return this.http.post(ApiRouteConstants.LessonCertification.getLessonCertificationResult,lessonCertification,{params:new HttpParams()})
            .pipe(
            map((res: ResponseStatus) => {             
                    for (let i = 0; i < res.data.length; i++) {
                        res.data[i].ratingDate = (res.data[i].ratingDate===null ? null : new Date(res.data[i].ratingDate)) ;                
                    }
                    return res;
            }));
    }

    public getLessonCertification(lessonId){
        return this.http.get(ApiRouteConstants.LessonCertification.getLessonCertification
        .replace(":lessonId", lessonId), { params: new HttpParams() })
        .pipe(
            map((res: ResponseStatus) => res)
        );
    }
    public getLessonEvents(lessonId){
        return this.http.get(ApiRouteConstants.LessonCertification.getLessonEvents
        .replace(":lessonId", lessonId), { params: new HttpParams() })
        .pipe(
            map((res: ResponseStatus) => res)
        );
    }

    public saveLessonCertificationresult(lessonCertificationresultList:LessonCertificationValue){
        return this.http.post(ApiRouteConstants.LessonCertification.saveLessonCertificationResult,lessonCertificationresultList,{params:new HttpParams()})
        .pipe(
            map((res: ResponseStatus) => {                
            for (let i = 0; i < res.data.length; i++) {
                res.data[i].ratingDate = (res.data[i].ratingDate===null ? null : new Date(res.data[i].ratingDate)) ;                
            }
            return res;
        }));    }
}