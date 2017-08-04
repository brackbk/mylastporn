/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FotoDetailComponent } from '../../../../../../main/webapp/app/entities/foto/foto-detail.component';
import { FotoService } from '../../../../../../main/webapp/app/entities/foto/foto.service';
import { Foto } from '../../../../../../main/webapp/app/entities/foto/foto.model';

describe('Component Tests', () => {

    describe('Foto Management Detail Component', () => {
        let comp: FotoDetailComponent;
        let fixture: ComponentFixture<FotoDetailComponent>;
        let service: FotoService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [FotoDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FotoService,
                    JhiEventManager
                ]
            }).overrideTemplate(FotoDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FotoDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FotoService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Foto(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.foto).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
