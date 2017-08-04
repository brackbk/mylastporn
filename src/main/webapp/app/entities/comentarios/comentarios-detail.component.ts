import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager , JhiDataUtils } from 'ng-jhipster';

import { Comentarios } from './comentarios.model';
import { ComentariosService } from './comentarios.service';

@Component({
    selector: 'jhi-comentarios-detail',
    templateUrl: './comentarios-detail.component.html'
})
export class ComentariosDetailComponent implements OnInit, OnDestroy {

    comentarios: Comentarios;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private dataUtils: JhiDataUtils,
        private comentariosService: ComentariosService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInComentarios();
    }

    load(id) {
        this.comentariosService.find(id).subscribe((comentarios) => {
            this.comentarios = comentarios;
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

    registerChangeInComentarios() {
        this.eventSubscriber = this.eventManager.subscribe(
            'comentariosListModification',
            (response) => this.load(this.comentarios.id)
        );
    }
}
