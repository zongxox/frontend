import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0126Component } from './ind0126.component';

describe('Ind0126Component', () => {
  let component: Ind0126Component;
  let fixture: ComponentFixture<Ind0126Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0126Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0126Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
