/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { MoneysTestModule } from '../../../test.module';
import { MoneyNgDeleteDialogComponent } from '../../../../../../main/webapp/app/entities/money-ng/money-ng-delete-dialog.component';
import { MoneyNgService } from '../../../../../../main/webapp/app/entities/money-ng/money-ng.service';

describe('Component Tests', () => {

    describe('MoneyNg Management Delete Component', () => {
        let comp: MoneyNgDeleteDialogComponent;
        let fixture: ComponentFixture<MoneyNgDeleteDialogComponent>;
        let service: MoneyNgService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MoneysTestModule],
                declarations: [MoneyNgDeleteDialogComponent],
                providers: [
                    MoneyNgService
                ]
            })
            .overrideTemplate(MoneyNgDeleteDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MoneyNgDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyNgService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        spyOn(service, 'delete').and.returnValue(Observable.of({}));

                        // WHEN
                        comp.confirmDelete(123);
                        tick();

                        // THEN
                        expect(service.delete).toHaveBeenCalledWith(123);
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
