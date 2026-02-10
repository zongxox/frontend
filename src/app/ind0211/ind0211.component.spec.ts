import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0211Component } from './ind0211.component';

describe('Ind0211Component', () => {
  let component: Ind0211Component;
  let fixture: ComponentFixture<Ind0211Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0211Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0211Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
