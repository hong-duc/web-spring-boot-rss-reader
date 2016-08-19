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
var rss_service_1 = require('../../services/rss.service');
var AppComponent = (function () {
    function AppComponent(rssService) {
        this.rssService = rssService;
    }
    AppComponent.prototype.getListOfRss = function () {
        var _this = this;
        console.log('get a list of Feed');
        this.rssService.getManyRss().then(function (rss) {
            if (rss !== null) {
                _this.feeds = rss;
            }
        });
    };
    AppComponent.prototype.onSelect = function (feed) {
        this.selectedFeed = feed;
        this.articles = this.selectedFeed.articles;
    };
    AppComponent.prototype.onSubmit = function () {
        var _this = this;
        this.rssService.addLink(this.link).then(function (rss) {
            if (rss === null) {
                console.log('onSubmit: co loi xay ra');
            }
            else {
                _this.feeds.push(rss);
                alert('them rss thanh cong');
                _this.link = '';
                console.log('onSubmit: them thanh cong');
            }
        });
        console.log('submit link: ' + this.link);
    };
    AppComponent.prototype.onDelete = function (rss) {
        console.log('delete this: ' + rss.title);
    };
    AppComponent.prototype.onRefesh = function (feed) {
        var _this = this;
        console.log('refesh this: ' + feed.title);
        this.rssService.refeshFeed(feed).then(function (feed) {
            if (feed !== null) {
                _this.getListOfRss();
            }
        });
    };
    AppComponent.prototype.ngOnInit = function () {
        console.log('init app component');
        this.getListOfRss();
    };
    AppComponent = __decorate([
        core_1.Component({
            moduleId: module.id,
            selector: 'app-root',
            templateUrl: '/app/components/app-component/app.component.html'
        }), 
        __metadata('design:paramtypes', [rss_service_1.RssService])
    ], AppComponent);
    return AppComponent;
}());
exports.AppComponent = AppComponent;
//# sourceMappingURL=app.component.js.map