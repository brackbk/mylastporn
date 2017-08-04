import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { MylastpornSharedModule } from '../../shared';
import {
    TipoService,
    TipoPopupService,
    TipoComponent,
    TipoDetailComponent,
    TipoDialogComponent,
    TipoPopupComponent,
    TipoDeletePopupComponent,
    TipoDeleteDialogComponent,
    tipoRoute,
    tipoPopupRoute
} from './';

const ENTITY_STATES = [
    ...tipoRoute,
    ...tipoPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        TipoComponent,
        TipoDetailComponent,
        TipoDialogComponent,
        TipoDeleteDialogComponent,
        TipoPopupComponent,
        TipoDeletePopupComponent,
    ],
    entryComponents: [
        TipoComponent,
        TipoDialogComponent,
        TipoPopupComponent,
        TipoDeleteDialogComponent,
        TipoDeletePopupComponent,
    ],
    providers: [
        TipoService,
        TipoPopupService
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornTipoModule {}
