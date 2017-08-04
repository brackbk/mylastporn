import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { FavoritosComponent } from './favoritos.component';
import { FavoritosDetailComponent } from './favoritos-detail.component';
import { FavoritosPopupComponent } from './favoritos-dialog.component';
import { FavoritosDeletePopupComponent } from './favoritos-delete-dialog.component';

import { Principal } from '../../shared';

export const favoritosRoute: Routes = [
    {
        path: 'favoritos',
        component: FavoritosComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.favoritos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'favoritos/:id',
        component: FavoritosDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.favoritos.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const favoritosPopupRoute: Routes = [
    {
        path: 'favoritos-new',
        component: FavoritosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.favoritos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favoritos/:id/edit',
        component: FavoritosPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.favoritos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'favoritos/:id/delete',
        component: FavoritosDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.favoritos.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
