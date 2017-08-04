import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Foto } from './foto.model';
import { FotoPopupService } from './foto-popup.service';
import { FotoService } from './foto.service';
import { User, UserService } from '../../shared';
import { Tipo, TipoService } from '../tipo';
import { Visibilidade, VisibilidadeService } from '../visibilidade';
import { Tags, TagsService } from '../tags';
import { Pagina, PaginaService } from '../pagina';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-foto-dialog',
    templateUrl: './foto-dialog.component.html'
})
export class FotoDialogComponent implements OnInit {

    foto: Foto;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    tipos: Tipo[];

    visibilidades: Visibilidade[];

    tags: Tags[];

    paginas: Pagina[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private fotoService: FotoService,
        private userService: UserService,
        private tipoService: TipoService,
        private visibilidadeService: VisibilidadeService,
        private tagsService: TagsService,
        private paginaService: PaginaService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.tipoService.query()
            .subscribe((res: ResponseWrapper) => { this.tipos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.visibilidadeService.query()
            .subscribe((res: ResponseWrapper) => { this.visibilidades = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.tagsService.query()
            .subscribe((res: ResponseWrapper) => { this.tags = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.paginaService.query()
            .subscribe((res: ResponseWrapper) => { this.paginas = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, foto, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                foto[field] = base64Data;
                foto[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.foto.id !== undefined) {
            this.subscribeToSaveResponse(
                this.fotoService.update(this.foto));
        } else {
            this.subscribeToSaveResponse(
                this.fotoService.create(this.foto));
        }
    }

    private subscribeToSaveResponse(result: Observable<Foto>) {
        result.subscribe((res: Foto) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Foto) {
        this.eventManager.broadcast({ name: 'fotoListModification', content: 'OK'});
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

    trackTipoById(index: number, item: Tipo) {
        return item.id;
    }

    trackVisibilidadeById(index: number, item: Visibilidade) {
        return item.id;
    }

    trackTagsById(index: number, item: Tags) {
        return item.id;
    }

    trackPaginaById(index: number, item: Pagina) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}

@Component({
    selector: 'jhi-foto-popup',
    template: ''
})
export class FotoPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private fotoPopupService: FotoPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.fotoPopupService
                    .open(FotoDialogComponent, params['id']);
            } else {
                this.modalRef = this.fotoPopupService
                    .open(FotoDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
