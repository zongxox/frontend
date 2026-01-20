import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ins0120Component } from './ins0120.component';

describe('Ins0120Component', () => {
  let component: Ins0120Component;
  let fixture: ComponentFixture<Ins0120Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ins0120Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ins0120Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
