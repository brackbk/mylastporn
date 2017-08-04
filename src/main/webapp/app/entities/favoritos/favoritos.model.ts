import { BaseEntity } from './../../shared';

export class Favoritos implements BaseEntity {
    constructor(
        public id?: number,
        public idconteudo?: number,
        public datacriado?: any,
        public userId?: number,
        public visibilidadeId?: number,
        public modulosId?: number,
    ) {
    }
}
