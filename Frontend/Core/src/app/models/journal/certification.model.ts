import { Student } from './../account/student';
import { Lesson } from './../shedule/lesson';

//Аттестация в журнале
export class Certification {
    public id: Number
    public startDate: String
    public endDate: String
    private lesson: any
    public currentCertificationValueDTOList:CertificationValue[]

    constructor(startDate:String, endDate:String, certificationValue:CertificationValue[],lesson) {
        this.id = 0;
        this.startDate = startDate;
        this.currentCertificationValueDTOList=certificationValue
        this.endDate = endDate;
        this.lesson = lesson
    }


}
//Конкретное значение аттестации в журнале
export class CertificationValue {
    id: Number
    certificationId: Number
    student: any
    certificationValue: Number
    missedAcademicHours: Number

    constructor(certification_id, student, certificationValue, missedAcademicHours) {
        this.id = 0
        this.certificationId = certification_id
        this.student = student
        this.certificationValue = certificationValue
        this.missedAcademicHours = missedAcademicHours
    }
}

//Тип итоговая аттестации
export class LessonCertificationType{
    public id:number
    public name:String
}
//Итоговая аттестация
export class LessonCertification{
    public id:Number;
    public lesson:Lesson;
    public lessonCertificationType:LessonCertificationType; 
    public maxCertificationScore:number;
    public events:IntermadiateCertificationEvent[]

    constructor(lesson:Lesson,lessonCertificationType:LessonCertificationType){
        this.lesson=lesson
        this.lessonCertificationType=lessonCertificationType
    }
}
//Конкретное значение итоговой аттестации
export class LessonCertificationValue{
    public id:Number
    public absence:boolean
    public certificationScore:number
    public lessonCertificatonId:number
    public ratingDate:Date
    public currentScore:number
    public studentDTO:Student
    public totalScore:number
    public mark : String
    public events: IntermadiateCertificationStudentEvent[]
}

export class IntermadiateCertificationEvent{
    name : String
    type : String
    maxValue : number
}

export class IntermadiateCertificationStudentEvent extends IntermadiateCertificationEvent{
   value:number
}

export class IntermadiateCertification{
    id:number
    events: IntermadiateCertificationEvent[]
    lesson : Lesson
    maxCertificationScore : number
    studList: IntermadiateCertificationStudentValue[]
    lessonCertificationType: LessonCertificationType
}

export class IntermadiateCertificationStudentValue{
    public id:Number
    public absence:boolean
    public certificationScore:number
    public lessonCertificatonId:number
    public ratingDate:Date
    public currentScore:number
    public studentDTO:Student
    public totalScore:number
    public mark : String
    public events: IntermadiateCertificationStudentEvent[]
}