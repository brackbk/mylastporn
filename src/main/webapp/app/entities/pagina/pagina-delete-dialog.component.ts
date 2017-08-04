import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Pagina } from './pagina.model';
import { PaginaPopupService } from './pagina-popup.service';
import { PaginaService } from './pagina.service';

@Component({
    selector: 'jhi-pagina-delete-dialog',
    templateUrl: './pagina-delete-dialog.component.html'
})
export class PaginaDeleteDialogComponent {

    pagina: Pagina;

    constructor(
        private paginaService: PaginaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.paginaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'paginaListModification',
                content: 'Deleted an pagina'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-pagina-delete-popup',
    template: ''
})
export class PaginaDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private paginaPopupService: PaginaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.paginaPopupService
                .open(PaginaDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
