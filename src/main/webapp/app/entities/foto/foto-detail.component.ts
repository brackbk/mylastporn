import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Foto } from './foto.model';
import { FotoService } from './foto.service';

@Component({
    selector: 'jhi-foto-detail',
    templateUrl: './foto-detail.component.html'
})
export class FotoDetailComponent implements OnInit, OnDestroy {

    foto: Foto;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private fotoService: FotoService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInFotos();
    }

    load(id) {
        this.fotoService.find(id).subscribe((foto) => {
            this.foto = foto;
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

    registerChangeInFotos() {
        this.eventSubscriber = this.eventManager.subscribe(
            'fotoListModification',
            (response) => this.load(this.foto.id)
        );
    }
}
