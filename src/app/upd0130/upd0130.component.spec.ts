import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Upd0130Component } from './upd0130.component';

describe('Upd0130Component', () => {
  let component: Upd0130Component;
  let fixture: ComponentFixture<Upd0130Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Upd0130Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Upd0130Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
