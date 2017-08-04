import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Pagina } from './pagina.model';
import { PaginaService } from './pagina.service';

@Component({
    selector: 'jhi-pagina-detail',
    templateUrl: './pagina-detail.component.html'
})
export class PaginaDetailComponent implements OnInit, OnDestroy {

    pagina: Pagina;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private paginaService: PaginaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPaginas();
    }

    load(id) {
        this.paginaService.find(id).subscribe((pagina) => {
            this.pagina = pagina;
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

    registerChangeInPaginas() {
        this.eventSubscriber = this.eventManager.subscribe(
            'paginaListModification',
            (response) => this.load(this.pagina.id)
        );
    }
}
