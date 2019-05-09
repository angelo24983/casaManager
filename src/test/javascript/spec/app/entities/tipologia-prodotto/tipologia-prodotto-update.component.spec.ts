/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CasaManagerTestModule } from '../../../test.module';
import { TipologiaProdottoUpdateComponent } from 'app/entities/tipologia-prodotto/tipologia-prodotto-update.component';
import { TipologiaProdottoService } from 'app/entities/tipologia-prodotto/tipologia-prodotto.service';
import { TipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';

describe('Component Tests', () => {
  describe('TipologiaProdotto Management Update Component', () => {
    let comp: TipologiaProdottoUpdateComponent;
    let fixture: ComponentFixture<TipologiaProdottoUpdateComponent>;
    let service: TipologiaProdottoService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasaManagerTestModule],
        declarations: [TipologiaProdottoUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(TipologiaProdottoUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(TipologiaProdottoUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipologiaProdottoService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipologiaProdotto(123);
        spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.update).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));

      it('Should call create service on save for new entity', fakeAsync(() => {
        // GIVEN
        const entity = new TipologiaProdotto();
        spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
        comp.updateForm(entity);
        // WHEN
        comp.save();
        tick(); // simulate async

        // THEN
        expect(service.create).toHaveBeenCalledWith(entity);
        expect(comp.isSaving).toEqual(false);
      }));
    });
  });
});
