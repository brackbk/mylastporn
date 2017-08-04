import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../shared';
import {
    HomeService,
    HomeComponent,
    HomeDetailComponent,
    homeRoute,
    HomeResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...homeRoute
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HomeComponent,
        HomeDetailComponent
    ],
    entryComponents: [
        HomeComponent
    ],
    providers: [
        HomeService,
        HomeResolvePagingParams
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornHomeModule {}
