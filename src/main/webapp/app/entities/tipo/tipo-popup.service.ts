import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Tipo } from './tipo.model';
import { TipoService } from './tipo.service';

@Injectable()
export class TipoPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private tipoService: TipoService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.tipoService.find(id).subscribe((tipo) => {
                tipo.datacriado = this.datePipe
                    .transform(tipo.datacriado, 'yyyy-MM-ddThh:mm');
                this.tipoModalRef(component, tipo);
            });
        } else {
            return this.tipoModalRef(component, new Tipo());
        }
    }

    tipoModalRef(component: Component, tipo: Tipo): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.tipo = tipo;
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
