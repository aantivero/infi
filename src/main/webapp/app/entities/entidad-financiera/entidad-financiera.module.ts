import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InfiSharedModule } from '../../shared';
import {
    EntidadFinancieraService,
    EntidadFinancieraPopupService,
    EntidadFinancieraComponent,
    EntidadFinancieraDetailComponent,
    EntidadFinancieraDialogComponent,
    EntidadFinancieraPopupComponent,
    EntidadFinancieraDeletePopupComponent,
    EntidadFinancieraDeleteDialogComponent,
    entidadFinancieraRoute,
    entidadFinancieraPopupRoute,
} from './';

const ENTITY_STATES = [
    ...entidadFinancieraRoute,
    ...entidadFinancieraPopupRoute,
];

@NgModule({
    imports: [
        InfiSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EntidadFinancieraComponent,
        EntidadFinancieraDetailComponent,
        EntidadFinancieraDialogComponent,
        EntidadFinancieraDeleteDialogComponent,
        EntidadFinancieraPopupComponent,
        EntidadFinancieraDeletePopupComponent,
    ],
    entryComponents: [
        EntidadFinancieraComponent,
        EntidadFinancieraDialogComponent,
        EntidadFinancieraPopupComponent,
        EntidadFinancieraDeleteDialogComponent,
        EntidadFinancieraDeletePopupComponent,
    ],
    providers: [
        EntidadFinancieraService,
        EntidadFinancieraPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InfiEntidadFinancieraModule {}
