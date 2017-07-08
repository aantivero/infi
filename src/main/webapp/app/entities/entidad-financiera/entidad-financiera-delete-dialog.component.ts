import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EntidadFinanciera } from './entidad-financiera.model';
import { EntidadFinancieraPopupService } from './entidad-financiera-popup.service';
import { EntidadFinancieraService } from './entidad-financiera.service';

@Component({
    selector: 'jhi-entidad-financiera-delete-dialog',
    templateUrl: './entidad-financiera-delete-dialog.component.html'
})
export class EntidadFinancieraDeleteDialogComponent {

    entidadFinanciera: EntidadFinanciera;

    constructor(
        private entidadFinancieraService: EntidadFinancieraService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.entidadFinancieraService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'entidadFinancieraListModification',
                content: 'Deleted an entidadFinanciera'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-entidad-financiera-delete-popup',
    template: ''
})
export class EntidadFinancieraDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private entidadFinancieraPopupService: EntidadFinancieraPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.entidadFinancieraPopupService
                .open(EntidadFinancieraDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
