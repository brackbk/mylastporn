import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TipoComponent } from './tipo.component';
import { TipoDetailComponent } from './tipo-detail.component';
import { TipoPopupComponent } from './tipo-dialog.component';
import { TipoDeletePopupComponent } from './tipo-delete-dialog.component';

import { Principal } from '../../shared';

export const tipoRoute: Routes = [
    {
        path: 'tipo',
        component: TipoComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tipo/:id',
        component: TipoDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipo.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoPopupRoute: Routes = [
    {
        path: 'tipo-new',
        component: TipoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo/:id/edit',
        component: TipoPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo/:id/delete',
        component: TipoDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipo.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
