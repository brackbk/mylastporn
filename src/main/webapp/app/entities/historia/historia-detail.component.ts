import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Historia } from './historia.model';
import { HistoriaService } from './historia.service';

@Component({
    selector: 'jhi-historia-detail',
    templateUrl: './historia-detail.component.html'
})
export class HistoriaDetailComponent implements OnInit, OnDestroy {

    historia: Historia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private historiaService: HistoriaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInHistorias();
    }

    load(id) {
        this.historiaService.find(id).subscribe((historia) => {
            this.historia = historia;
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

    registerChangeInHistorias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'historiaListModification',
            (response) => this.load(this.historia.id)
        );
    }
}
