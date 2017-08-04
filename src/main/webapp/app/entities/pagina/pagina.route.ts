import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { PaginaComponent } from './pagina.component';
import { PaginaDetailComponent } from './pagina-detail.component';
import { PaginaPopupComponent } from './pagina-dialog.component';
import { PaginaDeletePopupComponent } from './pagina-delete-dialog.component';

import { Principal } from '../../shared';

export const paginaRoute: Routes = [
    {
        path: 'pagina',
        component: PaginaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.pagina.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'pagina/:id',
        component: PaginaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.pagina.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const paginaPopupRoute: Routes = [
    {
        path: 'pagina-new',
        component: PaginaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.pagina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pagina/:id/edit',
        component: PaginaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.pagina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'pagina/:id/delete',
        component: PaginaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.pagina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
