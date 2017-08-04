import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AmigosComponent } from './amigos.component';
import { AmigosDetailComponent } from './amigos-detail.component';
import { AmigosPopupComponent } from './amigos-dialog.component';
import { AmigosDeletePopupComponent } from './amigos-delete-dialog.component';

import { Principal } from '../../shared';

export const amigosRoute: Routes = [
    {
        path: 'amigos',
        component: AmigosComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.amigos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'amigos/:id',
        component: AmigosDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.amigos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const amigosPopupRoute: Routes = [
    {
        path: 'amigos-new',
        component: AmigosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.amigos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amigos/:id/edit',
        component: AmigosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.amigos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'amigos/:id/delete',
        component: AmigosDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.amigos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
