import { BaseEntity } from './../../shared';

const enum Status {
    'ACTIVE',
    'INATIVE',
    'WHAIT'
}

export class Comentarios implements BaseEntity {
    constructor(
        public id?: number,
        public idconteudo?: number,
        public titulo?: string,
        public comentario?: any,
        public datacriado?: any,
        public status?: Status,
        public userId?: number,
        public visibilidadeId?: number,
        public modulosId?: number,
    ) {
    }
}
