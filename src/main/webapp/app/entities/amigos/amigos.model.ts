import { BaseEntity } from './../../shared';

const enum Status {
    'ACTIVE',
    'INATIVE',
    'WHAIT'
}

export class Amigos implements BaseEntity {
    constructor(
        public id?: number,
        public status?: Status,
        public userId?: number,
    ) {
    }
}
