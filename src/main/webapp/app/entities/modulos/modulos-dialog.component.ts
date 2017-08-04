import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Modulos } from './modulos.model';
import { ModulosPopupService } from './modulos-popup.service';
import { ModulosService } from './modulos.service';

@Component({
    selector: 'jhi-modulos-dialog',
    templateUrl: './modulos-dialog.component.html'
})
export class ModulosDialogComponent implements OnInit {

    modulos: Modulos;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private modulosService: ModulosService,
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
        if (this.modulos.id !== undefined) {
            this.subscribeToSaveResponse(
                this.modulosService.update(this.modulos));
        } else {
            this.subscribeToSaveResponse(
                this.modulosService.create(this.modulos));
        }
    }

    private subscribeToSaveResponse(result: Observable<Modulos>) {
        result.subscribe((res: Modulos) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Modulos) {
        this.eventManager.broadcast({ name: 'modulosListModification', content: 'OK'});
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
    selector: 'jhi-modulos-popup',
    template: ''
})
export class ModulosPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modulosPopupService: ModulosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.modulosPopupService
                    .open(ModulosDialogComponent, params['id']);
            } else {
                this.modalRef = this.modulosPopupService
                    .open(ModulosDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
