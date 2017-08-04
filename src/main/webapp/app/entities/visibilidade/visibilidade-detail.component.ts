import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Visibilidade } from './visibilidade.model';
import { VisibilidadeService } from './visibilidade.service';

@Component({
    selector: 'jhi-visibilidade-detail',
    templateUrl: './visibilidade-detail.component.html'
})
export class VisibilidadeDetailComponent implements OnInit, OnDestroy {

    visibilidade: Visibilidade;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private visibilidadeService: VisibilidadeService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInVisibilidades();
    }

    load(id) {
        this.visibilidadeService.find(id).subscribe((visibilidade) => {
            this.visibilidade = visibilidade;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInVisibilidades() {
        this.eventSubscriber = this.eventManager.subscribe(
            'visibilidadeListModification',
            (response) => this.load(this.visibilidade.id)
        );
    }
}
