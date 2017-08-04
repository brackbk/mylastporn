import { BaseEntity } from './../../shared';

export class Visibilidade implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
    ) {
    }
}
