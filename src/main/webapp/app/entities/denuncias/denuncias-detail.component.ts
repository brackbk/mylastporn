import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Denuncias } from './denuncias.model';
import { DenunciasService } from './denuncias.service';

@Component({
    selector: 'jhi-denuncias-detail',
    templateUrl: './denuncias-detail.component.html'
})
export class DenunciasDetailComponent implements OnInit, OnDestroy {

    denuncias: Denuncias;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private denunciasService: DenunciasService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInDenuncias();
    }

    load(id) {
        this.denunciasService.find(id).subscribe((denuncias) => {
            this.denuncias = denuncias;
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

    registerChangeInDenuncias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'denunciasListModification',
            (response) => this.load(this.denuncias.id)
        );
    }
}
