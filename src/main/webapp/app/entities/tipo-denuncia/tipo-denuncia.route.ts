import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { TipoDenunciaComponent } from './tipo-denuncia.component';
import { TipoDenunciaDetailComponent } from './tipo-denuncia-detail.component';
import { TipoDenunciaPopupComponent } from './tipo-denuncia-dialog.component';
import { TipoDenunciaDeletePopupComponent } from './tipo-denuncia-delete-dialog.component';

import { Principal } from '../../shared';

export const tipoDenunciaRoute: Routes = [
    {
        path: 'tipo-denuncia',
        component: TipoDenunciaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipoDenuncia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'tipo-denuncia/:id',
        component: TipoDenunciaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipoDenuncia.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const tipoDenunciaPopupRoute: Routes = [
    {
        path: 'tipo-denuncia-new',
        component: TipoDenunciaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipoDenuncia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-denuncia/:id/edit',
        component: TipoDenunciaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipoDenuncia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'tipo-denuncia/:id/delete',
        component: TipoDenunciaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.tipoDenuncia.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
