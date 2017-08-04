import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { SeguidoresPagina } from './seguidores-pagina.model';
import { SeguidoresPaginaPopupService } from './seguidores-pagina-popup.service';
import { SeguidoresPaginaService } from './seguidores-pagina.service';
import { Pagina, PaginaService } from '../pagina';
import { User, UserService } from '../../shared';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-seguidores-pagina-dialog',
    templateUrl: './seguidores-pagina-dialog.component.html'
})
export class SeguidoresPaginaDialogComponent implements OnInit {

    seguidoresPagina: SeguidoresPagina;
    authorities: any[];
    isSaving: boolean;

    paginas: Pagina[];

    users: User[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private seguidoresPaginaService: SeguidoresPaginaService,
        private paginaService: PaginaService,
        private userService: UserService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.paginaService.query()
            .subscribe((res: ResponseWrapper) => { this.paginas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.seguidoresPagina.id !== undefined) {
            this.subscribeToSaveResponse(
                this.seguidoresPaginaService.update(this.seguidoresPagina));
        } else {
            this.subscribeToSaveResponse(
                this.seguidoresPaginaService.create(this.seguidoresPagina));
        }
    }

    private subscribeToSaveResponse(result: Observable<SeguidoresPagina>) {
        result.subscribe((res: SeguidoresPagina) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: SeguidoresPagina) {
        this.eventManager.broadcast({ name: 'seguidoresPaginaListModification', content: 'OK'});
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

    trackPaginaById(index: number, item: Pagina) {
        return item.id;
    }

    trackUserById(index: number, item: User) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-seguidores-pagina-popup',
    template: ''
})
export class SeguidoresPaginaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seguidoresPaginaPopupService: SeguidoresPaginaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.seguidoresPaginaPopupService
                    .open(SeguidoresPaginaDialogComponent, params['id']);
            } else {
                this.modalRef = this.seguidoresPaginaPopupService
                    .open(SeguidoresPaginaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
