import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { TipoDenuncia } from './tipo-denuncia.model';
import { TipoDenunciaPopupService } from './tipo-denuncia-popup.service';
import { TipoDenunciaService } from './tipo-denuncia.service';

@Component({
    selector: 'jhi-tipo-denuncia-delete-dialog',
    templateUrl: './tipo-denuncia-delete-dialog.component.html'
})
export class TipoDenunciaDeleteDialogComponent {

    tipoDenuncia: TipoDenuncia;

    constructor(
        private tipoDenunciaService: TipoDenunciaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tipoDenunciaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'tipoDenunciaListModification',
                content: 'Deleted an tipoDenuncia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tipo-denuncia-delete-popup',
    template: ''
})
export class TipoDenunciaDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoDenunciaPopupService: TipoDenunciaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.tipoDenunciaPopupService
                .open(TipoDenunciaDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
