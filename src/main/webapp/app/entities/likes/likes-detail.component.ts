import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Likes } from './likes.model';
import { LikesService } from './likes.service';

@Component({
    selector: 'jhi-likes-detail',
    templateUrl: './likes-detail.component.html'
})
export class LikesDetailComponent implements OnInit, OnDestroy {

    likes: Likes;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private likesService: LikesService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLikes();
    }

    load(id) {
        this.likesService.find(id).subscribe((likes) => {
            this.likes = likes;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLikes() {
        this.eventSubscriber = this.eventManager.subscribe(
            'likesListModification',
            (response) => this.load(this.likes.id)
        );
    }
}
