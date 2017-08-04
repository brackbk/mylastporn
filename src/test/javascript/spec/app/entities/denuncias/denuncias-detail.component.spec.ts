/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { DenunciasDetailComponent } from '../../../../../../main/webapp/app/entities/denuncias/denuncias-detail.component';
import { DenunciasService } from '../../../../../../main/webapp/app/entities/denuncias/denuncias.service';
import { Denuncias } from '../../../../../../main/webapp/app/entities/denuncias/denuncias.model';

describe('Component Tests', () => {

    describe('Denuncias Management Detail Component', () => {
        let comp: DenunciasDetailComponent;
        let fixture: ComponentFixture<DenunciasDetailComponent>;
        let service: DenunciasService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [DenunciasDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    DenunciasService,
                    JhiEventManager
                ]
            }).overrideTemplate(DenunciasDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DenunciasDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DenunciasService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Denuncias(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.denuncias).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
