import { BaseEntity } from './../shared';

export class EntidadesFinancieras implements BaseEntity {
    constructor(
        public id?: number,
        public codigo?: string,
        public denominacion?: string
    ) {
    }
}
