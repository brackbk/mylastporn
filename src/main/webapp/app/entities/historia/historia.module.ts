import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    HistoriaService,
    HistoriaPopupService,
    HistoriaComponent,
    HistoriaDetailComponent,
    HistoriaDialogComponent,
    HistoriaPopupComponent,
    HistoriaDeletePopupComponent,
    HistoriaDeleteDialogComponent,
    historiaRoute,
    historiaPopupRoute,
    HistoriaResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...historiaRoute,
    ...historiaPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        HistoriaComponent,
        HistoriaDetailComponent,
        HistoriaDialogComponent,
        HistoriaDeleteDialogComponent,
        HistoriaPopupComponent,
        HistoriaDeletePopupComponent,
    ],
    entryComponents: [
        HistoriaComponent,
        HistoriaDialogComponent,
        HistoriaPopupComponent,
        HistoriaDeleteDialogComponent,
        HistoriaDeletePopupComponent,
    ],
    providers: [
        HistoriaService,
        HistoriaPopupService,
        HistoriaResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornHistoriaModule {}
