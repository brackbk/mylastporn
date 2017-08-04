import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Favoritos } from './favoritos.model';
import { FavoritosPopupService } from './favoritos-popup.service';
import { FavoritosService } from './favoritos.service';
import { User, UserService } from '../../shared';
import { Visibilidade, VisibilidadeService } from '../visibilidade';
import { Modulos, ModulosService } from '../modulos';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-favoritos-dialog',
    templateUrl: './favoritos-dialog.component.html'
})
export class FavoritosDialogComponent implements OnInit {

    favoritos: Favoritos;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    visibilidades: Visibilidade[];

    modulos: Modulos[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private favoritosService: FavoritosService,
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

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.favoritos.id !== undefined) {
            this.subscribeToSaveResponse(
                this.favoritosService.update(this.favoritos));
        } else {
            this.subscribeToSaveResponse(
                this.favoritosService.create(this.favoritos));
        }
    }

    private subscribeToSaveResponse(result: Observable<Favoritos>) {
        result.subscribe((res: Favoritos) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Favoritos) {
        this.eventManager.broadcast({ name: 'favoritosListModification', content: 'OK'});
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
    selector: 'jhi-favoritos-popup',
    template: ''
})
export class FavoritosPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private favoritosPopupService: FavoritosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.favoritosPopupService
                    .open(FavoritosDialogComponent, params['id']);
            } else {
                this.modalRef = this.favoritosPopupService
                    .open(FavoritosDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
