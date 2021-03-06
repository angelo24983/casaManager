/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { CasaManagerTestModule } from '../../../test.module';
import { SpesaDeleteDialogComponent } from 'app/entities/spesa/spesa-delete-dialog.component';
import { SpesaService } from 'app/entities/spesa/spesa.service';

describe('Component Tests', () => {
  describe('Spesa Management Delete Component', () => {
    let comp: SpesaDeleteDialogComponent;
    let fixture: ComponentFixture<SpesaDeleteDialogComponent>;
    let service: SpesaService;
    let mockEventManager: any;
    let mockActiveModal: any;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasaManagerTestModule],
        declarations: [SpesaDeleteDialogComponent]
      })
        .overrideTemplate(SpesaDeleteDialogComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpesaDeleteDialogComponent);
      comp = fixture.componentInstance;
      service = fixture.debugElement.injector.get(SpesaService);
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
