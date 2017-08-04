import { BaseEntity } from './../../shared';

const enum Status {
    'ACTIVE',
    'INATIVE',
    'WHAIT'
}

export class Tags implements BaseEntity {
    constructor(
        public id?: number,
        public nome?: string,
        public imagem?: string,
        public status?: Status,
        public visitado?: number,
        public datacriado?: any,
    ) {
    }
}
