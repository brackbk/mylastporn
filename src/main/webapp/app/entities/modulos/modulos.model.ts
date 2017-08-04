import { BaseEntity } from './../../shared';

const enum Status {
    'ACTIVE',
    'INATIVE',
    'WHAIT'
}

export class Modulos implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public caminho?: string,
        public status?: Status,
        public datacriado?: any,
    ) {
    }
}
