/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { SeguidoresPaginaDetailComponent } from '../../../../../../main/webapp/app/entities/seguidores-pagina/seguidores-pagina-detail.component';
import { SeguidoresPaginaService } from '../../../../../../main/webapp/app/entities/seguidores-pagina/seguidores-pagina.service';
import { SeguidoresPagina } from '../../../../../../main/webapp/app/entities/seguidores-pagina/seguidores-pagina.model';

describe('Component Tests', () => {

    describe('SeguidoresPagina Management Detail Component', () => {
        let comp: SeguidoresPaginaDetailComponent;
        let fixture: ComponentFixture<SeguidoresPaginaDetailComponent>;
        let service: SeguidoresPaginaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [SeguidoresPaginaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    SeguidoresPaginaService,
                    JhiEventManager
                ]
            }).overrideTemplate(SeguidoresPaginaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SeguidoresPaginaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SeguidoresPaginaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new SeguidoresPagina(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.seguidoresPagina).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
