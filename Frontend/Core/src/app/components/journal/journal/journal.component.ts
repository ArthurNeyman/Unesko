import {Component, Injectable, Input, OnInit} from '@angular/core';
import {Lesson} from "../../../models/shedule/lesson";
import {SemesterNumberYear} from "../../../models/semesterNumberYear.model";
import {MenuItem} from 'primeng/api';

@Component({
    selector: 'journal',
    templateUrl: './journal.component.html',
    styleUrls: ['./journal.component.css']
})

@Injectable()
export class JournalComponent implements OnInit {

    @Input() lesson: Lesson;
    @Input() subgroupType: number = 0;
    @Input() semesterNumberYear: SemesterNumberYear;
    
    public items: MenuItem[];
    activeItem: MenuItem;

    ngOnInit(): void {
    
        this.items = [{
            label: 'Журнал',
            command: (event) => {
                this.activeItem=this.items[0];
            }        
        },{
            label: 'Успеваемость',
            command: (event) => {
                this.activeItem=this.items[1];
            }
        },{
            label: 'Текущая аттестация',
            command: (event) => {
                this.activeItem=this.items[2];
            }
        }]

        this.activeItem = this.items[0];
        
    }

}