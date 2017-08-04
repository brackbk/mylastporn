import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import {HashLocationStrategy, LocationStrategy} from '@angular/common';
import { navbarRoute } from '../app.route';
import { errorRoute } from './';

const LAYOUT_ROUTES = [
    navbarRoute,
    ...errorRoute
];

@NgModule({
    imports: [
        RouterModule.forRoot(LAYOUT_ROUTES, { useHash: true }),
    ],
    providers: [
        {provide: LocationStrategy,  useClass: HashLocationStrategy }
    ],
    exports: [
        RouterModule
    ]
})
export class LayoutRoutingModule {}
