import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0130Component } from './ind0130.component';

describe('Ind0130Component', () => {
  let component: Ind0130Component;
  let fixture: ComponentFixture<Ind0130Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0130Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0130Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
