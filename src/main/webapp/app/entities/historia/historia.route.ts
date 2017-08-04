import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HistoriaComponent } from './historia.component';
import { HistoriaDetailComponent } from './historia-detail.component';
import { HistoriaPopupComponent } from './historia-dialog.component';
import { HistoriaDeletePopupComponent } from './historia-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class HistoriaResolvePagingParams implements Resolve<any> {

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

export const historiaRoute: Routes = [
    {
        path: 'historia',
        component: HistoriaComponent,
        resolve: {
            'pagingParams': HistoriaResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.historia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'historia/:id',
        component: HistoriaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.historia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const historiaPopupRoute: Routes = [
    {
        path: 'historia-new',
        component: HistoriaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.historia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'historia/:id/edit',
        component: HistoriaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.historia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'historia/:id/delete',
        component: HistoriaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.historia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
