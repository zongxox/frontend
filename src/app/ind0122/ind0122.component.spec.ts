import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0122Component } from './ind0122.component';

describe('Ind0122Component', () => {
  let component: Ind0122Component;
  let fixture: ComponentFixture<Ind0122Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0122Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0122Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
