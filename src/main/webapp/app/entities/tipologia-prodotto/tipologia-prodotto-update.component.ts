import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { ITipologiaProdotto, TipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';
import { TipologiaProdottoService } from './tipologia-prodotto.service';

@Component({
  selector: 'jhi-tipologia-prodotto-update',
  templateUrl: './tipologia-prodotto-update.component.html'
})
export class TipologiaProdottoUpdateComponent implements OnInit {
  tipologiaProdotto: ITipologiaProdotto;
  isSaving: boolean;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(2)]],
    descrizione: [null, [Validators.required, Validators.minLength(2)]]
  });

  constructor(
    protected tipologiaProdottoService: TipologiaProdottoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ tipologiaProdotto }) => {
      this.updateForm(tipologiaProdotto);
      this.tipologiaProdotto = tipologiaProdotto;
    });
  }

  updateForm(tipologiaProdotto: ITipologiaProdotto) {
    this.editForm.patchValue({
      id: tipologiaProdotto.id,
      nome: tipologiaProdotto.nome,
      descrizione: tipologiaProdotto.descrizione
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const tipologiaProdotto = this.createFromForm();
    if (tipologiaProdotto.id !== undefined) {
      this.subscribeToSaveResponse(this.tipologiaProdottoService.update(tipologiaProdotto));
    } else {
      this.subscribeToSaveResponse(this.tipologiaProdottoService.create(tipologiaProdotto));
    }
  }

  private createFromForm(): ITipologiaProdotto {
    const entity = {
      ...new TipologiaProdotto(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      descrizione: this.editForm.get(['descrizione']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITipologiaProdotto>>) {
    result.subscribe((res: HttpResponse<ITipologiaProdotto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
}
