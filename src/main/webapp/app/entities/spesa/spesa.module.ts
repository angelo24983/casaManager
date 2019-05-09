import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';
import { JhiLanguageService } from 'ng-jhipster';
import { JhiLanguageHelper } from 'app/core';

import { CasaManagerSharedModule } from 'app/shared';
import {
  SpesaComponent,
  SpesaDetailComponent,
  SpesaUpdateComponent,
  SpesaDeletePopupComponent,
  SpesaDeleteDialogComponent,
  spesaRoute,
  spesaPopupRoute
} from './';

const ENTITY_STATES = [...spesaRoute, ...spesaPopupRoute];

@NgModule({
  imports: [CasaManagerSharedModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [SpesaComponent, SpesaDetailComponent, SpesaUpdateComponent, SpesaDeleteDialogComponent, SpesaDeletePopupComponent],
  entryComponents: [SpesaComponent, SpesaUpdateComponent, SpesaDeleteDialogComponent, SpesaDeletePopupComponent],
  providers: [{ provide: JhiLanguageService, useClass: JhiLanguageService }],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CasaManagerSpesaModule {
  constructor(private languageService: JhiLanguageService, private languageHelper: JhiLanguageHelper) {
    this.languageHelper.language.subscribe((languageKey: string) => {
      if (languageKey !== undefined) {
        this.languageService.changeLanguage(languageKey);
      }
    });
  }
}
