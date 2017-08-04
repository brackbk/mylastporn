/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PaginaDetailComponent } from '../../../../../../main/webapp/app/entities/pagina/pagina-detail.component';
import { PaginaService } from '../../../../../../main/webapp/app/entities/pagina/pagina.service';
import { Pagina } from '../../../../../../main/webapp/app/entities/pagina/pagina.model';

describe('Component Tests', () => {

    describe('Pagina Management Detail Component', () => {
        let comp: PaginaDetailComponent;
        let fixture: ComponentFixture<PaginaDetailComponent>;
        let service: PaginaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [PaginaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PaginaService,
                    JhiEventManager
                ]
            }).overrideTemplate(PaginaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PaginaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PaginaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Pagina(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.pagina).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
