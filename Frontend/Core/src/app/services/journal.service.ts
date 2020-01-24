import { Group } from './../models/shedule/group';
import { Injectable } from "@angular/core";
import { HttpClient, HttpParams } from "@angular/common/http";
import { Observable } from "rxjs/Observable";
import { Router } from "@angular/router";
import { ApiRouteConstants } from "../bootstrap/app.route.constants";
import "rxjs/add/operator/catch";
import { HandelErrorService } from "./handelError.service";
import { ResponseStatus } from "../models/additional/responseStatus";
import { LessonEvent } from "../models/journal/lessonEvent.model";
import { VisitationConfig } from "../models/journal/visitationConfig.model";
import { catchError, map } from "rxjs/operators";
import { SemesterNumberYear } from "../models/semesterNumberYear.model";

@Injectable()
export class JournalService {

    constructor(
        private http: HttpClient,
        private router: Router,
        private handleError: HandelErrorService
    ) {
    }

    public GetStudentPerformance(userID): Observable<ResponseStatus> {
        let params = new HttpParams().set("userId", userID.toString());;
        return this.http
            .post("performance/points", params)
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public GetAll(): Observable<ResponseStatus> {
        return this.http.get(ApiRouteConstants.Journal.All)
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public GetJournal(lessonId, month, semesterNumberYear: SemesterNumberYear, forDate?): Observable<ResponseStatus> {
        let params = new HttpParams();
        params = params.set("month", month);
        params = params.set("forDate", !forDate ? '' : forDate);
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());

        return this.http.get(ApiRouteConstants.Journal.All
            .replace(":lessonId", lessonId), { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    //journal-sertification service
    public GetJournalCertificationReport(lessonId, start, end, semesterNumberYear: SemesterNumberYear): Observable<ResponseStatus> {
        let params = new HttpParams();
        params = params.set("start", start);
        params = params.set("end", end);
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());

        return this.http.get(ApiRouteConstants.Journal.СertificationReport
            .replace(":lessonId", lessonId), { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public GetJournalDates(lessonId, semesterNumberYear: SemesterNumberYear): Observable<ResponseStatus> {
        let params = new HttpParams();
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());

        return this.http.get(ApiRouteConstants.Journal.Dates
            .replace(":lessonId", lessonId), { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public GetJournalHistoryDate(lessonId, semesterNumberYear: SemesterNumberYear): Observable<ResponseStatus> {
        let params = new HttpParams();
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());

        return this.http.get(ApiRouteConstants.Journal.HistoryDates
            .replace(":lessonId", lessonId), { params: params })
            .pipe(
                map((res: ResponseStatus) => {
                    for (let i = 0; i < res.data.length; i++) {
                        res.data[i] = new Date(res.data[i]);
                    }
                    return res;
                }),
                catchError(e => this.handleError.handle(e))
            );
    }

    public GetEvents(lessonId): Observable<ResponseStatus> {
        return this.http.get(ApiRouteConstants.Journal.Events
            .replace(":lessonId", lessonId))
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public Save(journal): Observable<ResponseStatus> {
        let params = new HttpParams();
        return this.http.post(ApiRouteConstants.Journal.Save, journal, { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public SaveEvent(event: LessonEvent): Observable<ResponseStatus> {
        let params = new HttpParams();
        return this.http.post(ApiRouteConstants.Journal.EventSave, event, { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public SaveVisitation(config: VisitationConfig): Observable<ResponseStatus> {
        let params = new HttpParams();
        return this.http.post(ApiRouteConstants.Journal.VisitationConfigSave, config, { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public GetVisitation(lessonId): Observable<ResponseStatus> {
        return this.http.get(ApiRouteConstants.Journal.VisitationConfigGet
            .replace(":lessonId", lessonId))
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public EventDelete(id: number): Observable<ResponseStatus> {
        return this.http.get(ApiRouteConstants.Journal.EventDelete
            .replace(":id", id.toString()))
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }

    public getAcademiPerformanceReport(semesterNumberYear: SemesterNumberYear, professorId: string) {
        let params = new HttpParams();
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());

        return this.http.get(ApiRouteConstants.Report.ReportAcademicPerfomance
            .replace(":professorId", professorId), { params: params })
            .pipe(
                map((res: ResponseStatus) => res),
                catchError(e => this.handleError.handle(e))
            );
    }
    //-------------------------------------------------------------------------------------------------------
    //Получить список аттестаций для группы по учебному периоду
    public getCertificationList(lessnId: Number, group: Group, semesterNumberYear: SemesterNumberYear) {
        let params = new HttpParams();
        params = params.set("semester", semesterNumberYear.semester.toString());
        params = params.set("year", semesterNumberYear.year.toString());
    }
    //Сохранить сформированную аттестацию
    public saveCertification(certification) {

    }
    //Удалить аттестацию
    public deleteCertification(certification) {

    }

}

export class Certification {
    private id: Number
    private group_id: Number
    private startDate: String
    private endDate: String
    private semester: Number
    private year: Number
    private certificationValue: CertificationValue[]
    private lessonId: Number
    constructor(group_id, startDate, endDate, semester, year, certificationValue, lessonId) {
        this.id = 0;
        this.group_id = group_id;
        this.startDate = startDate;
        this.endDate = endDate;
        this.semester = semester;
        this.year = year;
        this.certificationValue = certificationValue
        this.lessonId = lessonId
    }
}

export class CertificationValue {
    id: Number
    certification_id: Number
    studentId: Number
    certificationValue: Number
    missedAcademicHours: Number

    constructor(certification_id, studentId, certificationValue, missedAcademicHours) {
        this.id = 0
        this.certification_id = certification_id
        this.studentId = studentId
        this.certificationValue = certificationValue
        this.missedAcademicHours = missedAcademicHours
    }
}