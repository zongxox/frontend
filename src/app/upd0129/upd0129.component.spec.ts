import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Upd0129Component } from './upd0129.component';

describe('Upd0129Component', () => {
  let component: Upd0129Component;
  let fixture: ComponentFixture<Upd0129Component>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ Upd0129Component ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Upd0129Component);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
