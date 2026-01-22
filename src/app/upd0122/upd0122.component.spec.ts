import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Upd0122Component } from './upd0122.component';

describe('Upd0122Component', () => {
  let component: Upd0122Component;
  let fixture: ComponentFixture<Upd0122Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Upd0122Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Upd0122Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
