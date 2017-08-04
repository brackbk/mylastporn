import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Visibilidade } from './visibilidade.model';
import { VisibilidadePopupService } from './visibilidade-popup.service';
import { VisibilidadeService } from './visibilidade.service';

@Component({
    selector: 'jhi-visibilidade-dialog',
    templateUrl: './visibilidade-dialog.component.html'
})
export class VisibilidadeDialogComponent implements OnInit {

    visibilidade: Visibilidade;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private visibilidadeService: VisibilidadeService,
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
        if (this.visibilidade.id !== undefined) {
            this.subscribeToSaveResponse(
                this.visibilidadeService.update(this.visibilidade));
        } else {
            this.subscribeToSaveResponse(
                this.visibilidadeService.create(this.visibilidade));
        }
    }

    private subscribeToSaveResponse(result: Observable<Visibilidade>) {
        result.subscribe((res: Visibilidade) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Visibilidade) {
        this.eventManager.broadcast({ name: 'visibilidadeListModification', content: 'OK'});
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
    selector: 'jhi-visibilidade-popup',
    template: ''
})
export class VisibilidadePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visibilidadePopupService: VisibilidadePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.visibilidadePopupService
                    .open(VisibilidadeDialogComponent, params['id']);
            } else {
                this.modalRef = this.visibilidadePopupService
                    .open(VisibilidadeDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
