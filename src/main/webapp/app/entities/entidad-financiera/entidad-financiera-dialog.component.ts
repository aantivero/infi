import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EntidadFinanciera } from './entidad-financiera.model';
import { EntidadFinancieraPopupService } from './entidad-financiera-popup.service';
import { EntidadFinancieraService } from './entidad-financiera.service';

@Component({
    selector: 'jhi-entidad-financiera-dialog',
    templateUrl: './entidad-financiera-dialog.component.html'
})
export class EntidadFinancieraDialogComponent implements OnInit {

    entidadFinanciera: EntidadFinanciera;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private entidadFinancieraService: EntidadFinancieraService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.entidadFinanciera.id !== undefined) {
            this.subscribeToSaveResponse(
                this.entidadFinancieraService.update(this.entidadFinanciera));
        } else {
            this.subscribeToSaveResponse(
                this.entidadFinancieraService.create(this.entidadFinanciera));
        }
    }

    private subscribeToSaveResponse(result: Observable<EntidadFinanciera>) {
        result.subscribe((res: EntidadFinanciera) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: EntidadFinanciera) {
        this.eventManager.broadcast({ name: 'entidadFinancieraListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-entidad-financiera-popup',
    template: ''
})
export class EntidadFinancieraPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entidadFinancieraPopupService: EntidadFinancieraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.entidadFinancieraPopupService
                    .open(EntidadFinancieraDialogComponent, params['id']);
            } else {
                this.modalRef = this.entidadFinancieraPopupService
                    .open(EntidadFinancieraDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
