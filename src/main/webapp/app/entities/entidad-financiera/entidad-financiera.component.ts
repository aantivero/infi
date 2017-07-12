import { Component, OnInit, OnDestroy, ViewChild, EventEmitter } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { EntidadFinanciera } from './entidad-financiera.model';
import { EntidadFinancieraService } from './entidad-financiera.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';
import { ConfirmService } from '../../shared/confirm-modal-and-service';
import {RequestOptions} from '@angular/http';
import {Http} from '@angular/http';
import {Observable} from 'rxjs/Observable';

@Component({
    selector: 'jhi-entidad-financiera',
    templateUrl: './entidad-financiera.component.html'
})
export class EntidadFinancieraComponent implements OnInit, OnDestroy {

    entidadFinancieras: EntidadFinanciera[];
    currentAccount: any;
    eventSubscriber: Subscription;
    itemsPerPage: number;
    links: any;
    page: any;
    predicate: any;
    queryCount: any;
    reverse: any;
    totalItems: number;
    @ViewChild('archivo') archivo;

    constructor(
        private entidadFinancieraService: EntidadFinancieraService,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private parseLinks: JhiParseLinks,
        private principal: Principal,
        private confirmService: ConfirmService,
        private http: Http
    ) {
        this.entidadFinancieras = [];
        this.itemsPerPage = ITEMS_PER_PAGE;
        this.page = 0;
        this.links = {
            last: 0
        };
        this.predicate = 'id';
        this.reverse = true;
    }

    loadAll() {
        this.entidadFinancieraService.query({
            page: this.page,
            size: this.itemsPerPage,
            sort: this.sort()
        }).subscribe(
            (res: ResponseWrapper) => this.onSuccess(res.json, res.headers),
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    reset() {
        this.page = 0;
        this.entidadFinancieras = [];
        this.loadAll();
    }

    loadPage(page) {
        this.page = page;
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEntidadFinancieras();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EntidadFinanciera) {
        return item.id;
    }
    registerChangeInEntidadFinancieras() {
        this.eventSubscriber = this.eventManager.subscribe('entidadFinancieraListModification', (response) => this.reset());
    }

    sort() {
        const result = [this.predicate + ',' + (this.reverse ? 'asc' : 'desc')];
        if (this.predicate !== 'id') {
            result.push('id');
        }
        return result;
    }

    private onSuccess(data, headers) {
        this.links = this.parseLinks.parse(headers.get('link'));
        this.totalItems = headers.get('X-Total-Count');
        for (let i = 0; i < data.length; i++) {
            this.entidadFinancieras.push(data[i]);
        }
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }

    loadData(evento) {
        this.confirmService.confirm({
            title: 'infiApp.entidadFinanciera.cargarArchivo.label.confirmacion', message: 'infiApp.entidadFinanciera.cargarArchivo.mensaje.confirmacion'
        }).then(
            () => {
                const fileList: FileList = evento.target.files;
                if (fileList.length > 0) {
                    this.entidadFinancieraService.loadData(fileList[0]).subscribe(
                        (res: ResponseWrapper) => this.onSuccessLoadData(res.json),
                        (res: ResponseWrapper) => this.onError(res.json)
                    );
                }
                this.archivo.nativeElement.value = '';
            },
            () => {
                this.archivo.nativeElement.value = '';
            });
    }

    private onSuccessLoadData(respuesta: boolean) {
        this.loadAll();
        this.alertService.info(respuesta ? 'infiApp.entidadFinanciera.cargarArchivo.mensaje.success' : 'infiApp.entidadFinanciera.cargarArchivo.mensaje.error');
    }

    fileChange(event) {
        const fileList: FileList = event.target.files;
        if (fileList.length > 0) {
            const file: File = fileList[0];
            const formData: FormData = new FormData();
            formData.append('archivo', file, file.name);
            const headers = new Headers();
            headers.append('Content-Type', 'multipart/form-data');
            headers.append('Accept', 'application/json');
            // let options = new RequestOptions({ headers: headers });
            this.http.post(`/api/entidad-financiera/load`, formData)
                .map((res) => res.json())
                .catch((error) => Observable.throw(error))
                .subscribe(
                    (data) => console.log('success'),
                    (error) => console.log(error)
                )
        }
    }
}
