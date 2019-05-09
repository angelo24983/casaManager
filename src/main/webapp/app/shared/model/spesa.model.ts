import { Moment } from 'moment';
import { IProdotto } from 'app/shared/model/prodotto.model';

export interface ISpesa {
  id?: number;
  nome?: string;
  data?: Moment;
  userLogin?: string;
  userId?: number;
  prodottos?: IProdotto[];
}

export class Spesa implements ISpesa {
  constructor(
    public id?: number,
    public nome?: string,
    public data?: Moment,
    public userLogin?: string,
    public userId?: number,
    public prodottos?: IProdotto[]
  ) {}
}
