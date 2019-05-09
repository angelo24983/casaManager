import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IProdotto, Prodotto } from 'app/shared/model/prodotto.model';
import { ProdottoService } from './prodotto.service';
import { ITipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';
import { TipologiaProdottoService } from 'app/entities/tipologia-prodotto';
import { ISpesa } from 'app/shared/model/spesa.model';
import { SpesaService } from 'app/entities/spesa';

@Component({
  selector: 'jhi-prodotto-update',
  templateUrl: './prodotto-update.component.html'
})
export class ProdottoUpdateComponent implements OnInit {
  prodotto: IProdotto;
  isSaving: boolean;

  tipologias: ITipologiaProdotto[];

  spesas: ISpesa[];

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(2)]],
    prezzo: [null, [Validators.required]],
    tipologiaId: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected prodottoService: ProdottoService,
    protected tipologiaProdottoService: TipologiaProdottoService,
    protected spesaService: SpesaService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ prodotto }) => {
      this.updateForm(prodotto);
      this.prodotto = prodotto;
    });
    this.tipologiaProdottoService
      .query({ filter: 'prodotto-is-null' })
      .pipe(
        filter((mayBeOk: HttpResponse<ITipologiaProdotto[]>) => mayBeOk.ok),
        map((response: HttpResponse<ITipologiaProdotto[]>) => response.body)
      )
      .subscribe(
        (res: ITipologiaProdotto[]) => {
          if (!this.prodotto.tipologiaId) {
            this.tipologias = res;
          } else {
            this.tipologiaProdottoService
              .find(this.prodotto.tipologiaId)
              .pipe(
                filter((subResMayBeOk: HttpResponse<ITipologiaProdotto>) => subResMayBeOk.ok),
                map((subResponse: HttpResponse<ITipologiaProdotto>) => subResponse.body)
              )
              .subscribe(
                (subRes: ITipologiaProdotto) => (this.tipologias = [subRes].concat(res)),
                (subRes: HttpErrorResponse) => this.onError(subRes.message)
              );
          }
        },
        (res: HttpErrorResponse) => this.onError(res.message)
      );
    this.spesaService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<ISpesa[]>) => mayBeOk.ok),
        map((response: HttpResponse<ISpesa[]>) => response.body)
      )
      .subscribe((res: ISpesa[]) => (this.spesas = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(prodotto: IProdotto) {
    this.editForm.patchValue({
      id: prodotto.id,
      nome: prodotto.nome,
      prezzo: prodotto.prezzo,
      tipologiaId: prodotto.tipologiaId
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const prodotto = this.createFromForm();
    if (prodotto.id !== undefined) {
      this.subscribeToSaveResponse(this.prodottoService.update(prodotto));
    } else {
      this.subscribeToSaveResponse(this.prodottoService.create(prodotto));
    }
  }

  private createFromForm(): IProdotto {
    const entity = {
      ...new Prodotto(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      prezzo: this.editForm.get(['prezzo']).value,
      tipologiaId: this.editForm.get(['tipologiaId']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IProdotto>>) {
    result.subscribe((res: HttpResponse<IProdotto>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
  }

  protected onSaveSuccess() {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError() {
    this.isSaving = false;
  }
  protected onError(errorMessage: string) {
    this.jhiAlertService.error(errorMessage, null, null);
  }

  trackTipologiaProdottoById(index: number, item: ITipologiaProdotto) {
    return item.id;
  }

  trackSpesaById(index: number, item: ISpesa) {
    return item.id;
  }

  getSelected(selectedVals: Array<any>, option: any) {
    if (selectedVals) {
      for (let i = 0; i < selectedVals.length; i++) {
        if (option.id === selectedVals[i].id) {
          return selectedVals[i];
        }
      }
    }
    return option;
  }
}
