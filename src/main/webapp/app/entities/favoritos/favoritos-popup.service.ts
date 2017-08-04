import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { Favoritos } from './favoritos.model';
import { FavoritosService } from './favoritos.service';

@Injectable()
export class FavoritosPopupService {
    private isOpen = false;
    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private favoritosService: FavoritosService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.favoritosService.find(id).subscribe((favoritos) => {
                favoritos.datacriado = this.datePipe
                    .transform(favoritos.datacriado, 'yyyy-MM-ddThh:mm');
                this.favoritosModalRef(component, favoritos);
            });
        } else {
            return this.favoritosModalRef(component, new Favoritos());
        }
    }

    favoritosModalRef(component: Component, favoritos: Favoritos): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.favoritos = favoritos;
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
