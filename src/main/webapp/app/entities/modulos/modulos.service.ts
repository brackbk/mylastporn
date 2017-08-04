import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils } from 'ng-jhipster';

import { Modulos } from './modulos.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class ModulosService {

    private resourceUrl = 'api/modulos';
    private resourceSearchUrl = 'api/_search/modulos';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(modulos: Modulos): Observable<Modulos> {
        const copy = this.convert(modulos);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    update(modulos: Modulos): Observable<Modulos> {
        const copy = this.convert(modulos);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
        });
    }

    find(id: number): Observable<Modulos> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            this.convertItemFromServer(jsonResponse);
            return jsonResponse;
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
        for (let i = 0; i < jsonResponse.length; i++) {
            this.convertItemFromServer(jsonResponse[i]);
        }
        return new ResponseWrapper(res.headers, jsonResponse, res.status);
    }

    private convertItemFromServer(entity: any) {
        entity.datacriado = this.dateUtils
            .convertDateTimeFromServer(entity.datacriado);
    }

    private convert(modulos: Modulos): Modulos {
        const copy: Modulos = Object.assign({}, modulos);

        copy.datacriado = this.dateUtils.toDate(modulos.datacriado);
        return copy;
    }
}
