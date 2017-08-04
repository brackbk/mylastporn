/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { HistoriaDetailComponent } from '../../../../../../main/webapp/app/entities/historia/historia-detail.component';
import { HistoriaService } from '../../../../../../main/webapp/app/entities/historia/historia.service';
import { Historia } from '../../../../../../main/webapp/app/entities/historia/historia.model';

describe('Component Tests', () => {

    describe('Historia Management Detail Component', () => {
        let comp: HistoriaDetailComponent;
        let fixture: ComponentFixture<HistoriaDetailComponent>;
        let service: HistoriaService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [HistoriaDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    HistoriaService,
                    JhiEventManager
                ]
            }).overrideTemplate(HistoriaDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(HistoriaDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(HistoriaService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Historia(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.historia).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
