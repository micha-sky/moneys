import { Component, OnInit, OnDestroy } from '@angular/core';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { MoneyNg } from './money-ng.model';
import { MoneyNgService } from './money-ng.service';
import { Principal, ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-money-ng',
    templateUrl: './money-ng.component.html'
})
export class MoneyNgComponent implements OnInit, OnDestroy {
monies: MoneyNg[];
    currentAccount: any;
    eventSubscriber: Subscription;

    constructor(
        private moneyService: MoneyNgService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private principal: Principal
    ) {
    }

    loadAll() {
        this.moneyService.query().subscribe(
            (res: ResponseWrapper) => {
                this.monies = res.json;
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInMonies();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: MoneyNg) {
        return item.id;
    }
    registerChangeInMonies() {
        this.eventSubscriber = this.eventManager.subscribe('moneyListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
