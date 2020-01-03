import { Injectable } from '@angular/core';
import * as FileSaver from 'file-saver';
import * as XLSX from 'xlsx';
const EXCEL_TYPE = 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8';
const EXCEL_EXTENSION = '.xlsx';
@Injectable()
export class ExcelService {
  constructor() { }

  public exportAsExcelFile(json: any): void {
    let nameList=[]
    let sheetList:XLSX.WorkSheet=[]
    let fileName='';
    for(let i in json){
      nameList.push(i.substring(0,30))
      fileName+='_'+i.substring(0,i.indexOf('_'))
      sheetList[i.toString()]=this.getList(json[i])
    }
    let workbook: XLSX.WorkBook = { Sheets: sheetList, SheetNames: nameList };
    const excelBuffer: any = XLSX.write(workbook, { bookType: 'xlsx', type: 'array' });
    this.saveAsExcelFile(excelBuffer,fileName);
  }

  private saveAsExcelFile(buffer: any, fileName: string): void {
    const data: Blob = new Blob([buffer], {type: EXCEL_TYPE});
    let  date:Date=new Date();
    FileSaver.saveAs(data, fileName + EXCEL_EXTENSION);
  }

  private getList(json: any[]){
    return XLSX.utils.json_to_sheet(json)
  }

}