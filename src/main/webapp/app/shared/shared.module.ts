import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { CasaManagerSharedLibsModule, CasaManagerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective } from './';

@NgModule({
  imports: [CasaManagerSharedLibsModule, CasaManagerSharedCommonModule],
  declarations: [JhiLoginModalComponent, HasAnyAuthorityDirective],
  entryComponents: [JhiLoginModalComponent],
  exports: [CasaManagerSharedCommonModule, JhiLoginModalComponent, HasAnyAuthorityDirective],
  schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class CasaManagerSharedModule {
  static forRoot() {
    return {
      ngModule: CasaManagerSharedModule
    };
  }
}
