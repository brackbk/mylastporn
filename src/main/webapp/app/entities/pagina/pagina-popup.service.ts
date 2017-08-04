import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Pagina } from './pagina.model';
import { PaginaService } from './pagina.service';

@Injectable()
export class PaginaPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private paginaService: PaginaService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.paginaService.find(id).subscribe((pagina) => {
                pagina.datacriado = this.datePipe
                    .transform(pagina.datacriado, 'yyyy-MM-ddThh:mm');
                this.paginaModalRef(component, pagina);
            });
        } else {
            return this.paginaModalRef(component, new Pagina());
        }
    }

    paginaModalRef(component: Component, pagina: Pagina): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.pagina = pagina;
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
