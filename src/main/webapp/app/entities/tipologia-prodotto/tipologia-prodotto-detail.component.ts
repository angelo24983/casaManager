import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ITipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';

@Component({
  selector: 'jhi-tipologia-prodotto-detail',
  templateUrl: './tipologia-prodotto-detail.component.html'
})
export class TipologiaProdottoDetailComponent implements OnInit {
  tipologiaProdotto: ITipologiaProdotto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipologiaProdotto }) => {
      this.tipologiaProdotto = tipologiaProdotto;
    });
  }

  previousState() {
    window.history.back();
  }
}
