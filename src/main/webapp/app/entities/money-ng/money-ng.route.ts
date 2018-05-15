import { Routes } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { MoneyNgComponent } from './money-ng.component';
import { MoneyNgDetailComponent } from './money-ng-detail.component';
import { MoneyNgPopupComponent } from './money-ng-dialog.component';
import { MoneyNgDeletePopupComponent } from './money-ng-delete-dialog.component';

export const moneyRoute: Routes = [
    {
        path: 'money-ng',
        component: MoneyNgComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monies'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'money-ng/:id',
        component: MoneyNgDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monies'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const moneyPopupRoute: Routes = [
    {
        path: 'money-ng-new',
        component: MoneyNgPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'money-ng/:id/edit',
        component: MoneyNgPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'money-ng/:id/delete',
        component: MoneyNgDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'Monies'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
