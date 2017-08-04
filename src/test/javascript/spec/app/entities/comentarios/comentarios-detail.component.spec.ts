/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ComentariosDetailComponent } from '../../../../../../main/webapp/app/entities/comentarios/comentarios-detail.component';
import { ComentariosService } from '../../../../../../main/webapp/app/entities/comentarios/comentarios.service';
import { Comentarios } from '../../../../../../main/webapp/app/entities/comentarios/comentarios.model';

describe('Component Tests', () => {

    describe('Comentarios Management Detail Component', () => {
        let comp: ComentariosDetailComponent;
        let fixture: ComponentFixture<ComentariosDetailComponent>;
        let service: ComentariosService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [ComentariosDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ComentariosService,
                    JhiEventManager
                ]
            }).overrideTemplate(ComentariosDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ComentariosDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ComentariosService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Comentarios(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.comentarios).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
