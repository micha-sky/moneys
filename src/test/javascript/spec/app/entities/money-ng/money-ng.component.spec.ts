/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { Headers } from '@angular/http';

import { MoneysTestModule } from '../../../test.module';
import { MoneyNgComponent } from '../../../../../../main/webapp/app/entities/money-ng/money-ng.component';
import { MoneyNgService } from '../../../../../../main/webapp/app/entities/money-ng/money-ng.service';
import { MoneyNg } from '../../../../../../main/webapp/app/entities/money-ng/money-ng.model';

describe('Component Tests', () => {

    describe('MoneyNg Management Component', () => {
        let comp: MoneyNgComponent;
        let fixture: ComponentFixture<MoneyNgComponent>;
        let service: MoneyNgService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [MoneysTestModule],
                declarations: [MoneyNgComponent],
                providers: [
                    MoneyNgService
                ]
            })
            .overrideTemplate(MoneyNgComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(MoneyNgComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoneyNgService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new MoneyNg(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.monies[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
