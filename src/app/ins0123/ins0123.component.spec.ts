import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ins0123Component } from './ins0123.component';

describe('Ins0123Component', () => {
  let component: Ins0123Component;
  let fixture: ComponentFixture<Ins0123Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ins0123Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ins0123Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
