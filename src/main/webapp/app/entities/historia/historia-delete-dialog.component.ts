import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Historia } from './historia.model';
import { HistoriaPopupService } from './historia-popup.service';
import { HistoriaService } from './historia.service';

@Component({
    selector: 'jhi-historia-delete-dialog',
    templateUrl: './historia-delete-dialog.component.html'
})
export class HistoriaDeleteDialogComponent {

    historia: Historia;

    constructor(
        private historiaService: HistoriaService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.historiaService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'historiaListModification',
                content: 'Deleted an historia'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-historia-delete-popup',
    template: ''
})
export class HistoriaDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private historiaPopupService: HistoriaPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.historiaPopupService
                .open(HistoriaDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
