import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';

type EntityResponseType = HttpResponse<ITipologiaProdotto>;
type EntityArrayResponseType = HttpResponse<ITipologiaProdotto[]>;

@Injectable({ providedIn: 'root' })
export class TipologiaProdottoService {
  public resourceUrl = SERVER_API_URL + 'api/tipologia-prodottos';

  constructor(protected http: HttpClient) {}

  create(tipologiaProdotto: ITipologiaProdotto): Observable<EntityResponseType> {
    return this.http.post<ITipologiaProdotto>(this.resourceUrl, tipologiaProdotto, { observe: 'response' });
  }

  update(tipologiaProdotto: ITipologiaProdotto): Observable<EntityResponseType> {
    return this.http.put<ITipologiaProdotto>(this.resourceUrl, tipologiaProdotto, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ITipologiaProdotto>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ITipologiaProdotto[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<any>> {
    return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
