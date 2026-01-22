import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ins0121Component } from './ins0121.component';

describe('Ins0121Component', () => {
  let component: Ins0121Component;
  let fixture: ComponentFixture<Ins0121Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ins0121Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ins0121Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
