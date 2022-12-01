import * as i0 from '@angular/core';
import { Injectable, Component, NgModule } from '@angular/core';

class DrsParserService {
    constructor() { }
}
DrsParserService.ɵfac = function DrsParserService_Factory(t) { return new (t || DrsParserService)(); };
DrsParserService.ɵprov = /*@__PURE__*/ i0.ɵɵdefineInjectable({ token: DrsParserService, factory: DrsParserService.ɵfac, providedIn: 'root' });
(function () {
    (typeof ngDevMode === "undefined" || ngDevMode) && i0.ɵsetClassMetadata(DrsParserService, [{
            type: Injectable,
            args: [{
                    providedIn: 'root'
                }]
        }], function () { return []; }, null);
})();

class DrsParserComponent {
}
DrsParserComponent.ɵfac = function DrsParserComponent_Factory(t) { return new (t || DrsParserComponent)(); };
DrsParserComponent.ɵcmp = /*@__PURE__*/ i0.ɵɵdefineComponent({ type: DrsParserComponent, selectors: [["lib-drs-parser"]], decls: 2, vars: 0, template: function DrsParserComponent_Template(rf, ctx) {
        if (rf & 1) {
            i0.ɵɵelementStart(0, "p");
            i0.ɵɵtext(1, " drs-parser works! ");
            i0.ɵɵelementEnd();
        }
    }, encapsulation: 2 });
(function () {
    (typeof ngDevMode === "undefined" || ngDevMode) && i0.ɵsetClassMetadata(DrsParserComponent, [{
            type: Component,
            args: [{ selector: 'lib-drs-parser', template: `
    <p>
      drs-parser works!
    </p>
  ` }]
        }], null, null);
})();

class DrsParserModule {
}
DrsParserModule.ɵfac = function DrsParserModule_Factory(t) { return new (t || DrsParserModule)(); };
DrsParserModule.ɵmod = /*@__PURE__*/ i0.ɵɵdefineNgModule({ type: DrsParserModule });
DrsParserModule.ɵinj = /*@__PURE__*/ i0.ɵɵdefineInjector({});
(function () {
    (typeof ngDevMode === "undefined" || ngDevMode) && i0.ɵsetClassMetadata(DrsParserModule, [{
            type: NgModule,
            args: [{
                    declarations: [
                        DrsParserComponent
                    ],
                    imports: [],
                    exports: [
                        DrsParserComponent
                    ]
                }]
        }], null, null);
})();
(function () { (typeof ngJitMode === "undefined" || ngJitMode) && i0.ɵɵsetNgModuleScope(DrsParserModule, { declarations: [DrsParserComponent], exports: [DrsParserComponent] }); })();

const greet = (x) => {
    console.log(`Drs-parser greets you ${x} times.`);
    return x;
};

/*
 * Public API Surface of drs-parser
 */

/**
 * Generated bundle index. Do not edit.
 */

export { DrsParserComponent, DrsParserModule, DrsParserService, greet };
//# sourceMappingURL=drs-parser.mjs.map
//# sourceMappingURL=drs-parser.mjs.map
