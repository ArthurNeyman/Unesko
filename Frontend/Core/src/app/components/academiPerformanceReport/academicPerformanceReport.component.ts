import {Component, Injectable, OnInit} from '@angular/core';
import { JournalService } from '../../services/journal.service';


@Component({
    selector: "academicPerformanceReport-component",
    templateUrl: "./academicPerformanceReport.component.html",
    styleUrls: ["./academicPerformanceReport.component.css"]
})

@Injectable()
export class AcademicPerformanceReportComponent implements OnInit{
    
    constructor(private journalService:JournalService) {}
    ngOnInit(): void { 
        this.journalService.getAcademiPerformanceReport().subscribe();
    }
}