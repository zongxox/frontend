import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ins0119Component } from './ins0119.component';

describe('Ins0119Component', () => {
  let component: Ins0119Component;
  let fixture: ComponentFixture<Ins0119Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ins0119Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ins0119Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
