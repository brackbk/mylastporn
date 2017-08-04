import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Home } from './home.model';
import { HomeService } from './home.service';

@Component({
    selector: 'jhi-home-detail',
    templateUrl: './home-detail.component.html'
})
export class HomeDetailComponent implements OnInit, OnDestroy {

    home: Home;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private homeService: HomeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVideos();
    }

    load(id) {
        this.homeService.find(id).subscribe((home) => {
            this.home = home;
        });
    }
    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVideos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'videoListModification',
            (response) => this.load(this.home.id)
        );
    }
}
