import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Upd0123Component } from './upd0123.component';

describe('Upd0123Component', () => {
  let component: Upd0123Component;
  let fixture: ComponentFixture<Upd0123Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Upd0123Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Upd0123Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
