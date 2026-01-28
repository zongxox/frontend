import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0128Component } from './ind0128.component';

describe('Ind0128Component', () => {
  let component: Ind0128Component;
  let fixture: ComponentFixture<Ind0128Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0128Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0128Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
