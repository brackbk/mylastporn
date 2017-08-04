import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Amigos } from './amigos.model';
import { AmigosPopupService } from './amigos-popup.service';
import { AmigosService } from './amigos.service';

@Component({
    selector: 'jhi-amigos-delete-dialog',
    templateUrl: './amigos-delete-dialog.component.html'
})
export class AmigosDeleteDialogComponent {

    amigos: Amigos;

    constructor(
        private amigosService: AmigosService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.amigosService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'amigosListModification',
                content: 'Deleted an amigos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-amigos-delete-popup',
    template: ''
})
export class AmigosDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private amigosPopupService: AmigosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.amigosPopupService
                .open(AmigosDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
