import { TestBed } from '@angular/core/testing';

import { DrsParserService } from './drs-parser.service';

describe('DrsParserService', () => {
  let service: DrsParserService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(DrsParserService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
