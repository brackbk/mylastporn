import { BaseEntity } from './../../shared';

export class Likes implements BaseEntity {
    constructor(
        public id?: number,
        public idconteudo?: number,
        public like?: boolean,
        public dislike?: boolean,
        public datacriado?: any,
        public userId?: number,
        public modulosId?: number,
    ) {
        this.like = false;
        this.dislike = false;
    }
}
