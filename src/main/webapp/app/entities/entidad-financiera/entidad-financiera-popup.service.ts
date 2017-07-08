import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EntidadFinanciera } from './entidad-financiera.model';
import { EntidadFinancieraService } from './entidad-financiera.service';

@Injectable()
export class EntidadFinancieraPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private entidadFinancieraService: EntidadFinancieraService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.entidadFinancieraService.find(id).subscribe((entidadFinanciera) => {
                this.entidadFinancieraModalRef(component, entidadFinanciera);
            });
        } else {
            return this.entidadFinancieraModalRef(component, new EntidadFinanciera());
        }
    }

    entidadFinancieraModalRef(component: Component, entidadFinanciera: EntidadFinanciera): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.entidadFinanciera = entidadFinanciera;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
