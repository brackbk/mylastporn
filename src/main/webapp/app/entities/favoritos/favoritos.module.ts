import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    FavoritosService,
    FavoritosPopupService,
    FavoritosComponent,
    FavoritosDetailComponent,
    FavoritosDialogComponent,
    FavoritosPopupComponent,
    FavoritosDeletePopupComponent,
    FavoritosDeleteDialogComponent,
    favoritosRoute,
    favoritosPopupRoute,
} from './';

const ENTITY_STATES = [
    ...favoritosRoute,
    ...favoritosPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        FavoritosComponent,
        FavoritosDetailComponent,
        FavoritosDialogComponent,
        FavoritosDeleteDialogComponent,
        FavoritosPopupComponent,
        FavoritosDeletePopupComponent,
    ],
    entryComponents: [
        FavoritosComponent,
        FavoritosDialogComponent,
        FavoritosPopupComponent,
        FavoritosDeleteDialogComponent,
        FavoritosDeletePopupComponent,
    ],
    providers: [
        FavoritosService,
        FavoritosPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornFavoritosModule {}
