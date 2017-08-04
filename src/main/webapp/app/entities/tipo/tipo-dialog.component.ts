import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Tipo } from './tipo.model';
import { TipoPopupService } from './tipo-popup.service';
import { TipoService } from './tipo.service';
import { Modulos, ModulosService } from '../modulos';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-tipo-dialog',
    templateUrl: './tipo-dialog.component.html'
})
export class TipoDialogComponent implements OnInit {

    tipo: Tipo;
    authorities: any[];
    isSaving: boolean;

    modulos: Modulos[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private tipoService: TipoService,
        private modulosService: ModulosService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.modulosService.query()
            .subscribe((res: ResponseWrapper) => { this.modulos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.tipo.id !== undefined) {
            this.subscribeToSaveResponse(
                this.tipoService.update(this.tipo));
        } else {
            this.subscribeToSaveResponse(
                this.tipoService.create(this.tipo));
        }
    }

    private subscribeToSaveResponse(result: Observable<Tipo>) {
        result.subscribe((res: Tipo) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Tipo) {
        this.eventManager.broadcast({ name: 'tipoListModification', content: 'OK'});
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

    trackModulosById(index: number, item: Modulos) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-tipo-popup',
    template: ''
})
export class TipoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private tipoPopupService: TipoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.tipoPopupService
                    .open(TipoDialogComponent, params['id']);
            } else {
                this.modalRef = this.tipoPopupService
                    .open(TipoDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
