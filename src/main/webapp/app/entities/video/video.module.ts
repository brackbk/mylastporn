import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MylastpornSharedModule } from '../../shared';
import { MylastpornAdminModule } from '../../admin/admin.module';
import {
    VideoService,
    VideoPopupService,
    VideoComponent,
    VideoDetailComponent,
    VideoDialogComponent,
    VideoPopupComponent,
    VideoDeletePopupComponent,
    VideoDeleteDialogComponent,
    videoRoute,
    videoPopupRoute,
    VideoResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...videoRoute,
    ...videoPopupRoute,
];

@NgModule({
    imports: [
        MylastpornSharedModule,
        MylastpornAdminModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        VideoComponent,
        VideoDetailComponent,
        VideoDialogComponent,
        VideoDeleteDialogComponent,
        VideoPopupComponent,
        VideoDeletePopupComponent,
    ],
    entryComponents: [
        VideoComponent,
        VideoDialogComponent,
        VideoPopupComponent,
        VideoDeleteDialogComponent,
        VideoDeletePopupComponent,
    ],
    providers: [
        VideoService,
        VideoPopupService,
        VideoResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MylastpornVideoModule {}
