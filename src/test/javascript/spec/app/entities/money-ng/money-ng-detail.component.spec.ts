/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';

import { MoneysTestModule } from '../../../test.module';
import { MoneyNgDetailComponent } from '../../../../../../main/webapp/app/entities/money-ng/money-ng-detail.component';
import { MoneyNgService } from '../../../../../../main/webapp/app/entities/money-ng/money-ng.service';
import { MoneyNg } from '../../../../../../main/webapp/app/entities/money-ng/money-ng.model';

describe('Component Tests', () => {

    describe('MoneyNg Management Detail Component', () => {
        let comp: MoneyNgDetailComponent;
        let fixture: ComponentFixture<MoneyNgDetailComponent>;
        let service: MoneyNgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MoneysTestModule],
                declarations: [MoneyNgDetailComponent],
                providers: [
                    MoneyNgService
                ]
            })
            .overrideTemplate(MoneyNgDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MoneyNgDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyNgService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new MoneyNg(123)));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.money).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
