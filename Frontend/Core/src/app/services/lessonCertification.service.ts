import { Lesson } from './../models/shedule/lesson';
import { SemesterNumberYear } from './../models/semesterNumberYear.model';
import { LessonCertificationValue } from './../models/journal/certification.model';
import { HttpClient, HttpParams } from '@angular/common/http';

import { Injectable } from "@angular/core";
import { ApiRouteConstants } from '../bootstrap/app.route.constants';
import { ResponseStatus } from '../models/additional/responseStatus';
import { catchError, map } from 'rxjs/operators';
import { LessonCertification } from '../models/journal/certification.model';
import { Observable } from "rxjs/Observable";
import { HandelErrorService } from "./handelError.service";

@Injectable()
export class LessonCertificationService {

    constructor(
        private http: HttpClient,
        private handleError: HandelErrorService
    ) { }

    public getLessonCertificationtypes(): Observable<ResponseStatus> {
        return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.getLessonCertificationTypes).pipe(
            map((res: ResponseStatus) => res)
        );
    }

    public setLessonCertificationtype(lessonCertification: LessonCertification): Observable<ResponseStatus> {
        return this.http.post(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.setLessonCertificationType, lessonCertification, { params: new HttpParams() }).pipe(
            map((res: ResponseStatus) => res)
        );
    }

    public getLessonCertificationList(semesterNumberYear: SemesterNumberYear, professorId: string): Observable<ResponseStatus> {
        let params = new HttpParams();
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());
        return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.getLessonCertificationList
            .replace(":professorId", professorId), { params: params })
            .pipe(
                map((res: ResponseStatus) => res)
            );
    }

    public setMaxScore(lessonCertification: LessonCertification): Observable<ResponseStatus> {
        return this.http.post(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.setMaxCertificationScore, lessonCertification, { params: new HttpParams() }).pipe(
            map((res: ResponseStatus) => res)
        );
    }

    public getLessonCertificationResult(lessonCertification: LessonCertification, semesterNumberYear: SemesterNumberYear, curentOnly: boolean): Observable<ResponseStatus> {

        let params = new HttpParams();

        params = params.set("currentOnly", curentOnly ? "1" : "0")

        return this.http.post(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.getLessonCertificationResult, lessonCertification, { params: params })
            .pipe(
                map((res: ResponseStatus) => {
                    for (let i = 0; i < res.data.length; i++) {
                        res.data[i].ratingDate = (res.data[i].ratingDate === null ? null : new Date(res.data[i].ratingDate));
                    }
                    return res;
                }));
    }

    public getLessonCertification(lessonId): Observable<ResponseStatus> {
        return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.getLessonCertification
            .replace(":lessonId", lessonId), { params: new HttpParams() })
            .pipe(
                map((res: ResponseStatus) => res)
            );
    }

    public getLessonEvents(lessonId, semesterNumberYear: SemesterNumberYear): Observable<ResponseStatus> {
        let params = new HttpParams();
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());

        return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.getLessonEvents
            .replace(":lessonId", lessonId), { params: params })
            .pipe(
                map((res: ResponseStatus) => res)
            );
    }

    public saveLessonCertificationResult(lessonCertificationresultList: LessonCertificationValue[]): Observable<ResponseStatus> {
        return this.http.post(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.saveLessonCertificationResult, lessonCertificationresultList, { params: new HttpParams() })
            .pipe(
                map((res: ResponseStatus) => {
                    for (let i = 0; i < res.data.length; i++) {
                        res.data[i].ratingDate = (res.data[i].ratingDate === null ? null : new Date(res.data[i].ratingDate));
                    }
                    return res;
                }));
    }

    public GetLessonListWithCertificationForStudent(userID): Observable<ResponseStatus> {
        let params = new HttpParams().set("userId", userID.toString());
        return this.http.post(ApiRouteConstants.LessonCertification.getLessonListWithCertification, params)
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public getStudentCertification(lessonId: number, studentId: number, curentOnly: boolean): Observable<ResponseStatus> {
        let params = new HttpParams();
        params = params.set("lessonId", lessonId.toString());
        params = params.set("student_id", studentId.toString());

        return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.getCertificationForStudent, { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public UpdateTotalScore(lessomCertificationresultDTO, lessonId, semesterNumberYear: SemesterNumberYear, curentOnly: boolean): Observable<ResponseStatus> {
        let params = new HttpParams();
        params = params.set("lesson_id", lessonId.toString());
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());
        params = params.set("currentOnly", curentOnly ? "1" : "0")

        return this.http.post(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.updateTotalScor, lessomCertificationresultDTO, { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    //-------------------------------------------------------------------------------------------------
    public getStudResults(lesson: Lesson) {
        return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.LessonCertification.getStudentResults
            .replace(":lessonId", lesson.id.toString()), { params: new HttpParams() })
            .pipe(
                map((res: ResponseStatus) => res)
            );
    }
}