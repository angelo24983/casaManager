import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ISpesa } from 'app/shared/model/spesa.model';

type EntityResponseType = HttpResponse<ISpesa>;
type EntityArrayResponseType = HttpResponse<ISpesa[]>;

@Injectable({ providedIn: 'root' })
export class SpesaService {
  public resourceUrl = SERVER_API_URL + 'api/spesas';

  constructor(protected http: HttpClient) {}

  create(spesa: ISpesa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(spesa);
    return this.http
      .post<ISpesa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(spesa: ISpesa): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(spesa);
    return this.http
      .put<ISpesa>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ISpesa>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ISpesa[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(spesa: ISpesa): ISpesa {
    const copy: ISpesa = Object.assign({}, spesa, {
      data: spesa.data != null && spesa.data.isValid() ? spesa.data.format(DATE_FORMAT) : null
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.data = res.body.data != null ? moment(res.body.data) : null;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((spesa: ISpesa) => {
        spesa.data = spesa.data != null ? moment(spesa.data) : null;
      });
    }
    return res;
  }
}
