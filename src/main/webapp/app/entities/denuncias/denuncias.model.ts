import { BaseEntity } from './../../shared';

const enum Status {
    'ACTIVE',
    'INATIVE',
    'WHAIT'
}

export class Denuncias implements BaseEntity {
    constructor(
        public id?: number,
        public idconteudo?: number,
        public titulo?: string,
        public descricao?: any,
        public status?: Status,
        public email?: string,
        public userId?: number,
        public tipoDenunciaId?: number,
        public modulosId?: number,
    ) {
    }
}
