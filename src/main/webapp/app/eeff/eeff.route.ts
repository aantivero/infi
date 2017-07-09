import { Route } from '@angular/router';

import { UserRouteAccessService } from '../shared';
import { EeffComponent } from './';

export const EEFF_ROUTE: Route = {
    path: 'eeff',
    component: EeffComponent,
    data: {
        authorities: [],
        pageTitle: 'eeff.title'
    }
};
