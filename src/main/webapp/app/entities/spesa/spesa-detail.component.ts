import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ISpesa } from 'app/shared/model/spesa.model';

@Component({
  selector: 'jhi-spesa-detail',
  templateUrl: './spesa-detail.component.html'
})
export class SpesaDetailComponent implements OnInit {
  spesa: ISpesa;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spesa }) => {
      this.spesa = spesa;
    });
  }

  previousState() {
    window.history.back();
  }
}
