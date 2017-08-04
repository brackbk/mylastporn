import { BaseEntity } from './../../shared';

export class TipoDenuncia implements BaseEntity {
    constructor(
        public id?: number,
        public tipo?: string,
    ) {
    }
}
