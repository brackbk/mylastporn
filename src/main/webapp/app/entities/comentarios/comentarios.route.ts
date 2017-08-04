import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ComentariosComponent } from './comentarios.component';
import { ComentariosDetailComponent } from './comentarios-detail.component';
import { ComentariosPopupComponent } from './comentarios-dialog.component';
import { ComentariosDeletePopupComponent } from './comentarios-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class ComentariosResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const comentariosRoute: Routes = [
    {
        path: 'comentarios',
        component: ComentariosComponent,
        resolve: {
            'pagingParams': ComentariosResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.comentarios.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'comentarios/:id',
        component: ComentariosDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.comentarios.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const comentariosPopupRoute: Routes = [
    {
        path: 'comentarios-new',
        component: ComentariosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.comentarios.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comentarios/:id/edit',
        component: ComentariosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.comentarios.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'comentarios/:id/delete',
        component: ComentariosDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.comentarios.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
