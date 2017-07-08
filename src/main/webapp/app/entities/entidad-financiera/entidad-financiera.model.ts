import { BaseEntity } from './../../shared';

export class EntidadFinanciera implements BaseEntity {
    constructor(
        public id?: number,
        public codigo?: string,
        public denominacion?: string,
        public codigoNumerico?: number,
    ) {
    }
}
