import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0204Component } from './ind0204.component';

describe('Ind0204Component', () => {
  let component: Ind0204Component;
  let fixture: ComponentFixture<Ind0204Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0204Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0204Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
