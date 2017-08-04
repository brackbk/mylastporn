import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { VisibilidadeComponent } from './visibilidade.component';
import { VisibilidadeDetailComponent } from './visibilidade-detail.component';
import { VisibilidadePopupComponent } from './visibilidade-dialog.component';
import { VisibilidadeDeletePopupComponent } from './visibilidade-delete-dialog.component';

import { Principal } from '../../shared';

export const visibilidadeRoute: Routes = [
    {
        path: 'visibilidade',
        component: VisibilidadeComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.visibilidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'visibilidade/:id',
        component: VisibilidadeDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.visibilidade.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const visibilidadePopupRoute: Routes = [
    {
        path: 'visibilidade-new',
        component: VisibilidadePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.visibilidade.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visibilidade/:id/edit',
        component: VisibilidadePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.visibilidade.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'visibilidade/:id/delete',
        component: VisibilidadeDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.visibilidade.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
