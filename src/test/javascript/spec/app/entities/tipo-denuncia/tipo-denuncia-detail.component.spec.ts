/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TipoDenunciaDetailComponent } from '../../../../../../main/webapp/app/entities/tipo-denuncia/tipo-denuncia-detail.component';
import { TipoDenunciaService } from '../../../../../../main/webapp/app/entities/tipo-denuncia/tipo-denuncia.service';
import { TipoDenuncia } from '../../../../../../main/webapp/app/entities/tipo-denuncia/tipo-denuncia.model';

describe('Component Tests', () => {

    describe('TipoDenuncia Management Detail Component', () => {
        let comp: TipoDenunciaDetailComponent;
        let fixture: ComponentFixture<TipoDenunciaDetailComponent>;
        let service: TipoDenunciaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [TipoDenunciaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TipoDenunciaService,
                    JhiEventManager
                ]
            }).overrideTemplate(TipoDenunciaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoDenunciaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoDenunciaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new TipoDenuncia(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tipoDenuncia).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
