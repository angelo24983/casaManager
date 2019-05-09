import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITipologiaProdotto } from 'app/shared/model/tipologia-prodotto.model';
import { TipologiaProdottoService } from './tipologia-prodotto.service';

@Component({
  selector: 'jhi-tipologia-prodotto-delete-dialog',
  templateUrl: './tipologia-prodotto-delete-dialog.component.html'
})
export class TipologiaProdottoDeleteDialogComponent {
  tipologiaProdotto: ITipologiaProdotto;

  constructor(
    protected tipologiaProdottoService: TipologiaProdottoService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  clear() {
    this.activeModal.dismiss('cancel');
  }

  confirmDelete(id: number) {
    this.tipologiaProdottoService.delete(id).subscribe(response => {
      this.eventManager.broadcast({
        name: 'tipologiaProdottoListModification',
        content: 'Deleted an tipologiaProdotto'
      });
      this.activeModal.dismiss(true);
    });
  }
}

@Component({
  selector: 'jhi-tipologia-prodotto-delete-popup',
  template: ''
})
export class TipologiaProdottoDeletePopupComponent implements OnInit, OnDestroy {
  protected ngbModalRef: NgbModalRef;

  constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

  ngOnInit() {
    this.activatedRoute.data.subscribe(({ tipologiaProdotto }) => {
      setTimeout(() => {
        this.ngbModalRef = this.modalService.open(TipologiaProdottoDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
        this.ngbModalRef.componentInstance.tipologiaProdotto = tipologiaProdotto;
        this.ngbModalRef.result.then(
          result => {
            this.router.navigate(['/tipologia-prodotto', { outlets: { popup: null } }]);
            this.ngbModalRef = null;
          },
          reason => {
            this.router.navigate(['/tipologia-prodotto', { outlets: { popup: null } }]);
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
