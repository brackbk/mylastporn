import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import {
    TipoDenunciaService,
    TipoDenunciaPopupService,
    TipoDenunciaComponent,
    TipoDenunciaDetailComponent,
    TipoDenunciaDialogComponent,
    TipoDenunciaPopupComponent,
    TipoDenunciaDeletePopupComponent,
    TipoDenunciaDeleteDialogComponent,
    tipoDenunciaRoute,
    tipoDenunciaPopupRoute,
} from './';

const ENTITY_STATES = [
    ...tipoDenunciaRoute,
    ...tipoDenunciaPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TipoDenunciaComponent,
        TipoDenunciaDetailComponent,
        TipoDenunciaDialogComponent,
        TipoDenunciaDeleteDialogComponent,
        TipoDenunciaPopupComponent,
        TipoDenunciaDeletePopupComponent,
    ],
    entryComponents: [
        TipoDenunciaComponent,
        TipoDenunciaDialogComponent,
        TipoDenunciaPopupComponent,
        TipoDenunciaDeleteDialogComponent,
        TipoDenunciaDeletePopupComponent,
    ],
    providers: [
        TipoDenunciaService,
        TipoDenunciaPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornTipoDenunciaModule {}
