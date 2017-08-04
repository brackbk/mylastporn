import { BaseEntity } from './../../shared';

const enum Status {
    'ACTIVE',
    'INATIVE',
    'WHAIT'
}

export class Tipo implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public status?: Status,
        public datacriado?: any,
        public modulosId?: number,
    ) {
    }
}
