/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MoneysTestModule } from '../../../test.module';
import { MoneyNgDialogComponent } from '../../../../../../main/webapp/app/entities/money-ng/money-ng-dialog.component';
import { MoneyNgService } from '../../../../../../main/webapp/app/entities/money-ng/money-ng.service';
import { MoneyNg } from '../../../../../../main/webapp/app/entities/money-ng/money-ng.model';

describe('Component Tests', () => {

    describe('MoneyNg Management Dialog Component', () => {
        let comp: MoneyNgDialogComponent;
        let fixture: ComponentFixture<MoneyNgDialogComponent>;
        let service: MoneyNgService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MoneysTestModule],
                declarations: [MoneyNgDialogComponent],
                providers: [
                    MoneyNgService
                ]
            })
            .overrideTemplate(MoneyNgDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MoneyNgDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyNgService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MoneyNg(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(entity));
                        comp.money = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'moneyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new MoneyNg();
                        spyOn(service, 'create').and.returnValue(Observable.of(entity));
                        comp.money = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'moneyListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
