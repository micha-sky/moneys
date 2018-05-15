import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { MoneyNg } from './money-ng.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class MoneyNgService {

    private resourceUrl =  SERVER_API_URL + 'api/monies';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(money: MoneyNg): Observable<MoneyNg> {
        const copy = this.convert(money);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(money: MoneyNg): Observable<MoneyNg> {
        const copy = this.convert(money);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<MoneyNg> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to MoneyNg.
     */
    private convertItemFromServer(json: any): MoneyNg {
        const entity: MoneyNg = Object.assign(new MoneyNg(), json);
        entity.date = this.dateUtils
            .convertDateTimeFromServer(json.date);
        return entity;
    }

    /**
     * Convert a MoneyNg to a JSON which can be sent to the server.
     */
    private convert(money: MoneyNg): MoneyNg {
        const copy: MoneyNg = Object.assign({}, money);

        copy.date = this.dateUtils.toDate(money.date);
        return copy;
    }
}
