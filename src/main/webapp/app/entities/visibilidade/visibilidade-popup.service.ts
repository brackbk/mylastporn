import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Visibilidade } from './visibilidade.model';
import { VisibilidadeService } from './visibilidade.service';

@Injectable()
export class VisibilidadePopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private visibilidadeService: VisibilidadeService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.visibilidadeService.find(id).subscribe((visibilidade) => {
                this.visibilidadeModalRef(component, visibilidade);
            });
        } else {
            return this.visibilidadeModalRef(component, new Visibilidade());
        }
    }

    visibilidadeModalRef(component: Component, visibilidade: Visibilidade): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.visibilidade = visibilidade;
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
