import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FotoComponent } from './foto.component';
import { FotoDetailComponent } from './foto-detail.component';
import { FotoPopupComponent } from './foto-dialog.component';
import { FotoDeletePopupComponent } from './foto-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class FotoResolvePagingParams implements Resolve<any> {

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

export const fotoRoute: Routes = [
    {
        path: 'foto',
        component: FotoComponent,
        resolve: {
            'pagingParams': FotoResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.foto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'foto/:id',
        component: FotoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.foto.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const fotoPopupRoute: Routes = [
    {
        path: 'foto-new',
        component: FotoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.foto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'foto/:id/edit',
        component: FotoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.foto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'foto/:id/delete',
        component: FotoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.foto.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
