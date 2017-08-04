import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { Likes } from './likes.model';
import { LikesPopupService } from './likes-popup.service';
import { LikesService } from './likes.service';

@Component({
    selector: 'jhi-likes-delete-dialog',
    templateUrl: './likes-delete-dialog.component.html'
})
export class LikesDeleteDialogComponent {

    likes: Likes;

    constructor(
        private likesService: LikesService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.likesService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'likesListModification',
                content: 'Deleted an likes'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-likes-delete-popup',
    template: ''
})
export class LikesDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private likesPopupService: LikesPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.likesPopupService
                .open(LikesDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
