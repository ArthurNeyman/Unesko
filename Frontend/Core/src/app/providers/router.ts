import { DefaultUrlSerializer, UrlTree } from "@angular/router";

export class LowerCaseUrlSerializer extends DefaultUrlSerializer {
    parse(url: string): UrlTree {
        if (url == '/Performance') {
            return super.parse(url);
        } else {
            return super.parse(url.toLowerCase());
        }
    }
}