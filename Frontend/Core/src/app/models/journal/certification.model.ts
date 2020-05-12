import { Student } from './../account/student';
import { Lesson } from './../shedule/lesson';
export class Certification {
    public id: Number
    public startDate: String
    public endDate: String
    private lesson: any
    public certificationValueDTOList:CertificationValue[]

    constructor(startDate:String, endDate:String, certificationValue:CertificationValue[],lesson) {
        this.id = 0;
        this.startDate = startDate;
        this.certificationValueDTOList=certificationValue
        this.endDate = endDate;
        this.lesson = lesson
    }


}

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

export class LessonCertificationType{
    public id:number
    public name:String
}

export class LessonCertification{
    public id:Number;
    public lesson:Lesson;
    public lessonCertificationType:LessonCertificationType; 
    public maxCertificationScore:number;
    
    constructor(lesson:Lesson,lessonCertificationType:LessonCertificationType){
        this.lesson=lesson
        this.lessonCertificationType=lessonCertificationType
    }
}

export class LessonCertificationValue{
    public id:Number
    public absence:boolean
    public certificationScore:number
    public lessonCertificatonId:number
    public ratingDate:Date
    public studentDTO:Student
    public totalScore:number
}