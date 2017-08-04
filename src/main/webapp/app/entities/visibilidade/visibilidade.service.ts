import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';

import { Visibilidade } from './visibilidade.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class VisibilidadeService {

    private resourceUrl = 'api/visibilidades';
    private resourceSearchUrl = 'api/_search/visibilidades';

    constructor(private http: Http) { }

    create(visibilidade: Visibilidade): Observable<Visibilidade> {
        const copy = this.convert(visibilidade);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    update(visibilidade: Visibilidade): Observable<Visibilidade> {
        const copy = this.convert(visibilidade);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            return res.json();
        });
    }

    find(id: number): Observable<Visibilidade> {
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

    private convert(visibilidade: Visibilidade): Visibilidade {
        const copy: Visibilidade = Object.assign({}, visibilidade);
        return copy;
    }
}
