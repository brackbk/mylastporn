import { BaseEntity } from './../../shared';

const enum Status {
    'ACTIVE',
    'INATIVE',
    'WHAIT'
}

export class SeguidoresPagina implements BaseEntity {
    constructor(
        public id?: number,
        public status?: Status,
        public paginaId?: number,
        public userId?: number,
    ) {
    }
}
