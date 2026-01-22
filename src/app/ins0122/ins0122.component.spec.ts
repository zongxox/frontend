import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ins0122Component } from './ins0122.component';

describe('Ins0122Component', () => {
  let component: Ins0122Component;
  let fixture: ComponentFixture<Ins0122Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ins0122Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ins0122Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
