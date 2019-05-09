import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { TipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';
import { TipologiaProdottoService } from './tipologia-prodotto.service';
import { TipologiaProdottoComponent } from './tipologia-prodotto.component';
import { TipologiaProdottoDetailComponent } from './tipologia-prodotto-detail.component';
import { TipologiaProdottoUpdateComponent } from './tipologia-prodotto-update.component';
import { TipologiaProdottoDeletePopupComponent } from './tipologia-prodotto-delete-dialog.component';
import { ITipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';

@Injectable({ providedIn: 'root' })
export class TipologiaProdottoResolve implements Resolve<ITipologiaProdotto> {
  constructor(private service: TipologiaProdottoService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ITipologiaProdotto> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<TipologiaProdotto>) => response.ok),
        map((tipologiaProdotto: HttpResponse<TipologiaProdotto>) => tipologiaProdotto.body)
      );
    }
    return of(new TipologiaProdotto());
  }
}

export const tipologiaProdottoRoute: Routes = [
  {
    path: '',
    component: TipologiaProdottoComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.tipologiaProdotto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TipologiaProdottoDetailComponent,
    resolve: {
      tipologiaProdotto: TipologiaProdottoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.tipologiaProdotto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TipologiaProdottoUpdateComponent,
    resolve: {
      tipologiaProdotto: TipologiaProdottoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.tipologiaProdotto.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TipologiaProdottoUpdateComponent,
    resolve: {
      tipologiaProdotto: TipologiaProdottoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.tipologiaProdotto.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const tipologiaProdottoPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: TipologiaProdottoDeletePopupComponent,
    resolve: {
      tipologiaProdotto: TipologiaProdottoResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.tipologiaProdotto.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
