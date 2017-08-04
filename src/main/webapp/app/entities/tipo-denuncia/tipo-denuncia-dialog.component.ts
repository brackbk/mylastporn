import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { TipoDenuncia } from './tipo-denuncia.model';
import { TipoDenunciaPopupService } from './tipo-denuncia-popup.service';
import { TipoDenunciaService } from './tipo-denuncia.service';

@Component({
    selector: 'jhi-tipo-denuncia-dialog',
    templateUrl: './tipo-denuncia-dialog.component.html'
})
export class TipoDenunciaDialogComponent implements OnInit {

    tipoDenuncia: TipoDenuncia;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tipoDenunciaService: TipoDenunciaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tipoDenuncia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipoDenunciaService.update(this.tipoDenuncia));
        } else {
            this.subscribeToSaveResponse(
                this.tipoDenunciaService.create(this.tipoDenuncia));
        }
    }

    private subscribeToSaveResponse(result: Observable<TipoDenuncia>) {
        result.subscribe((res: TipoDenuncia) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: TipoDenuncia) {
        this.eventManager.broadcast({ name: 'tipoDenunciaListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-tipo-denuncia-popup',
    template: ''
})
export class TipoDenunciaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoDenunciaPopupService: TipoDenunciaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.tipoDenunciaPopupService
                    .open(TipoDenunciaDialogComponent, params['id']);
            } else {
                this.modalRef = this.tipoDenunciaPopupService
                    .open(TipoDenunciaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
