import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    ComentariosService,
    ComentariosPopupService,
    ComentariosComponent,
    ComentariosDetailComponent,
    ComentariosDialogComponent,
    ComentariosPopupComponent,
    ComentariosDeletePopupComponent,
    ComentariosDeleteDialogComponent,
    comentariosRoute,
    comentariosPopupRoute,
    ComentariosResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...comentariosRoute,
    ...comentariosPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ComentariosComponent,
        ComentariosDetailComponent,
        ComentariosDialogComponent,
        ComentariosDeleteDialogComponent,
        ComentariosPopupComponent,
        ComentariosDeletePopupComponent,
    ],
    entryComponents: [
        ComentariosComponent,
        ComentariosDialogComponent,
        ComentariosPopupComponent,
        ComentariosDeleteDialogComponent,
        ComentariosDeletePopupComponent,
    ],
    providers: [
        ComentariosService,
        ComentariosPopupService,
        ComentariosResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornComentariosModule {}
