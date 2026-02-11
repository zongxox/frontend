import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0212Component } from './ind0212.component';

describe('Ind0212Component', () => {
  let component: Ind0212Component;
  let fixture: ComponentFixture<Ind0212Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0212Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0212Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
