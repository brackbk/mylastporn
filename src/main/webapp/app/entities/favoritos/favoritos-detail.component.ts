import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Favoritos } from './favoritos.model';
import { FavoritosService } from './favoritos.service';

@Component({
    selector: 'jhi-favoritos-detail',
    templateUrl: './favoritos-detail.component.html'
})
export class FavoritosDetailComponent implements OnInit, OnDestroy {

    favoritos: Favoritos;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private favoritosService: FavoritosService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFavoritos();
    }

    load(id) {
        this.favoritosService.find(id).subscribe((favoritos) => {
            this.favoritos = favoritos;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInFavoritos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'favoritosListModification',
            (response) => this.load(this.favoritos.id)
        );
    }
}
