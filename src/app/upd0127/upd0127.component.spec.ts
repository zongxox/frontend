import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Upd0127Component } from './upd0127.component';

describe('Upd0127Component', () => {
  let component: Upd0127Component;
  let fixture: ComponentFixture<Upd0127Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Upd0127Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Upd0127Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
