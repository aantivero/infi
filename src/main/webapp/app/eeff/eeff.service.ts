import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { EntidadesFinancieras } from './eeff.model';
import { ResponseWrapper, createRequestOption } from '../shared';

@Injectable()
export class EntidadesFinancierasService {

    private resourceUrl = 'api/eeff';

    constructor(private http: Http) { }

    find(id: number): Observable<EntidadesFinancieras> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(entidadesFinancieras: EntidadesFinancieras): EntidadesFinancieras {
        const copy: EntidadesFinancieras = Object.assign({}, entidadesFinancieras);
        return copy;
    }
}
