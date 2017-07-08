import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { InfiEntidadFinancieraModule } from './entidad-financiera/entidad-financiera.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        InfiEntidadFinancieraModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class InfiEntityModule {}
