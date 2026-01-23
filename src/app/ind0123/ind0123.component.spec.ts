import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0123Component } from './ind0123.component';

describe('Ind0123Component', () => {
  let component: Ind0123Component;
  let fixture: ComponentFixture<Ind0123Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0123Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0123Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
