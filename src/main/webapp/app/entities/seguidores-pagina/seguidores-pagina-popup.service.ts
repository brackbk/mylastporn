import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { SeguidoresPagina } from './seguidores-pagina.model';
import { SeguidoresPaginaService } from './seguidores-pagina.service';

@Injectable()
export class SeguidoresPaginaPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private seguidoresPaginaService: SeguidoresPaginaService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.seguidoresPaginaService.find(id).subscribe((seguidoresPagina) => {
                this.seguidoresPaginaModalRef(component, seguidoresPagina);
            });
        } else {
            return this.seguidoresPaginaModalRef(component, new SeguidoresPagina());
        }
    }

    seguidoresPaginaModalRef(component: Component, seguidoresPagina: SeguidoresPagina): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.seguidoresPagina = seguidoresPagina;
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
