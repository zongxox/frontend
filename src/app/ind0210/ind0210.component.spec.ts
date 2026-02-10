import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0210Component } from './ind0210.component';

describe('Ind0210Component', () => {
  let component: Ind0210Component;
  let fixture: ComponentFixture<Ind0210Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0210Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0210Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
