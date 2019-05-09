import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'spesa',
        loadChildren: './spesa/spesa.module#CasaManagerSpesaModule'
      },
      {
        path: 'prodotto',
        loadChildren: './prodotto/prodotto.module#CasaManagerProdottoModule'
      },
      {
        path: 'tipologia-prodotto',
        loadChildren: './tipologia-prodotto/tipologia-prodotto.module#CasaManagerTipologiaProdottoModule'
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ],
  declarations: [],
  entryComponents: [],
  providers: [],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CasaManagerEntityModule {}
