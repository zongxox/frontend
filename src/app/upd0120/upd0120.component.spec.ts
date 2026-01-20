import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Upd0120Component } from './upd0120.component';

describe('Upd0120Component', () => {
  let component: Upd0120Component;
  let fixture: ComponentFixture<Upd0120Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Upd0120Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Upd0120Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
