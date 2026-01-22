import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0121Component } from './ind0121.component';

describe('Ind0121Component', () => {
  let component: Ind0121Component;
  let fixture: ComponentFixture<Ind0121Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0121Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0121Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
