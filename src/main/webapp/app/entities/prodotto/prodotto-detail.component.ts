import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IProdotto } from 'app/shared/model/prodotto.model';

@Component({
  selector: 'jhi-prodotto-detail',
  templateUrl: './prodotto-detail.component.html'
})
export class ProdottoDetailComponent implements OnInit {
  prodotto: IProdotto;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ prodotto }) => {
      this.prodotto = prodotto;
    });
  }

  previousState() {
    window.history.back();
  }
}
