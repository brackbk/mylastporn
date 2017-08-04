import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Amigos } from './amigos.model';
import { AmigosService } from './amigos.service';

@Component({
    selector: 'jhi-amigos-detail',
    templateUrl: './amigos-detail.component.html'
})
export class AmigosDetailComponent implements OnInit, OnDestroy {

    amigos: Amigos;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private amigosService: AmigosService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAmigos();
    }

    load(id) {
        this.amigosService.find(id).subscribe((amigos) => {
            this.amigos = amigos;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAmigos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'amigosListModification',
            (response) => this.load(this.amigos.id)
        );
    }
}
