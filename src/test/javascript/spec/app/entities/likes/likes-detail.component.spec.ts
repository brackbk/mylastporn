/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LikesDetailComponent } from '../../../../../../main/webapp/app/entities/likes/likes-detail.component';
import { LikesService } from '../../../../../../main/webapp/app/entities/likes/likes.service';
import { Likes } from '../../../../../../main/webapp/app/entities/likes/likes.model';

describe('Component Tests', () => {

    describe('Likes Management Detail Component', () => {
        let comp: LikesDetailComponent;
        let fixture: ComponentFixture<LikesDetailComponent>;
        let service: LikesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [LikesDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LikesService,
                    JhiEventManager
                ]
            }).overrideTemplate(LikesDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LikesDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LikesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Likes(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.likes).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
