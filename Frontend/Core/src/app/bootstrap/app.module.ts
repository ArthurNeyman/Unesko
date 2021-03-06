﻿import { LessonCertificationService } from './../services/lessonCertification.service';
import {CommonModule, registerLocaleData} from "@angular/common";
import {LOCALE_ID, NgModule} from "@angular/core";
import {UrlSerializer} from "@angular/router";
import {BrowserModule} from "@angular/platform-browser";
import {BrowserAnimationsModule} from "@angular/platform-browser/animations";
import {HttpModule, RequestOptions} from "@angular/http";
import {FormsModule} from "@angular/forms";
import {TranslationModule, TranslationService} from "angular-l10n";
import localeRu from '@angular/common/locales/ru';
// custom
import {routing} from "./app.routes";
import {LowerCaseUrlSerializer} from "../providers/router";
import {EnumKeysPipe} from "../pipes/enum.keys";
import {Globals} from "../globals";
import {ProfileDirective} from "../directive/profile.dirictive";
import {EnumStringKeysPipe} from "../pipes/enum.string.keys";
import {FileUploadModule as ngFileUploadModule} from "ng2-file-upload";
import {SpinnerModule} from 'primeng/spinner';

import {
    CheckboxModule,
    EditorModule, 
    FileUploadModule, 
    InplaceModule,
    InputTextareaModule,
    RadioButtonModule,
    TabViewModule,
    TooltipModule
} from "primeng/primeng";
import {ConfirmDialogModule} from 'primeng/primeng';
import {InputTextModule} from 'primeng/primeng';
import {DialogModule} from 'primeng/primeng';
import {GrowlModule} from 'primeng/primeng';
import {AutoCompleteModule} from 'primeng/primeng';
import {CalendarModule} from 'primeng/primeng';
import {DataTableModule} from 'primeng/primeng';
import {DragDropModule} from 'primeng/primeng';
import {DropdownModule} from 'primeng/primeng';
import {InputSwitchModule} from 'primeng/primeng';
import {PasswordModule} from 'primeng/primeng';
import {SelectButtonModule} from 'primeng/primeng';
import {ToggleButtonModule} from 'primeng/primeng';
import {ConfirmationService} from 'primeng/primeng';
import {MessageService} from 'primeng/primeng';
import {TabMenuModule} from 'primeng/primeng';
import {CardModule} from 'primeng/primeng';
import {PanelModule} from 'primeng/primeng';
import {AccordionModule} from 'primeng/primeng';
import {ListboxModule} from 'primeng/primeng';

import {ToastModule} from 'primeng/components/toast/toast';
import {TableModule} from "primeng/components/table/table";
// http
import {HTTP_INTERCEPTORS, HttpClientModule} from "@angular/common/http";
import {GlobalHttpOptions} from "../http/globalHttpOptions";
import {ServiceHttpInterceptor} from "../http/serviceHttpInterceptor";
// services
import {NewsService} from "../services/news.service";
import {TaskService} from "../services/task.service";
import {AuthenticationService} from "../services/auth.service";
import {ScheduleService} from "../services/schedule.service";
import {PluginService} from "../services/plugin.service";
import {AccountService} from "../services/account.service";
import {DictionaryService} from "../services/dictionary.service";
import {JournalService} from "../services/journal.service";
import {UtilsService} from "../services/utils.service";
import {NotificationService} from "../services/notification.service";
import {HandelErrorService} from "../services/handelError.service";
import {FileService} from "../services/file.service";
import {ExcelService } from '../services/excelService.service';
// guards
import {AuthGuard} from "../guards/auth.guard";
// components
import {CertificationStudentComponent} from "../components/studentInterface/certificationStudent/certificationStudent.component";
import {ArchivePointsComponent} from "../components/studentInterface/archivePoints/archivePoints.component";
import {StudentProgressComponent} from "../components/studentInterface/studentProgress/studentProgress.component";
import {AppComponent} from "./app.component";
import {SingleNewsComponent} from "../components/news/single-news/single-news.component";
import {EditorSingleNewsComponent} from "../components/news/editor-single-news/editor-single-news.component";
import {EditorListNewsComponent} from "../components/news/editor-list-news/editor-list-news.component";
import {ListNewsComponent} from "../components/news/list-news/list-news.component";
import {NotFoundComponent} from "../components/notfound/notfound.component";
import {TaskDescListComponent} from "../components/task/taskList/taskDescList.component";
import {HeaderComponent} from "../components/shared/header/header";
import {AccessDeniedComponent} from "../components/shared/accessDenied/accessDenied.component";
import {LoaderComponent} from "../components/shared/loader/loader";
import {WeekScheduleComponent} from "../components/schedule/weekSchedule/weekSchedule.component";
import {DepartmentScheduleComponent} from "../components/schedule/departmentSchedule/departmentShedule.component";
import {PairDetailsComponent} from "../components/schedule/pairDetails/pairDeatails.component";
import {NewTaskDescComponent} from "../components/task/newTask/newTaskDesc.component";
import {WorkTaskComponent} from "../components/task/workTask/workTask.component";
import {ShowScheduleComponent} from "../components/schedule/showSchedule/show-schedule.component";
import {JournalComponent} from "../components/journal/journal/journal.component";
import {DictionaryTableAddComponent} from "../components.page/adminPanel/dictionaryTable/added/dictionaryTableAdd.component";
import {ParserXmlComponent} from "../components.page/adminPanel/parserXml/parserXml.component";
import {DictionaryComponent} from "../components.page/adminPanel/dictionaryTable/dictionaryTable.component";
import {AdminPanelComponent} from "../components.page/adminPanel/adminPanel";
import {SettingsPageComponent} from "../components.page/account/settings-page/settings-page.component";
import {LessonConfiguratorPageComponent} from "../components.page/account/lesson-configurator-page/lesson-configurator-page.component";
import {JournalPageComponent} from "../components.page/account/journal-page/journal-page.component";
import {AccountComponent} from "../components.page/account/account.component";
import {NewsDispatcherComponent} from "../components.page/account/news-dispatcher/news-dispatcher.component";
import {LogInComponent} from "../components.page/login/login.component";
import {UserAddComponent} from "../components.page/adminPanel/userAdd/userAdd";
import {LessonListComponent} from "../components/schedule/lessonsList/lessonsList.component";
import {ProfileComponent} from "../components/shared/profile/profile";
import {LessonDetailsComponent} from "../components/schedule/lesson-details/lessonDetails.component";
import {AccessControlComponent} from "../components.page/adminPanel/accessControl/accessControl.component";
import {DictionaryTableComponent} from "../components/shared/dictionaryTable/dictionaryTable.component";
import {HasAccessRightDirective} from "../directive/hasAccessRight.dirictive";
import {HasRoleDirective} from "../directive/hasRole.dirictive";
import {UserSearchComponent} from "../components/shared/userSearch/userSearch";
import {ScheduleComponent} from "../components.page/schedule/schedule.component";
import {DetailTaskComponent} from "../components/task/detailTask/detailTask.component";
import {StudentsConfiguratorComponent} from "../components.page/adminPanel/studentsConfigurator/studentsConfigurator";
import {AcademicPerformanceComponent} from "../components/journal/academic-performance/academic-performance.component";
import {MoodleService} from "../services/moodle.service";
import {LessonConfiguratorComponent} from "../components/journal/lesson-configurator/lesson-configurator.component";
import {EducationPeriodComponent} from "../components.page/adminPanel/educationPeriod/educationPeriod.component";
import {SpecialityListComponent} from "../components/schedule/specialityList/specialityList.component";
import {SpecialityDetailsComponent} from "../components/schedule/specialityDetails/specialityDetails.component";
import {SemesterPickerComponent} from "../components/shared/semesterPicker/semesterPicker.component";

import {JournalFillComponent} from "../components/journal/journal-fill/journal-fill.component";
import {JournalCertificationComponent} from "../components/journal/journal-certification/journal-certification.component";

import {AcademicPerformanceReportComponent} from "../components/academiPerformanceReport/academicPerformanceReport.component";

import {LessonCertificationSettingsComponent} from "../components.page/adminPanel/lessonCertificationSettings/lessonCertificationSettings.component";
import {LessonCertificationComponent, ListenInputCertification} from "../components/lessonCertification/lessonCertification.component"
import {TeacherProfileComponent } from '../components/StudentInterface/teacherProfile/teacherProfile.component';
import {OverlayPanelModule} from 'primeng/overlaypanel';

import {MonitoringStudentsProgress} from "../services/monitoringStudentsProgress.service"

registerLocaleData(localeRu);

@NgModule({
    imports: [
        OverlayPanelModule,
        CommonModule,
        BrowserAnimationsModule,
        BrowserModule,
        FormsModule,
        HttpModule,
        HttpClientModule,
        routing,
        TranslationModule.forRoot(),
        DialogModule,
        GrowlModule,
        DataTableModule,
        SelectButtonModule,
        DragDropModule,
        InputSwitchModule,
        CheckboxModule,
        CalendarModule,
        DropdownModule,
        InputTextModule,
        PasswordModule,
        AutoCompleteModule,
        ConfirmDialogModule,
        ToggleButtonModule,
        EditorModule,
        InplaceModule,
        RadioButtonModule,
        TableModule,
        TabViewModule,
        TooltipModule,
        InputTextareaModule,
        FileUploadModule,
        ngFileUploadModule,
        TabMenuModule,
        CardModule,
        ToastModule,
        PanelModule,
        AccordionModule,
        ListboxModule,
        SpinnerModule
    ]
        ,
    entryComponents: [
        ProfileComponent
    ],
    declarations: [
        CertificationStudentComponent,              // Просмотр аттестации для студента
        TeacherProfileComponent,                    // Профиль преподавателя
        ArchivePointsComponent,                     // Архив отметок
        StudentProgressComponent,                   // Компонент просмотров баллов
        AppComponent,
        AccountComponent,
        NewsDispatcherComponent,
        TaskDescListComponent,
        StudentsConfiguratorComponent,
        NewTaskDescComponent,
        WorkTaskComponent,
        ProfileComponent,
        LogInComponent,
        JournalPageComponent,
        AcademicPerformanceComponent,
        LessonConfiguratorPageComponent,
        SingleNewsComponent,
        ListNewsComponent,
        SettingsPageComponent,
        UserAddComponent,
        HeaderComponent,
        EditorSingleNewsComponent,
        EditorListNewsComponent,
        AdminPanelComponent,
        NotFoundComponent,
        AccessDeniedComponent,
        EnumKeysPipe,
        EnumStringKeysPipe,
        ProfileDirective,
        DictionaryComponent,
        ParserXmlComponent,
        WeekScheduleComponent,
        DictionaryTableAddComponent,
        LoaderComponent,
        JournalComponent,
        LessonConfiguratorComponent,
        DictionaryTableComponent,
        AccessControlComponent,
        DepartmentScheduleComponent,
        HasAccessRightDirective,
        HasRoleDirective,
        ShowScheduleComponent,
        PairDetailsComponent,
        LessonListComponent,
        LessonDetailsComponent,
        ScheduleComponent,
        UserSearchComponent,
        EducationPeriodComponent,
        SpecialityListComponent,
        SpecialityDetailsComponent,
        DetailTaskComponent,
        SemesterPickerComponent,
        JournalFillComponent,
        JournalCertificationComponent,
        AcademicPerformanceReportComponent,
        LessonCertificationSettingsComponent,
        LessonCertificationComponent,
        ListenInputCertification
        ],
    providers: [
        {provide: RequestOptions, useClass: GlobalHttpOptions},
        {provide: UrlSerializer, useClass: LowerCaseUrlSerializer},
        {provide: HTTP_INTERCEPTORS, useClass: ServiceHttpInterceptor, multi: true},
        { provide: LOCALE_ID, useValue: 'ru-RU' },
        TranslationService,
        AuthenticationService,
        NotificationService,
        HandelErrorService,
        TaskService,
        MoodleService,
        NewsService,
        MessageService,
        AccountService,
        UtilsService,
        DictionaryService,
        ConfirmationService,
        JournalService,
        AuthGuard,
        Globals,
        ScheduleService,
        PluginService,
        FileService,
        ExcelService,
        LessonCertificationService,
        MonitoringStudentsProgress
    ],
    bootstrap: [
        AppComponent
    ]
})

export class AppModule {
}