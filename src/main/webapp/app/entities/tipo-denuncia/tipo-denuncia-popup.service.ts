import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { TipoDenuncia } from './tipo-denuncia.model';
import { TipoDenunciaService } from './tipo-denuncia.service';

@Injectable()
export class TipoDenunciaPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private tipoDenunciaService: TipoDenunciaService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.tipoDenunciaService.find(id).subscribe((tipoDenuncia) => {
                this.tipoDenunciaModalRef(component, tipoDenuncia);
            });
        } else {
            return this.tipoDenunciaModalRef(component, new TipoDenuncia());
        }
    }

    tipoDenunciaModalRef(component: Component, tipoDenuncia: TipoDenuncia): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tipoDenuncia = tipoDenuncia;
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
