import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Denuncias } from './denuncias.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DenunciasService {

    private resourceUrl = 'api/denuncias';
    private resourceSearchUrl = 'api/_search/denuncias';

    constructor(private http: Http) { }

    create(denuncias: Denuncias): Observable<Denuncias> {
        const copy = this.convert(denuncias);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(denuncias: Denuncias): Observable<Denuncias> {
        const copy = this.convert(denuncias);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Denuncias> {
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

    private convert(denuncias: Denuncias): Denuncias {
        const copy: Denuncias = Object.assign({}, denuncias);
        return copy;
    }
}
