/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { InfiTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EntidadFinancieraDetailComponent } from '../../../../../../main/webapp/app/entities/entidad-financiera/entidad-financiera-detail.component';
import { EntidadFinancieraService } from '../../../../../../main/webapp/app/entities/entidad-financiera/entidad-financiera.service';
import { EntidadFinanciera } from '../../../../../../main/webapp/app/entities/entidad-financiera/entidad-financiera.model';

describe('Component Tests', () => {

    describe('EntidadFinanciera Management Detail Component', () => {
        let comp: EntidadFinancieraDetailComponent;
        let fixture: ComponentFixture<EntidadFinancieraDetailComponent>;
        let service: EntidadFinancieraService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [InfiTestModule],
                declarations: [EntidadFinancieraDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EntidadFinancieraService,
                    JhiEventManager
                ]
            }).overrideTemplate(EntidadFinancieraDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EntidadFinancieraDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EntidadFinancieraService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EntidadFinanciera(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.entidadFinanciera).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
