/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AmigosDetailComponent } from '../../../../../../main/webapp/app/entities/amigos/amigos-detail.component';
import { AmigosService } from '../../../../../../main/webapp/app/entities/amigos/amigos.service';
import { Amigos } from '../../../../../../main/webapp/app/entities/amigos/amigos.model';

describe('Component Tests', () => {

    describe('Amigos Management Detail Component', () => {
        let comp: AmigosDetailComponent;
        let fixture: ComponentFixture<AmigosDetailComponent>;
        let service: AmigosService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [AmigosDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AmigosService,
                    JhiEventManager
                ]
            }).overrideTemplate(AmigosDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AmigosDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AmigosService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Amigos(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.amigos).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
