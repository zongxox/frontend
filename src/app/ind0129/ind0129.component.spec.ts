import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Ind0129Component } from './ind0129.component';

describe('Ind0129Component', () => {
  let component: Ind0129Component;
  let fixture: ComponentFixture<Ind0129Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Ind0129Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Ind0129Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
