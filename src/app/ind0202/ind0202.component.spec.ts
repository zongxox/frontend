import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0202Component } from './ind0202.component';

describe('Ind0202Component', () => {
  let component: Ind0202Component;
  let fixture: ComponentFixture<Ind0202Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0202Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0202Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
