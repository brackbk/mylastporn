import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Modulos } from './modulos.model';
import { ModulosService } from './modulos.service';

@Component({
    selector: 'jhi-modulos-detail',
    templateUrl: './modulos-detail.component.html'
})
export class ModulosDetailComponent implements OnInit, OnDestroy {

    modulos: Modulos;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private modulosService: ModulosService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInModulos();
    }

    load(id) {
        this.modulosService.find(id).subscribe((modulos) => {
            this.modulos = modulos;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInModulos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'modulosListModification',
            (response) => this.load(this.modulos.id)
        );
    }
}
