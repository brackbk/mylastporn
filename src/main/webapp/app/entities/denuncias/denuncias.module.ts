import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    DenunciasService,
    DenunciasPopupService,
    DenunciasComponent,
    DenunciasDetailComponent,
    DenunciasDialogComponent,
    DenunciasPopupComponent,
    DenunciasDeletePopupComponent,
    DenunciasDeleteDialogComponent,
    denunciasRoute,
    denunciasPopupRoute,
} from './';

const ENTITY_STATES = [
    ...denunciasRoute,
    ...denunciasPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        DenunciasComponent,
        DenunciasDetailComponent,
        DenunciasDialogComponent,
        DenunciasDeleteDialogComponent,
        DenunciasPopupComponent,
        DenunciasDeletePopupComponent,
    ],
    entryComponents: [
        DenunciasComponent,
        DenunciasDialogComponent,
        DenunciasPopupComponent,
        DenunciasDeleteDialogComponent,
        DenunciasDeletePopupComponent,
    ],
    providers: [
        DenunciasService,
        DenunciasPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornDenunciasModule {}
