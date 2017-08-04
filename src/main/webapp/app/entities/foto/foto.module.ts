import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    FotoService,
    FotoPopupService,
    FotoComponent,
    FotoDetailComponent,
    FotoDialogComponent,
    FotoPopupComponent,
    FotoDeletePopupComponent,
    FotoDeleteDialogComponent,
    fotoRoute,
    fotoPopupRoute,
    FotoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...fotoRoute,
    ...fotoPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FotoComponent,
        FotoDetailComponent,
        FotoDialogComponent,
        FotoDeleteDialogComponent,
        FotoPopupComponent,
        FotoDeletePopupComponent,
    ],
    entryComponents: [
        FotoComponent,
        FotoDialogComponent,
        FotoPopupComponent,
        FotoDeleteDialogComponent,
        FotoDeletePopupComponent,
    ],
    providers: [
        FotoService,
        FotoPopupService,
        FotoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornFotoModule {}
