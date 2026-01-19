import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Upd0119Component } from './upd0119.component';

describe('Upd0119Component', () => {
  let component: Upd0119Component;
  let fixture: ComponentFixture<Upd0119Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Upd0119Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Upd0119Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
