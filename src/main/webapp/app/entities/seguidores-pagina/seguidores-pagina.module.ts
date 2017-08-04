import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    SeguidoresPaginaService,
    SeguidoresPaginaPopupService,
    SeguidoresPaginaComponent,
    SeguidoresPaginaDetailComponent,
    SeguidoresPaginaDialogComponent,
    SeguidoresPaginaPopupComponent,
    SeguidoresPaginaDeletePopupComponent,
    SeguidoresPaginaDeleteDialogComponent,
    seguidoresPaginaRoute,
    seguidoresPaginaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...seguidoresPaginaRoute,
    ...seguidoresPaginaPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        SeguidoresPaginaComponent,
        SeguidoresPaginaDetailComponent,
        SeguidoresPaginaDialogComponent,
        SeguidoresPaginaDeleteDialogComponent,
        SeguidoresPaginaPopupComponent,
        SeguidoresPaginaDeletePopupComponent,
    ],
    entryComponents: [
        SeguidoresPaginaComponent,
        SeguidoresPaginaDialogComponent,
        SeguidoresPaginaPopupComponent,
        SeguidoresPaginaDeleteDialogComponent,
        SeguidoresPaginaDeletePopupComponent,
    ],
    providers: [
        SeguidoresPaginaService,
        SeguidoresPaginaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornSeguidoresPaginaModule {}
