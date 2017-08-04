import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Pagina } from './pagina.model';
import { PaginaPopupService } from './pagina-popup.service';
import { PaginaService } from './pagina.service';
import { User, UserService } from '../../shared';
import { Visibilidade, VisibilidadeService } from '../visibilidade';
import { Tipo, TipoService } from '../tipo';
import { Tags, TagsService } from '../tags';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-pagina-dialog',
    templateUrl: './pagina-dialog.component.html'
})
export class PaginaDialogComponent implements OnInit {

    pagina: Pagina;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    visibilidades: Visibilidade[];

    tipos: Tipo[];

    tags: Tags[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private paginaService: PaginaService,
        private userService: UserService,
        private visibilidadeService: VisibilidadeService,
        private tipoService: TipoService,
        private tagsService: TagsService,
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
        this.tipoService.query()
            .subscribe((res: ResponseWrapper) => { this.tipos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.tagsService.query()
            .subscribe((res: ResponseWrapper) => { this.tags = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, pagina, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                pagina[field] = base64Data;
                pagina[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.pagina.id !== undefined) {
            this.subscribeToSaveResponse(
                this.paginaService.update(this.pagina));
        } else {
            this.subscribeToSaveResponse(
                this.paginaService.create(this.pagina));
        }
    }

    private subscribeToSaveResponse(result: Observable<Pagina>) {
        result.subscribe((res: Pagina) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Pagina) {
        this.eventManager.broadcast({ name: 'paginaListModification', content: 'OK'});
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

    trackTipoById(index: number, item: Tipo) {
        return item.id;
    }

    trackTagsById(index: number, item: Tags) {
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
    selector: 'jhi-pagina-popup',
    template: ''
})
export class PaginaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paginaPopupService: PaginaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.paginaPopupService
                    .open(PaginaDialogComponent, params['id']);
            } else {
                this.modalRef = this.paginaPopupService
                    .open(PaginaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
