import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0206Component } from './ind0206.component';

describe('Ind0206Component', () => {
  let component: Ind0206Component;
  let fixture: ComponentFixture<Ind0206Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0206Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0206Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
