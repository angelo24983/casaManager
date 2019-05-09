/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasaManagerTestModule } from '../../../test.module';
import { TipologiaProdottoDetailComponent } from 'app/entities/tipologia-prodotto/tipologia-prodotto-detail.component';
import { TipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';

describe('Component Tests', () => {
  describe('TipologiaProdotto Management Detail Component', () => {
    let comp: TipologiaProdottoDetailComponent;
    let fixture: ComponentFixture<TipologiaProdottoDetailComponent>;
    const route = ({ data: of({ tipologiaProdotto: new TipologiaProdotto(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasaManagerTestModule],
        declarations: [TipologiaProdottoDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(TipologiaProdottoDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipologiaProdottoDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.tipologiaProdotto).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
