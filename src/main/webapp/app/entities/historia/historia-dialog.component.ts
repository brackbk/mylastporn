import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { Historia } from './historia.model';
import { HistoriaPopupService } from './historia-popup.service';
import { HistoriaService } from './historia.service';
import { User, UserService } from '../../shared';
import { Visibilidade, VisibilidadeService } from '../visibilidade';
import { Tipo, TipoService } from '../tipo';
import { Tags, TagsService } from '../tags';
import { Pagina, PaginaService } from '../pagina';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-historia-dialog',
    templateUrl: './historia-dialog.component.html'
})
export class HistoriaDialogComponent implements OnInit {

    historia: Historia;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    visibilidades: Visibilidade[];

    tipos: Tipo[];

    tags: Tags[];

    paginas: Pagina[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private alertService: JhiAlertService,
        private historiaService: HistoriaService,
        private userService: UserService,
        private visibilidadeService: VisibilidadeService,
        private tipoService: TipoService,
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
        this.visibilidadeService.query()
            .subscribe((res: ResponseWrapper) => { this.visibilidades = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.tipoService.query()
            .subscribe((res: ResponseWrapper) => { this.tipos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
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

    setFileData(event, historia, field, isImage) {
        if (event && event.target.files && event.target.files[0]) {
            const file = event.target.files[0];
            if (isImage && !/^image\//.test(file.type)) {
                return;
            }
            this.dataUtils.toBase64(file, (base64Data) => {
                historia[field] = base64Data;
                historia[`${field}ContentType`] = file.type;
            });
        }
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.historia.id !== undefined) {
            this.subscribeToSaveResponse(
                this.historiaService.update(this.historia));
        } else {
            this.subscribeToSaveResponse(
                this.historiaService.create(this.historia));
        }
    }

    private subscribeToSaveResponse(result: Observable<Historia>) {
        result.subscribe((res: Historia) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Historia) {
        this.eventManager.broadcast({ name: 'historiaListModification', content: 'OK'});
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
    selector: 'jhi-historia-popup',
    template: ''
})
export class HistoriaPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historiaPopupService: HistoriaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.historiaPopupService
                    .open(HistoriaDialogComponent, params['id']);
            } else {
                this.modalRef = this.historiaPopupService
                    .open(HistoriaDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
