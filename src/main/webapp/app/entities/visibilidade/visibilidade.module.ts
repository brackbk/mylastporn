import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import {
    VisibilidadeService,
    VisibilidadePopupService,
    VisibilidadeComponent,
    VisibilidadeDetailComponent,
    VisibilidadeDialogComponent,
    VisibilidadePopupComponent,
    VisibilidadeDeletePopupComponent,
    VisibilidadeDeleteDialogComponent,
    visibilidadeRoute,
    visibilidadePopupRoute,
} from './';

const ENTITY_STATES = [
    ...visibilidadeRoute,
    ...visibilidadePopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VisibilidadeComponent,
        VisibilidadeDetailComponent,
        VisibilidadeDialogComponent,
        VisibilidadeDeleteDialogComponent,
        VisibilidadePopupComponent,
        VisibilidadeDeletePopupComponent,
    ],
    entryComponents: [
        VisibilidadeComponent,
        VisibilidadeDialogComponent,
        VisibilidadePopupComponent,
        VisibilidadeDeleteDialogComponent,
        VisibilidadeDeletePopupComponent,
    ],
    providers: [
        VisibilidadeService,
        VisibilidadePopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornVisibilidadeModule {}
