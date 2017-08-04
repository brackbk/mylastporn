import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Tipo } from './tipo.model';
import { TipoPopupService } from './tipo-popup.service';
import { TipoService } from './tipo.service';

@Component({
    selector: 'jhi-tipo-delete-dialog',
    templateUrl: './tipo-delete-dialog.component.html'
})
export class TipoDeleteDialogComponent {

    tipo: Tipo;

    constructor(
        private tipoService: TipoService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipoListModification',
                content: 'Deleted an tipo'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-delete-popup',
    template: ''
})
export class TipoDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoPopupService: TipoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.tipoPopupService
                .open(TipoDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
