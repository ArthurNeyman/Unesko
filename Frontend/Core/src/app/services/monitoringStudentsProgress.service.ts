
import { Injectable } from "@angular/core";
import { HttpParams, HttpClient } from "@angular/common/http";
import { SemesterNumberYear } from "../models/semesterNumberYear.model";
import { ApiRouteConstants } from "../bootstrap/app.route.constants";
import { HandelErrorService } from "./handelError.service";
import { ResponseStatus } from "../models/additional/responseStatus";
import { map, catchError } from "rxjs/operators";
import { Observable } from "rxjs";


@Injectable()
export class MonitoringStudentsProgress {

    constructor(
        private http: HttpClient,
        private handleError: HandelErrorService
    ) {
    }
    
  //Получить отчет по профессору
  public getAcademiPerformanceReport(semesterNumberYear: SemesterNumberYear, professorId: string) {
    let params = new HttpParams();
    params = params.set("semester", semesterNumberYear.semester.toString());
    params = params.set("year", semesterNumberYear.year.toString());

    return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.ReportOnProgress.get
        .replace(":professorId", professorId), { params: params })
        .pipe(
            map((res: ResponseStatus) => res),
            catchError(e => this.handleError.handle(e))
        );
}
//-------------------------------------------------------------------------------------------------------
//Получить список аттестаций по предмету
public getCertificationList(lessonId: Number) {
    return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.Certification.get
        .replace(":lessonId",lessonId.toString()))
        .pipe(
            map((res: ResponseStatus) => res),
            catchError(e => this.handleError.handle(e))
        );

}
//Сохранить сформированную аттестацию
public saveCertification(certification):Observable<ResponseStatus> {
    let params = new HttpParams();
    return this.http.post(ApiRouteConstants.MonitoringStudentsProgress.Certification.save,certification,{ params: params })
        .pipe(
            map((res: ResponseStatus) => res),
            catchError(e => this.handleError.handle(e))
        );
}
//Удалить аттестацию
public deleteCertification(certification) {
    let params = new HttpParams();
    return this.http.post(ApiRouteConstants.MonitoringStudentsProgress.Certification.delete,certification,{ params: params })
    .pipe(
        map((res: ResponseStatus) => res),
        catchError(e => this.handleError.handle(e))
    );
}
public GetJournalCertificationReport(lessonId, start, end): Observable<ResponseStatus> {
    let params = new HttpParams();
    params = params.set("start", start);
    params = params.set("end", end);

    return this.http.get(ApiRouteConstants.MonitoringStudentsProgress.Certification.certification
        .replace(":lessonId", lessonId), { params: params })
        .pipe(
            map((res: ResponseStatus) => res),
            catchError(e => this.handleError.handle(e))
        );
}
}