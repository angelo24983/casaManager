export interface ITipologiaProdotto {
  id?: number;
  nome?: string;
  descrizione?: string;
}

export class TipologiaProdotto implements ITipologiaProdotto {
  constructor(public id?: number, public nome?: string, public descrizione?: string) {}
}
