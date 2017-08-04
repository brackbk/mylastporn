/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ModulosDetailComponent } from '../../../../../../main/webapp/app/entities/modulos/modulos-detail.component';
import { ModulosService } from '../../../../../../main/webapp/app/entities/modulos/modulos.service';
import { Modulos } from '../../../../../../main/webapp/app/entities/modulos/modulos.model';

describe('Component Tests', () => {

    describe('Modulos Management Detail Component', () => {
        let comp: ModulosDetailComponent;
        let fixture: ComponentFixture<ModulosDetailComponent>;
        let service: ModulosService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [ModulosDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ModulosService,
                    JhiEventManager
                ]
            }).overrideTemplate(ModulosDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ModulosDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ModulosService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Modulos(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.modulos).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
