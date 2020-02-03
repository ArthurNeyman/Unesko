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