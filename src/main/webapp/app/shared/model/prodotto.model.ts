import { ISpesa } from 'app/shared/model/spesa.model';

export interface IProdotto {
  id?: number;
  nome?: string;
  prezzo?: number;
  tipologiaNome?: string;
  tipologiaId?: number;
  spesas?: ISpesa[];
}

export class Prodotto implements IProdotto {
  constructor(
    public id?: number,
    public nome?: string,
    public prezzo?: number,
    public tipologiaNome?: string,
    public tipologiaId?: number,
    public spesas?: ISpesa[]
  ) {}
}
