/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CasaManagerTestModule } from '../../../test.module';
import { TipologiaProdottoDeleteDialogComponent } from 'app/entities/tipologia-prodotto/tipologia-prodotto-delete-dialog.component';
import { TipologiaProdottoService } from 'app/entities/tipologia-prodotto/tipologia-prodotto.service';

describe('Component Tests', () => {
  describe('TipologiaProdotto Management Delete Component', () => {
    let comp: TipologiaProdottoDeleteDialogComponent;
    let fixture: ComponentFixture<TipologiaProdottoDeleteDialogComponent>;
    let service: TipologiaProdottoService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasaManagerTestModule],
        declarations: [TipologiaProdottoDeleteDialogComponent]
      })
        .overrideTemplate(TipologiaProdottoDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(TipologiaProdottoDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(TipologiaProdottoService);
      mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
      mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
    });

    describe('confirmDelete', () => {
      it('Should call delete service on confirmDelete', inject(
        [],
        fakeAsync(() => {
          // GIVEN
          spyOn(service, 'delete').and.returnValue(of({}));

          // WHEN
          comp.confirmDelete(123);
          tick();

          // THEN
          expect(service.delete).toHaveBeenCalledWith(123);
          expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
          expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
        })
      ));
    });
  });
});
