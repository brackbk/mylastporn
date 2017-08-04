import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Denuncias } from './denuncias.model';
import { DenunciasService } from './denuncias.service';

@Injectable()
export class DenunciasPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private denunciasService: DenunciasService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.denunciasService.find(id).subscribe((denuncias) => {
                this.denunciasModalRef(component, denuncias);
            });
        } else {
            return this.denunciasModalRef(component, new Denuncias());
        }
    }

    denunciasModalRef(component: Component, denuncias: Denuncias): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.denuncias = denuncias;
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
