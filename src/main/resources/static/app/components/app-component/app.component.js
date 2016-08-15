"use strict";
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var core_1 = require('@angular/core');
var forms_1 = require('@angular/forms');
var rss_service_1 = require('../../services/rss.service');
var AppComponent = (function () {
    function AppComponent(rssService) {
        this.rssService = rssService;
    }
    AppComponent.prototype.getListOfRss = function () {
        //this.rssService.getRss().then(rss => this.manyRss = rss);
        this.manyRss = this.rssService.getRss();
    };
    AppComponent.prototype.onSelect = function (rss) {
        this.selectedRss = rss;
    };
    AppComponent.prototype.onSubmit = function () {
        this.rssService.addLink(this.link);
        console.log("submit link: " + this.link);
    };
    AppComponent.prototype.ngOnInit = function () {
        this.getListOfRss();
        this.selectedRss = this.manyRss[0];
    };
    AppComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'app-root',
            templateUrl: '/app/components/app-component/app.component.html',
            directives: [forms_1.FORM_DIRECTIVES]
        }), 
        __metadata('design:paramtypes', [rss_service_1.RssService])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map