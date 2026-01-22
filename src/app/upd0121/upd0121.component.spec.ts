import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Upd0121Component } from './upd0121.component';

describe('Upd0121Component', () => {
  let component: Upd0121Component;
  let fixture: ComponentFixture<Upd0121Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Upd0121Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Upd0121Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
