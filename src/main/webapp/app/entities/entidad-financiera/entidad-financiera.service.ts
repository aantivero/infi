import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { EntidadFinanciera } from './entidad-financiera.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EntidadFinancieraService {

    private resourceUrl = 'api/entidad-financieras';

    constructor(private http: Http) { }

    create(entidadFinanciera: EntidadFinanciera): Observable<EntidadFinanciera> {
        const copy = this.convert(entidadFinanciera);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(entidadFinanciera: EntidadFinanciera): Observable<EntidadFinanciera> {
        const copy = this.convert(entidadFinanciera);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<EntidadFinanciera> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            return res.json();
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
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(entidadFinanciera: EntidadFinanciera): EntidadFinanciera {
        const copy: EntidadFinanciera = Object.assign({}, entidadFinanciera);
        return copy;
    }
}
