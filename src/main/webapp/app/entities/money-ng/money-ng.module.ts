import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { MoneysSharedModule } from '../../shared';
import {
    MoneyNgService,
    MoneyNgPopupService,
    MoneyNgComponent,
    MoneyNgDetailComponent,
    MoneyNgDialogComponent,
    MoneyNgPopupComponent,
    MoneyNgDeletePopupComponent,
    MoneyNgDeleteDialogComponent,
    moneyRoute,
    moneyPopupRoute,
} from './';

const ENTITY_STATES = [
    ...moneyRoute,
    ...moneyPopupRoute,
];

@NgModule({
    imports: [
        MoneysSharedModule,
        RouterModule.forChild(ENTITY_STATES)
    ],
    declarations: [
        MoneyNgComponent,
        MoneyNgDetailComponent,
        MoneyNgDialogComponent,
        MoneyNgDeleteDialogComponent,
        MoneyNgPopupComponent,
        MoneyNgDeletePopupComponent,
    ],
    entryComponents: [
        MoneyNgComponent,
        MoneyNgDialogComponent,
        MoneyNgPopupComponent,
        MoneyNgDeleteDialogComponent,
        MoneyNgDeletePopupComponent,
    ],
    providers: [
        MoneyNgService,
        MoneyNgPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class MoneysMoneyNgModule {}
