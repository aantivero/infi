import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { EntidadFinanciera } from './entidad-financiera.model';
import { EntidadFinancieraService } from './entidad-financiera.service';

@Component({
    selector: 'jhi-entidad-financiera-detail',
    templateUrl: './entidad-financiera-detail.component.html'
})
export class EntidadFinancieraDetailComponent implements OnInit, OnDestroy {

    entidadFinanciera: EntidadFinanciera;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private entidadFinancieraService: EntidadFinancieraService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEntidadFinancieras();
    }

    load(id) {
        this.entidadFinancieraService.find(id).subscribe((entidadFinanciera) => {
            this.entidadFinanciera = entidadFinanciera;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEntidadFinancieras() {
        this.eventSubscriber = this.eventManager.subscribe(
            'entidadFinancieraListModification',
            (response) => this.load(this.entidadFinanciera.id)
        );
    }
}
