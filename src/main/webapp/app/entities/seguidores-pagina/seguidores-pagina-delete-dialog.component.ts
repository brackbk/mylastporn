import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { SeguidoresPagina } from './seguidores-pagina.model';
import { SeguidoresPaginaPopupService } from './seguidores-pagina-popup.service';
import { SeguidoresPaginaService } from './seguidores-pagina.service';

@Component({
    selector: 'jhi-seguidores-pagina-delete-dialog',
    templateUrl: './seguidores-pagina-delete-dialog.component.html'
})
export class SeguidoresPaginaDeleteDialogComponent {

    seguidoresPagina: SeguidoresPagina;

    constructor(
        private seguidoresPaginaService: SeguidoresPaginaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.seguidoresPaginaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'seguidoresPaginaListModification',
                content: 'Deleted an seguidoresPagina'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-seguidores-pagina-delete-popup',
    template: ''
})
export class SeguidoresPaginaDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private seguidoresPaginaPopupService: SeguidoresPaginaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.seguidoresPaginaPopupService
                .open(SeguidoresPaginaDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
