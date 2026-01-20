import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0120Component } from './ind0120.component';

describe('Ind0120Component', () => {
  let component: Ind0120Component;
  let fixture: ComponentFixture<Ind0120Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0120Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0120Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
