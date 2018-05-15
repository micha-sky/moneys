import { BaseEntity } from './../../shared';

export class MoneyNg implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public date?: any,
        public amount?: number,
        public commissionPct?: number,
    ) {
    }
}
