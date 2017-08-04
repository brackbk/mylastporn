/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { MylastpornTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { FavoritosDetailComponent } from '../../../../../../main/webapp/app/entities/favoritos/favoritos-detail.component';
import { FavoritosService } from '../../../../../../main/webapp/app/entities/favoritos/favoritos.service';
import { Favoritos } from '../../../../../../main/webapp/app/entities/favoritos/favoritos.model';

describe('Component Tests', () => {

    describe('Favoritos Management Detail Component', () => {
        let comp: FavoritosDetailComponent;
        let fixture: ComponentFixture<FavoritosDetailComponent>;
        let service: FavoritosService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MylastpornTestModule],
                declarations: [FavoritosDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    FavoritosService,
                    JhiEventManager
                ]
            }).overrideTemplate(FavoritosDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FavoritosDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FavoritosService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Favoritos(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.favoritos).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
