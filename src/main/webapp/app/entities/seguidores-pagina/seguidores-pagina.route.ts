import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { SeguidoresPaginaComponent } from './seguidores-pagina.component';
import { SeguidoresPaginaDetailComponent } from './seguidores-pagina-detail.component';
import { SeguidoresPaginaPopupComponent } from './seguidores-pagina-dialog.component';
import { SeguidoresPaginaDeletePopupComponent } from './seguidores-pagina-delete-dialog.component';

import { Principal } from '../../shared';

export const seguidoresPaginaRoute: Routes = [
    {
        path: 'seguidores-pagina',
        component: SeguidoresPaginaComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.seguidoresPagina.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'seguidores-pagina/:id',
        component: SeguidoresPaginaDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.seguidoresPagina.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const seguidoresPaginaPopupRoute: Routes = [
    {
        path: 'seguidores-pagina-new',
        component: SeguidoresPaginaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.seguidoresPagina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'seguidores-pagina/:id/edit',
        component: SeguidoresPaginaPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.seguidoresPagina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'seguidores-pagina/:id/delete',
        component: SeguidoresPaginaDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'mylastpornApp.seguidoresPagina.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
