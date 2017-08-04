import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    PaginaService,
    PaginaPopupService,
    PaginaComponent,
    PaginaDetailComponent,
    PaginaDialogComponent,
    PaginaPopupComponent,
    PaginaDeletePopupComponent,
    PaginaDeleteDialogComponent,
    paginaRoute,
    paginaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...paginaRoute,
    ...paginaPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaginaComponent,
        PaginaDetailComponent,
        PaginaDialogComponent,
        PaginaDeleteDialogComponent,
        PaginaPopupComponent,
        PaginaDeletePopupComponent,
    ],
    entryComponents: [
        PaginaComponent,
        PaginaDialogComponent,
        PaginaPopupComponent,
        PaginaDeleteDialogComponent,
        PaginaDeletePopupComponent,
    ],
    providers: [
        PaginaService,
        PaginaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornPaginaModule {}
