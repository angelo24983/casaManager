import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ISpesa } from 'app/shared/model/spesa.model';
import { SpesaService } from './spesa.service';

@Component({
  selector: 'jhi-spesa-delete-dialog',
  templateUrl: './spesa-delete-dialog.component.html'
})
export class SpesaDeleteDialogComponent {
  spesa: ISpesa;

  constructor(protected spesaService: SpesaService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.spesaService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'spesaListModification',
        content: 'Deleted an spesa'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-spesa-delete-popup',
  template: ''
})
export class SpesaDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ spesa }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(SpesaDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.spesa = spesa;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/spesa', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/spesa', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          }
        );
      }, 0);
    });
  }

  ngOnDestroy() {
    this.ngbModalRef = null;
  }
}
