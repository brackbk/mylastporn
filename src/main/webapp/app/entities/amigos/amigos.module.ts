import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    AmigosService,
    AmigosPopupService,
    AmigosComponent,
    AmigosDetailComponent,
    AmigosDialogComponent,
    AmigosPopupComponent,
    AmigosDeletePopupComponent,
    AmigosDeleteDialogComponent,
    amigosRoute,
    amigosPopupRoute,
} from './';

const ENTITY_STATES = [
    ...amigosRoute,
    ...amigosPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AmigosComponent,
        AmigosDetailComponent,
        AmigosDialogComponent,
        AmigosDeleteDialogComponent,
        AmigosPopupComponent,
        AmigosDeletePopupComponent,
    ],
    entryComponents: [
        AmigosComponent,
        AmigosDialogComponent,
        AmigosPopupComponent,
        AmigosDeleteDialogComponent,
        AmigosDeletePopupComponent,
    ],
    providers: [
        AmigosService,
        AmigosPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornAmigosModule {}
