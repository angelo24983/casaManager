import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Spesa } from 'app/shared/model/spesa.model';
import { SpesaService } from './spesa.service';
import { SpesaComponent } from './spesa.component';
import { SpesaDetailComponent } from './spesa-detail.component';
import { SpesaUpdateComponent } from './spesa-update.component';
import { SpesaDeletePopupComponent } from './spesa-delete-dialog.component';
import { ISpesa } from 'app/shared/model/spesa.model';

@Injectable({ providedIn: 'root' })
export class SpesaResolve implements Resolve<ISpesa> {
  constructor(private service: SpesaService) {}

  resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<ISpesa> {
    const id = route.params['id'] ? route.params['id'] : null;
    if (id) {
      return this.service.find(id).pipe(
        filter((response: HttpResponse<Spesa>) => response.ok),
        map((spesa: HttpResponse<Spesa>) => spesa.body)
      );
    }
    return of(new Spesa());
  }
}

export const spesaRoute: Routes = [
  {
    path: '',
    component: SpesaComponent,
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.spesa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: SpesaDetailComponent,
    resolve: {
      spesa: SpesaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.spesa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: SpesaUpdateComponent,
    resolve: {
      spesa: SpesaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.spesa.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: SpesaUpdateComponent,
    resolve: {
      spesa: SpesaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.spesa.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

export const spesaPopupRoute: Routes = [
  {
    path: ':id/delete',
    component: SpesaDeletePopupComponent,
    resolve: {
      spesa: SpesaResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'casaManagerApp.spesa.home.title'
    },
    canActivate: [UserRouteAccessService],
    outlet: 'popup'
  }
];
