import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Login0131Component } from './login0131.component';

describe('Login0131Component', () => {
  let component: Login0131Component;
  let fixture: ComponentFixture<Login0131Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Login0131Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Login0131Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
