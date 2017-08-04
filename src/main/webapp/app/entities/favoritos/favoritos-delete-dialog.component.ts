import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Favoritos } from './favoritos.model';
import { FavoritosPopupService } from './favoritos-popup.service';
import { FavoritosService } from './favoritos.service';

@Component({
    selector: 'jhi-favoritos-delete-dialog',
    templateUrl: './favoritos-delete-dialog.component.html'
})
export class FavoritosDeleteDialogComponent {

    favoritos: Favoritos;

    constructor(
        private favoritosService: FavoritosService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.favoritosService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'favoritosListModification',
                content: 'Deleted an favoritos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-favoritos-delete-popup',
    template: ''
})
export class FavoritosDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private favoritosPopupService: FavoritosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.favoritosPopupService
                .open(FavoritosDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
