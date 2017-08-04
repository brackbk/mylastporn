import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Modulos } from './modulos.model';
import { ModulosPopupService } from './modulos-popup.service';
import { ModulosService } from './modulos.service';

@Component({
    selector: 'jhi-modulos-delete-dialog',
    templateUrl: './modulos-delete-dialog.component.html'
})
export class ModulosDeleteDialogComponent {

    modulos: Modulos;

    constructor(
        private modulosService: ModulosService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.modulosService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'modulosListModification',
                content: 'Deleted an modulos'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-modulos-delete-popup',
    template: ''
})
export class ModulosDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private modulosPopupService: ModulosPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.modulosPopupService
                .open(ModulosDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
