import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Likes } from './likes.model';
import { LikesService } from './likes.service';

@Injectable()
export class LikesPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private likesService: LikesService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.likesService.find(id).subscribe((likes) => {
                likes.datacriado = this.datePipe
                    .transform(likes.datacriado, 'yyyy-MM-ddThh:mm');
                this.likesModalRef(component, likes);
            });
        } else {
            return this.likesModalRef(component, new Likes());
        }
    }

    likesModalRef(component: Component, likes: Likes): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.likes = likes;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
