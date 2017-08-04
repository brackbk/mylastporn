import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ModulosComponent } from './modulos.component';
import { ModulosDetailComponent } from './modulos-detail.component';
import { ModulosPopupComponent } from './modulos-dialog.component';
import { ModulosDeletePopupComponent } from './modulos-delete-dialog.component';

import { Principal } from '../../shared';

export const modulosRoute: Routes = [
    {
        path: 'modulos',
        component: ModulosComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.modulos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'modulos/:id',
        component: ModulosDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.modulos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const modulosPopupRoute: Routes = [
    {
        path: 'modulos-new',
        component: ModulosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.modulos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modulos/:id/edit',
        component: ModulosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.modulos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'modulos/:id/delete',
        component: ModulosDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.modulos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
