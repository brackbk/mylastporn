import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Video } from './video.model';
import { VideoService } from './video.service';

@Component({
    selector: 'jhi-video-detail',
    templateUrl: './video-detail.component.html'
})
export class VideoDetailComponent implements OnInit, OnDestroy {

    video: Video;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private videoService: VideoService,
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
        this.videoService.find(id).subscribe((video) => {
            this.video = video;
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
            (response) => this.load(this.video.id)
        );
    }
}
