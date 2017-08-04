import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Visibilidade } from './visibilidade.model';
import { VisibilidadePopupService } from './visibilidade-popup.service';
import { VisibilidadeService } from './visibilidade.service';

@Component({
    selector: 'jhi-visibilidade-delete-dialog',
    templateUrl: './visibilidade-delete-dialog.component.html'
})
export class VisibilidadeDeleteDialogComponent {

    visibilidade: Visibilidade;

    constructor(
        private visibilidadeService: VisibilidadeService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.visibilidadeService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'visibilidadeListModification',
                content: 'Deleted an visibilidade'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-visibilidade-delete-popup',
    template: ''
})
export class VisibilidadeDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private visibilidadePopupService: VisibilidadePopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.visibilidadePopupService
                .open(VisibilidadeDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
