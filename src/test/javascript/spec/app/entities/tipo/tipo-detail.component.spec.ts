/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { TipoDetailComponent } from '../../../../../../main/webapp/app/entities/tipo/tipo-detail.component';
import { TipoService } from '../../../../../../main/webapp/app/entities/tipo/tipo.service';
import { Tipo } from '../../../../../../main/webapp/app/entities/tipo/tipo.model';

describe('Component Tests', () => {

    describe('Tipo Management Detail Component', () => {
        let comp: TipoDetailComponent;
        let fixture: ComponentFixture<TipoDetailComponent>;
        let service: TipoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [TipoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    TipoService,
                    JhiEventManager
                ]
            }).overrideTemplate(TipoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(TipoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TipoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Tipo(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.tipo).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
