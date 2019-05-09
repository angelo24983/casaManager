import { Component, OnInit } from '@angular/core';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ISpesa, Spesa } from 'app/shared/model/spesa.model';
import { SpesaService } from './spesa.service';
import { IUser, UserService } from 'app/core';
import { IProdotto } from 'app/shared/model/prodotto.model';
import { ProdottoService } from 'app/entities/prodotto';

@Component({
  selector: 'jhi-spesa-update',
  templateUrl: './spesa-update.component.html'
})
export class SpesaUpdateComponent implements OnInit {
  spesa: ISpesa;
  isSaving: boolean;

  users: IUser[];

  prodottos: IProdotto[];
  dataDp: any;

  editForm = this.fb.group({
    id: [],
    nome: [null, [Validators.required, Validators.minLength(3)]],
    data: [],
    userId: [],
    prodottos: []
  });

  constructor(
    protected jhiAlertService: JhiAlertService,
    protected spesaService: SpesaService,
    protected userService: UserService,
    protected prodottoService: ProdottoService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.isSaving = false;
    this.activatedRoute.data.subscribe(({ spesa }) => {
      this.updateForm(spesa);
      this.spesa = spesa;
    });
    this.userService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
        map((response: HttpResponse<IUser[]>) => response.body)
      )
      .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    this.prodottoService
      .query()
      .pipe(
        filter((mayBeOk: HttpResponse<IProdotto[]>) => mayBeOk.ok),
        map((response: HttpResponse<IProdotto[]>) => response.body)
      )
      .subscribe((res: IProdotto[]) => (this.prodottos = res), (res: HttpErrorResponse) => this.onError(res.message));
  }

  updateForm(spesa: ISpesa) {
    this.editForm.patchValue({
      id: spesa.id,
      nome: spesa.nome,
      data: spesa.data,
      userId: spesa.userId,
      prodottos: spesa.prodottos
    });
  }

  previousState() {
    window.history.back();
  }

  save() {
    this.isSaving = true;
    const spesa = this.createFromForm();
    if (spesa.id !== undefined) {
      this.subscribeToSaveResponse(this.spesaService.update(spesa));
    } else {
      this.subscribeToSaveResponse(this.spesaService.create(spesa));
    }
  }

  private createFromForm(): ISpesa {
    const entity = {
      ...new Spesa(),
      id: this.editForm.get(['id']).value,
      nome: this.editForm.get(['nome']).value,
      data: this.editForm.get(['data']).value,
      userId: this.editForm.get(['userId']).value,
      prodottos: this.editForm.get(['prodottos']).value
    };
    return entity;
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ISpesa>>) {
    result.subscribe((res: HttpResponse<ISpesa>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

  trackUserById(index: number, item: IUser) {
    return item.id;
  }

  trackProdottoById(index: number, item: IProdotto) {
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
