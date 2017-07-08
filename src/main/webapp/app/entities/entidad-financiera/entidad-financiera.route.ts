import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EntidadFinancieraComponent } from './entidad-financiera.component';
import { EntidadFinancieraDetailComponent } from './entidad-financiera-detail.component';
import { EntidadFinancieraPopupComponent } from './entidad-financiera-dialog.component';
import { EntidadFinancieraDeletePopupComponent } from './entidad-financiera-delete-dialog.component';

import { Principal } from '../../shared';

export const entidadFinancieraRoute: Routes = [
    {
        path: 'entidad-financiera',
        component: EntidadFinancieraComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infiApp.entidadFinanciera.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'entidad-financiera/:id',
        component: EntidadFinancieraDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infiApp.entidadFinanciera.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const entidadFinancieraPopupRoute: Routes = [
    {
        path: 'entidad-financiera-new',
        component: EntidadFinancieraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infiApp.entidadFinanciera.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entidad-financiera/:id/edit',
        component: EntidadFinancieraPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infiApp.entidadFinanciera.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'entidad-financiera/:id/delete',
        component: EntidadFinancieraDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'infiApp.entidadFinanciera.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
