import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Observable';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MoneyNg } from './money-ng.model';
import { MoneyNgPopupService } from './money-ng-popup.service';
import { MoneyNgService } from './money-ng.service';

@Component({
    selector: 'jhi-money-ng-dialog',
    templateUrl: './money-ng-dialog.component.html'
})
export class MoneyNgDialogComponent implements OnInit {

    money: MoneyNg;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private moneyService: MoneyNgService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.money.id !== undefined) {
            this.subscribeToSaveResponse(
                this.moneyService.update(this.money));
        } else {
            this.subscribeToSaveResponse(
                this.moneyService.create(this.money));
        }
    }

    private subscribeToSaveResponse(result: Observable<MoneyNg>) {
        result.subscribe((res: MoneyNg) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: MoneyNg) {
        this.eventManager.broadcast({ name: 'moneyListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }
}

@Component({
    selector: 'jhi-money-ng-popup',
    template: ''
})
export class MoneyNgPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moneyPopupService: MoneyNgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.moneyPopupService
                    .open(MoneyNgDialogComponent as Component, params['id']);
            } else {
                this.moneyPopupService
                    .open(MoneyNgDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
