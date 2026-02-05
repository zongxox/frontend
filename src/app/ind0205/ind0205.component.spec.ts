import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0205Component } from './ind0205.component';

describe('Ind0205Component', () => {
  let component: Ind0205Component;
  let fixture: ComponentFixture<Ind0205Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0205Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0205Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
