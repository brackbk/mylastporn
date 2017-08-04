import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Comentarios } from './comentarios.model';
import { ComentariosService } from './comentarios.service';

@Injectable()
export class ComentariosPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private comentariosService: ComentariosService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.comentariosService.find(id).subscribe((comentarios) => {
                comentarios.datacriado = this.datePipe
                    .transform(comentarios.datacriado, 'yyyy-MM-ddThh:mm');
                this.comentariosModalRef(component, comentarios);
            });
        } else {
            return this.comentariosModalRef(component, new Comentarios());
        }
    }

    comentariosModalRef(component: Component, comentarios: Comentarios): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.comentarios = comentarios;
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
