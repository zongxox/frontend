import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0203Component } from './ind0203.component';

describe('Ind0203Component', () => {
  let component: Ind0203Component;
  let fixture: ComponentFixture<Ind0203Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0203Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0203Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
