import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Likes } from './likes.model';
import { LikesPopupService } from './likes-popup.service';
import { LikesService } from './likes.service';
import { User, UserService } from '../../shared';
import { Modulos, ModulosService } from '../modulos';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-likes-dialog',
    templateUrl: './likes-dialog.component.html'
})
export class LikesDialogComponent implements OnInit {

    likes: Likes;
    authorities: any[];
    isSaving: boolean;

    users: User[];

    modulos: Modulos[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private likesService: LikesService,
        private userService: UserService,
        private modulosService: ModulosService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.userService.query()
            .subscribe((res: ResponseWrapper) => { this.users = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.modulosService.query()
            .subscribe((res: ResponseWrapper) => { this.modulos = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.likes.id !== undefined) {
            this.subscribeToSaveResponse(
                this.likesService.update(this.likes));
        } else {
            this.subscribeToSaveResponse(
                this.likesService.create(this.likes));
        }
    }

    private subscribeToSaveResponse(result: Observable<Likes>) {
        result.subscribe((res: Likes) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Likes) {
        this.eventManager.broadcast({ name: 'likesListModification', content: 'OK'});
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

    trackModulosById(index: number, item: Modulos) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-likes-popup',
    template: ''
})
export class LikesPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private likesPopupService: LikesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.likesPopupService
                    .open(LikesDialogComponent, params['id']);
            } else {
                this.modalRef = this.likesPopupService
                    .open(LikesDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
