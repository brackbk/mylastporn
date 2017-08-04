import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Amigos } from './amigos.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AmigosService {

    private resourceUrl = 'api/amigos';
    private resourceSearchUrl = 'api/_search/amigos';

    constructor(private http: Http) { }

    create(amigos: Amigos): Observable<Amigos> {
        const copy = this.convert(amigos);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(amigos: Amigos): Observable<Amigos> {
        const copy = this.convert(amigos);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Amigos> {
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

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convert(amigos: Amigos): Amigos {
        const copy: Amigos = Object.assign({}, amigos);
        return copy;
    }
}
