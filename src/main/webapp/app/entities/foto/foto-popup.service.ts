import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Foto } from './foto.model';
import { FotoService } from './foto.service';

@Injectable()
export class FotoPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private fotoService: FotoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.fotoService.find(id).subscribe((foto) => {
                foto.datacriado = this.datePipe
                    .transform(foto.datacriado, 'yyyy-MM-ddThh:mm');
                this.fotoModalRef(component, foto);
            });
        } else {
            return this.fotoModalRef(component, new Foto());
        }
    }

    fotoModalRef(component: Component, foto: Foto): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.foto = foto;
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
