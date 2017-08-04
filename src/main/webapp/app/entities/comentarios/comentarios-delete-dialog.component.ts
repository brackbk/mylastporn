import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Comentarios } from './comentarios.model';
import { ComentariosPopupService } from './comentarios-popup.service';
import { ComentariosService } from './comentarios.service';

@Component({
    selector: 'jhi-comentarios-delete-dialog',
    templateUrl: './comentarios-delete-dialog.component.html'
})
export class ComentariosDeleteDialogComponent {

    comentarios: Comentarios;

    constructor(
        private comentariosService: ComentariosService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.comentariosService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'comentariosListModification',
                content: 'Deleted an comentarios'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-comentarios-delete-popup',
    template: ''
})
export class ComentariosDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private comentariosPopupService: ComentariosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.comentariosPopupService
                .open(ComentariosDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
