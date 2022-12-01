import { ComponentFixture, TestBed } from '@angular/core/testing';

import { DrsParserComponent } from './drs-parser.component';

describe('DrsParserComponent', () => {
  let component: DrsParserComponent;
  let fixture: ComponentFixture<DrsParserComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ DrsParserComponent ]
    })
    .compileComponents();

    fixture = TestBed.createComponent(DrsParserComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
