/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { CasaManagerTestModule } from '../../../test.module';
import { SpesaDetailComponent } from 'app/entities/spesa/spesa-detail.component';
import { Spesa } from 'app/shared/model/spesa.model';

describe('Component Tests', () => {
  describe('Spesa Management Detail Component', () => {
    let comp: SpesaDetailComponent;
    let fixture: ComponentFixture<SpesaDetailComponent>;
    const route = ({ data: of({ spesa: new Spesa(123) }) } as any) as ActivatedRoute;

    beforeEach(() => {
      TestBed.configureTestingModule({
        imports: [CasaManagerTestModule],
        declarations: [SpesaDetailComponent],
        providers: [{ provide: ActivatedRoute, useValue: route }]
      })
        .overrideTemplate(SpesaDetailComponent, '')
        .compileComponents();
      fixture = TestBed.createComponent(SpesaDetailComponent);
      comp = fixture.componentInstance;
    });

    describe('OnInit', () => {
      it('Should call load all on init', () => {
        // GIVEN

        // WHEN
        comp.ngOnInit();

        // THEN
        expect(comp.spesa).toEqual(jasmine.objectContaining({ id: 123 }));
      });
    });
  });
});
