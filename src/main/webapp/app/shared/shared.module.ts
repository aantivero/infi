import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ConfirmService, ConfirmState, ConfirmTemplateDirective, ConfirmModalComponent } from './confirm-modal-and-service';

import {
    InfiSharedLibsModule,
    InfiSharedCommonModule,
    CSRFService,
    AuthServerProvider,
    AccountService,
    UserService,
    StateStorageService,
    LoginService,
    LoginModalService,
    Principal,
    JhiTrackerService,
    HasAnyAuthorityDirective,
    JhiLoginModalComponent
} from './';

@NgModule({
    imports: [
        InfiSharedLibsModule,
        InfiSharedCommonModule
    ],
    declarations: [
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        ConfirmTemplateDirective, ConfirmModalComponent
    ],
    providers: [
        LoginService,
        LoginModalService,
        AccountService,
        StateStorageService,
        Principal,
        CSRFService,
        JhiTrackerService,
        AuthServerProvider,
        UserService,
        DatePipe,
        ConfirmService, ConfirmState
    ],
    entryComponents: [JhiLoginModalComponent],
    exports: [
        InfiSharedCommonModule,
        JhiLoginModalComponent,
        HasAnyAuthorityDirective,
        DatePipe,
        ConfirmTemplateDirective, ConfirmModalComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]

})
export class InfiSharedModule {}
