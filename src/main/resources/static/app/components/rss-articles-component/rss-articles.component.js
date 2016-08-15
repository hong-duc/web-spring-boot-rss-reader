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
var RSS_1 = require('../../model/RSS');
var RssArticlesComponent = (function () {
    function RssArticlesComponent() {
        this.gioihan = 50;
    }
    RssArticlesComponent.prototype.ngOnInit = function () {
        this.articles = this.rss.articles;
        this.count = this.articles.length;
        this.message = this.gioihan - 49 + '-' + this.gioihan + ' of ' + this.count;
    };
    RssArticlesComponent.prototype.ngOnChanges = function (changes) {
        for (var proName in changes) {
            switch (proName) {
                case 'rss':
                    this.rss = changes['rss'].currentValue;
                    this.articles = this.rss.articles.slice(0, 50);
                    break;
                default:
                    break;
            }
        }
    };
    RssArticlesComponent.prototype.tangGioiHan50 = function () {
        this.gioihan += 50;
        this.articles = this.rss.articles.slice(this.gioihan - 50, this.gioihan);
    };
    RssArticlesComponent.prototype.giamGioiHan50 = function () {
        this.gioihan -= 50;
        this.articles = this.rss.articles.slice(this.gioihan - 50, this.gioihan);
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', RSS_1.RSS)
    ], RssArticlesComponent.prototype, "rss", void 0);
    RssArticlesComponent = __decorate([
        core_1.Component({
            selector: 'rss-articles',
            templateUrl: '/app/components/rss-articles-component/rss-articles.component.html'
        }), 
        __metadata('design:paramtypes', [])
    ], RssArticlesComponent);
    return RssArticlesComponent;
}());
exports.RssArticlesComponent = RssArticlesComponent;
//# sourceMappingURL=rss-articles.component.js.map