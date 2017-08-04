import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { DenunciasComponent } from './denuncias.component';
import { DenunciasDetailComponent } from './denuncias-detail.component';
import { DenunciasPopupComponent } from './denuncias-dialog.component';
import { DenunciasDeletePopupComponent } from './denuncias-delete-dialog.component';

import { Principal } from '../../shared';

export const denunciasRoute: Routes = [
    {
        path: 'denuncias',
        component: DenunciasComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.denuncias.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'denuncias/:id',
        component: DenunciasDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.denuncias.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const denunciasPopupRoute: Routes = [
    {
        path: 'denuncias-new',
        component: DenunciasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.denuncias.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'denuncias/:id/edit',
        component: DenunciasPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.denuncias.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'denuncias/:id/delete',
        component: DenunciasDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.denuncias.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
