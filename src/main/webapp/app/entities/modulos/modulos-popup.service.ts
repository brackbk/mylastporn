import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Modulos } from './modulos.model';
import { ModulosService } from './modulos.service';

@Injectable()
export class ModulosPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private modulosService: ModulosService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.modulosService.find(id).subscribe((modulos) => {
                modulos.datacriado = this.datePipe
                    .transform(modulos.datacriado, 'yyyy-MM-ddThh:mm');
                this.modulosModalRef(component, modulos);
            });
        } else {
            return this.modulosModalRef(component, new Modulos());
        }
    }

    modulosModalRef(component: Component, modulos: Modulos): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.modulos = modulos;
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
