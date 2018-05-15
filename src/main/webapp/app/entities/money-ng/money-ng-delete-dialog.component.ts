import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { MoneyNg } from './money-ng.model';
import { MoneyNgPopupService } from './money-ng-popup.service';
import { MoneyNgService } from './money-ng.service';

@Component({
    selector: 'jhi-money-ng-delete-dialog',
    templateUrl: './money-ng-delete-dialog.component.html'
})
export class MoneyNgDeleteDialogComponent {

    money: MoneyNg;

    constructor(
        private moneyService: MoneyNgService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moneyService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'moneyListModification',
                content: 'Deleted an money'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-money-ng-delete-popup',
    template: ''
})
export class MoneyNgDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private moneyPopupService: MoneyNgPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.moneyPopupService
                .open(MoneyNgDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
