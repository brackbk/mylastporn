import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Denuncias } from './denuncias.model';
import { DenunciasPopupService } from './denuncias-popup.service';
import { DenunciasService } from './denuncias.service';
import { User, UserService } from '../../shared';
import { TipoDenuncia, TipoDenunciaService } from '../tipo-denuncia';
import { Modulos, ModulosService } from '../modulos';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-denuncias-dialog',
    templateUrl: './denuncias-dialog.component.html'
})
export class DenunciasDialogComponent implements OnInit {

    denuncias: Denuncias;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    tipodenuncias: TipoDenuncia[];

    modulos: Modulos[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private denunciasService: DenunciasService,
        private userService: UserService,
        private tipoDenunciaService: TipoDenunciaService,
        private modulosService: ModulosService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.tipoDenunciaService.query()
            .subscribe((res: ResponseWrapper) => { this.tipodenuncias = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.modulosService.query()
            .subscribe((res: ResponseWrapper) => { this.modulos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, denuncias, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                denuncias[field] = base64Data;
                denuncias[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.denuncias.id !== undefined) {
            this.subscribeToSaveResponse(
                this.denunciasService.update(this.denuncias));
        } else {
            this.subscribeToSaveResponse(
                this.denunciasService.create(this.denuncias));
        }
    }

    private subscribeToSaveResponse(result: Observable<Denuncias>) {
        result.subscribe((res: Denuncias) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Denuncias) {
        this.eventManager.broadcast({ name: 'denunciasListModification', content: 'OK'});
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

    trackTipoDenunciaById(index: number, item: TipoDenuncia) {
        return item.id;
    }

    trackModulosById(index: number, item: Modulos) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-denuncias-popup',
    template: ''
})
export class DenunciasPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private denunciasPopupService: DenunciasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.denunciasPopupService
                    .open(DenunciasDialogComponent, params['id']);
            } else {
                this.modalRef = this.denunciasPopupService
                    .open(DenunciasDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
