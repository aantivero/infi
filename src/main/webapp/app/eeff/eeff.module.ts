import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { InfiSharedModule } from '../shared';
import { EeffComponent, EEFF_ROUTE } from './';

@NgModule({
  imports: [
      InfiSharedModule,
      RouterModule.forRoot([ EEFF_ROUTE ], { useHash: true })
  ],
  declarations: [EeffComponent],
    entryComponents: [
    ],
    providers: [
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EeffModule { }
