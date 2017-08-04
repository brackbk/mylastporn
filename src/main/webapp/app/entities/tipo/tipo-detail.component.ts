import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Tipo } from './tipo.model';
import { TipoService } from './tipo.service';

@Component({
    selector: 'jhi-tipo-detail',
    templateUrl: './tipo-detail.component.html'
})
export class TipoDetailComponent implements OnInit, OnDestroy {

    tipo: Tipo;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipoService: TipoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipos();
    }

    load(id) {
        this.tipoService.find(id).subscribe((tipo) => {
            this.tipo = tipo;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipoListModification',
            (response) => this.load(this.tipo.id)
        );
    }
}
