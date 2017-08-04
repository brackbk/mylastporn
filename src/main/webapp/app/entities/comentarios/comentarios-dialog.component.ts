import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Comentarios } from './comentarios.model';
import { ComentariosPopupService } from './comentarios-popup.service';
import { ComentariosService } from './comentarios.service';
import { User, UserService } from '../../shared';
import { Visibilidade, VisibilidadeService } from '../visibilidade';
import { Modulos, ModulosService } from '../modulos';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-comentarios-dialog',
    templateUrl: './comentarios-dialog.component.html'
})
export class ComentariosDialogComponent implements OnInit {

    comentarios: Comentarios;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    visibilidades: Visibilidade[];

    modulos: Modulos[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private comentariosService: ComentariosService,
        private userService: UserService,
        private visibilidadeService: VisibilidadeService,
        private modulosService: ModulosService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.visibilidadeService.query()
            .subscribe((res: ResponseWrapper) => { this.visibilidades = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.modulosService.query()
            .subscribe((res: ResponseWrapper) => { this.modulos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, comentarios, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                comentarios[field] = base64Data;
                comentarios[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.comentarios.id !== undefined) {
            this.subscribeToSaveResponse(
                this.comentariosService.update(this.comentarios));
        } else {
            this.subscribeToSaveResponse(
                this.comentariosService.create(this.comentarios));
        }
    }

    private subscribeToSaveResponse(result: Observable<Comentarios>) {
        result.subscribe((res: Comentarios) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Comentarios) {
        this.eventManager.broadcast({ name: 'comentariosListModification', content: 'OK'});
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

    trackUserById(index: number, item: User) {
        return item.id;
    }

    trackVisibilidadeById(index: number, item: Visibilidade) {
        return item.id;
    }

    trackModulosById(index: number, item: Modulos) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-comentarios-popup',
    template: ''
})
export class ComentariosPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private comentariosPopupService: ComentariosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.comentariosPopupService
                    .open(ComentariosDialogComponent, params['id']);
            } else {
                this.modalRef = this.comentariosPopupService
                    .open(ComentariosDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
