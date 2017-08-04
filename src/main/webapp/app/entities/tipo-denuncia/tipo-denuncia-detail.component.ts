import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { TipoDenuncia } from './tipo-denuncia.model';
import { TipoDenunciaService } from './tipo-denuncia.service';

@Component({
    selector: 'jhi-tipo-denuncia-detail',
    templateUrl: './tipo-denuncia-detail.component.html'
})
export class TipoDenunciaDetailComponent implements OnInit, OnDestroy {

    tipoDenuncia: TipoDenuncia;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private tipoDenunciaService: TipoDenunciaService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInTipoDenuncias();
    }

    load(id) {
        this.tipoDenunciaService.find(id).subscribe((tipoDenuncia) => {
            this.tipoDenuncia = tipoDenuncia;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInTipoDenuncias() {
        this.eventSubscriber = this.eventManager.subscribe(
            'tipoDenunciaListModification',
            (response) => this.load(this.tipoDenuncia.id)
        );
    }
}
