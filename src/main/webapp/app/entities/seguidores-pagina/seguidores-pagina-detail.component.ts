import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { SeguidoresPagina } from './seguidores-pagina.model';
import { SeguidoresPaginaService } from './seguidores-pagina.service';

@Component({
    selector: 'jhi-seguidores-pagina-detail',
    templateUrl: './seguidores-pagina-detail.component.html'
})
export class SeguidoresPaginaDetailComponent implements OnInit, OnDestroy {

    seguidoresPagina: SeguidoresPagina;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private seguidoresPaginaService: SeguidoresPaginaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInSeguidoresPaginas();
    }

    load(id) {
        this.seguidoresPaginaService.find(id).subscribe((seguidoresPagina) => {
            this.seguidoresPagina = seguidoresPagina;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInSeguidoresPaginas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'seguidoresPaginaListModification',
            (response) => this.load(this.seguidoresPagina.id)
        );
    }
}
