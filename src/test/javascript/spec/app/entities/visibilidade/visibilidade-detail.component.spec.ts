/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { VisibilidadeDetailComponent } from '../../../../../../main/webapp/app/entities/visibilidade/visibilidade-detail.component';
import { VisibilidadeService } from '../../../../../../main/webapp/app/entities/visibilidade/visibilidade.service';
import { Visibilidade } from '../../../../../../main/webapp/app/entities/visibilidade/visibilidade.model';

describe('Component Tests', () => {

    describe('Visibilidade Management Detail Component', () => {
        let comp: VisibilidadeDetailComponent;
        let fixture: ComponentFixture<VisibilidadeDetailComponent>;
        let service: VisibilidadeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [VisibilidadeDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    VisibilidadeService,
                    JhiEventManager
                ]
            }).overrideTemplate(VisibilidadeDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(VisibilidadeDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(VisibilidadeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Visibilidade(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.visibilidade).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
