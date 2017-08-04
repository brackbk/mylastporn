import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import {
    ModulosService,
    ModulosPopupService,
    ModulosComponent,
    ModulosDetailComponent,
    ModulosDialogComponent,
    ModulosPopupComponent,
    ModulosDeletePopupComponent,
    ModulosDeleteDialogComponent,
    modulosRoute,
    modulosPopupRoute,
} from './';

const ENTITY_STATES = [
    ...modulosRoute,
    ...modulosPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ModulosComponent,
        ModulosDetailComponent,
        ModulosDialogComponent,
        ModulosDeleteDialogComponent,
        ModulosPopupComponent,
        ModulosDeletePopupComponent,
    ],
    entryComponents: [
        ModulosComponent,
        ModulosDialogComponent,
        ModulosPopupComponent,
        ModulosDeleteDialogComponent,
        ModulosDeletePopupComponent,
    ],
    providers: [
        ModulosService,
        ModulosPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornModulosModule {}
