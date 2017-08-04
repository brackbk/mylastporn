import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Historia } from './historia.model';
import { HistoriaService } from './historia.service';

@Injectable()
export class HistoriaPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private historiaService: HistoriaService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.historiaService.find(id).subscribe((historia) => {
                historia.datacriado = this.datePipe
                    .transform(historia.datacriado, 'yyyy-MM-ddThh:mm');
                this.historiaModalRef(component, historia);
            });
        } else {
            return this.historiaModalRef(component, new Historia());
        }
    }

    historiaModalRef(component: Component, historia: Historia): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.historia = historia;
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
