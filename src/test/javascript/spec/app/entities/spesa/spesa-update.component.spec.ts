/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { Observable, of } from 'rxjs';

import { CasaManagerTestModule } from '../../../test.module';
import { SpesaUpdateComponent } from 'app/entities/spesa/spesa-update.component';
import { SpesaService } from 'app/entities/spesa/spesa.service';
import { Spesa } from 'app/shared/model/spesa.model';

describe('Component Tests', () => {
  describe('Spesa Management Update Component', () => {
    let comp: SpesaUpdateComponent;
    let fixture: ComponentFixture<SpesaUpdateComponent>;
    let service: SpesaService;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasaManagerTestModule],
        declarations: [SpesaUpdateComponent],
        providers: [FormBuilder]
      })
        .overrideTemplate(SpesaUpdateComponent, '')
        .compileComponents();

      fixture = TestBed.createComponent(SpesaUpdateComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpesaService);
    });

    describe('save', () => {
      it('Should call update service on save for existing entity', fakeAsync(() => {
        // GIVEN
        const entity = new Spesa(123);
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
        const entity = new Spesa();
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
