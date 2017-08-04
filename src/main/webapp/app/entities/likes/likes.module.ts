import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    LikesService,
    LikesPopupService,
    LikesComponent,
    LikesDetailComponent,
    LikesDialogComponent,
    LikesPopupComponent,
    LikesDeletePopupComponent,
    LikesDeleteDialogComponent,
    likesRoute,
    likesPopupRoute,
} from './';

const ENTITY_STATES = [
    ...likesRoute,
    ...likesPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LikesComponent,
        LikesDetailComponent,
        LikesDialogComponent,
        LikesDeleteDialogComponent,
        LikesPopupComponent,
        LikesDeletePopupComponent,
    ],
    entryComponents: [
        LikesComponent,
        LikesDialogComponent,
        LikesPopupComponent,
        LikesDeleteDialogComponent,
        LikesDeletePopupComponent,
    ],
    providers: [
        LikesService,
        LikesPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornLikesModule {}
