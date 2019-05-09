import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CasaManagerSharedModule } from 'app/shared';
import {
  TipologiaProdottoComponent,
  TipologiaProdottoDetailComponent,
  TipologiaProdottoUpdateComponent,
  TipologiaProdottoDeletePopupComponent,
  TipologiaProdottoDeleteDialogComponent,
  tipologiaProdottoRoute,
  tipologiaProdottoPopupRoute
} from './';

const ENTITY_STATES = [...tipologiaProdottoRoute, ...tipologiaProdottoPopupRoute];

@NgModule({
  imports: [CasaManagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [
    TipologiaProdottoComponent,
    TipologiaProdottoDetailComponent,
    TipologiaProdottoUpdateComponent,
    TipologiaProdottoDeleteDialogComponent,
    TipologiaProdottoDeletePopupComponent
  ],
  entryComponents: [
    TipologiaProdottoComponent,
    TipologiaProdottoUpdateComponent,
    TipologiaProdottoDeleteDialogComponent,
    TipologiaProdottoDeletePopupComponent
  ],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CasaManagerTipologiaProdottoModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
