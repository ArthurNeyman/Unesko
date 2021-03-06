import { Component } from '@angular/core';
import {News} from "../../../models/news/news.model";
import {NewsService} from "../../../services/news.service";

@Component({
  selector: 'list-news-page',
  templateUrl: './list-news.component.html'
})

export class ListNewsComponent  {

public _listOfNews: News[];
public loader: boolean = true;

  constructor(private newsService: NewsService) {
    this._listOfNews = [];
    this.GetNews();
  }

  public GetNews() {
    this.newsService.GetAll()
      .subscribe((res) => {
          this.loader = false;
          this._listOfNews = res.data;
        });
  }
}
