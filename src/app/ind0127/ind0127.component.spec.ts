import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0127Component } from './ind0127.component';

describe('Ind0127Component', () => {
  let component: Ind0127Component;
  let fixture: ComponentFixture<Ind0127Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0127Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0127Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
