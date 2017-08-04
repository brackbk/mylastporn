import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LikesComponent } from './likes.component';
import { LikesDetailComponent } from './likes-detail.component';
import { LikesPopupComponent } from './likes-dialog.component';
import { LikesDeletePopupComponent } from './likes-delete-dialog.component';

import { Principal } from '../../shared';

export const likesRoute: Routes = [
    {
        path: 'likes',
        component: LikesComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.likes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'likes/:id',
        component: LikesDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.likes.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const likesPopupRoute: Routes = [
    {
        path: 'likes-new',
        component: LikesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.likes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'likes/:id/edit',
        component: LikesPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.likes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'likes/:id/delete',
        component: LikesDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.likes.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
