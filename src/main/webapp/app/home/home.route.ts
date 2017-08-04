import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { HomeComponent } from './home.component';
import { HomeDetailComponent } from './home-detail.component';

import { Principal } from '../shared';

@Injectable()
export class HomeResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) { }

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
        };
    }
}

export const homeRoute: Routes = [
    {
        path: '',
        component: HomeComponent,
        resolve: {
            'pagingParams': HomeResolvePagingParams
        },
        data: {
            authorities: [],
            pageTitle: 'mylastpornApp.video.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
     {
        path: 'home',
        component: HomeComponent,
        resolve: {
            'pagingParams': HomeResolvePagingParams
        },
        data: {
            authorities: [],
            pageTitle: 'mylastpornApp.video.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'video/:titulo/:id',
        component: HomeDetailComponent,
        data: {
            authorities: [],
            pageTitle: 'mylastpornApp.video.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];
