import { BaseEntity } from './../../shared';

const enum Status {
    'ACTIVE',
    'INATIVE',
    'WHAIT'
}

export class Pagina implements BaseEntity {
    constructor(
        public id?: number,
        public titulo?: string,
        public descricao?: any,
        public gay?: boolean,
        public capa?: string,
        public status?: Status,
        public official?: boolean,
        public visitado?: number,
        public datacriado?: any,
        public userId?: number,
        public visibilidadeId?: number,
        public tipoId?: number,
        public tags?: BaseEntity[],
    ) {
        this.gay = false;
        this.official = false;
    }
}
