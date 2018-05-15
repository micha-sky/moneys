import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { JhiEventManager } from 'ng-jhipster';

import { MoneyNg } from './money-ng.model';
import { MoneyNgService } from './money-ng.service';

@Component({
    selector: 'jhi-money-ng-detail',
    templateUrl: './money-ng-detail.component.html'
})
export class MoneyNgDetailComponent implements OnInit, OnDestroy {

    money: MoneyNg;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private moneyService: MoneyNgService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInMonies();
    }

    load(id) {
        this.moneyService.find(id).subscribe((money) => {
            this.money = money;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInMonies() {
        this.eventSubscriber = this.eventManager.subscribe(
            'moneyListModification',
            (response) => this.load(this.money.id)
        );
    }
}
