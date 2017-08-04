import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Denuncias } from './denuncias.model';
import { DenunciasPopupService } from './denuncias-popup.service';
import { DenunciasService } from './denuncias.service';

@Component({
    selector: 'jhi-denuncias-delete-dialog',
    templateUrl: './denuncias-delete-dialog.component.html'
})
export class DenunciasDeleteDialogComponent {

    denuncias: Denuncias;

    constructor(
        private denunciasService: DenunciasService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.denunciasService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'denunciasListModification',
                content: 'Deleted an denuncias'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-denuncias-delete-popup',
    template: ''
})
export class DenunciasDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private denunciasPopupService: DenunciasPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.denunciasPopupService
                .open(DenunciasDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
