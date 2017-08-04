import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Foto } from './foto.model';
import { FotoPopupService } from './foto-popup.service';
import { FotoService } from './foto.service';

@Component({
    selector: 'jhi-foto-delete-dialog',
    templateUrl: './foto-delete-dialog.component.html'
})
export class FotoDeleteDialogComponent {

    foto: Foto;

    constructor(
        private fotoService: FotoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.fotoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'fotoListModification',
                content: 'Deleted an foto'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-foto-delete-popup',
    template: ''
})
export class FotoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fotoPopupService: FotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.fotoPopupService
                .open(FotoDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
