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
var article_service_1 = require('../../services/article.service');
var SortRule;
(function (SortRule) {
    SortRule[SortRule["ByRead"] = 0] = "ByRead";
    SortRule[SortRule["ByNewest"] = 1] = "ByNewest";
    SortRule[SortRule["ByABC"] = 2] = "ByABC";
})(SortRule || (SortRule = {}));
var RssArticlesComponent = (function () {
    function RssArticlesComponent(articleService) {
        this.articleService = articleService;
        this.isCheckAll = false;
        this.sortRule = SortRule;
        this.count = 0;
    }
    RssArticlesComponent.prototype.ngOnInit = function () {
        console.log('init RssArticlesComponent');
    };
    RssArticlesComponent.prototype.ngOnChanges = function (changes) {
        for (var proName in changes) {
            switch (proName) {
                case 'articles':
                    this.changeArticle(changes[proName].currentValue);
                    console.log('rss change in RssArticlesComponent');
                    break;
                default:
                    break;
            }
        }
    };
    RssArticlesComponent.prototype.changeArticle = function (value) {
        if (typeof this.articles !== 'undefined') {
            this.count = this.articles.length;
            if (value.filter(function (a) { return a.checked; }).length === value.length && value.length > 0) {
                this.isCheckAll = true;
            }
            else {
                this.isCheckAll = false;
            }
        }
    };
    RssArticlesComponent.prototype.checkAll = function (checked) {
        console.log('run check all');
        this.articles.forEach(function (a) { return a.checked = checked; });
    };
    RssArticlesComponent.prototype.unCheckAll = function () {
        this.articles.forEach(function (a) { return a.checked = false; });
        this.isCheckAll = false;
    };
    RssArticlesComponent.prototype.checkRead = function () {
        this.unCheckAll();
        this.articles.forEach(function (a) {
            if (a.isRead) {
                a.checked = true;
            }
        });
    };
    RssArticlesComponent.prototype.checkUnRead = function () {
        this.unCheckAll();
        this.articles.forEach(function (a) {
            if (!a.isRead) {
                a.checked = true;
            }
        });
    };
    RssArticlesComponent.prototype.makeCheckedRead = function () {
        var updateArticles = [];
        this.articles.forEach(function (a) {
            if (a.checked && !a.isRead) {
                a.isRead = true;
                updateArticles.push(a);
            }
        });
        if (updateArticles.length > 0) {
            this.unCheckAll();
            this.articleService.updateArticles(updateArticles)
                .then(function (a) {
                if (a === null) {
                    alert('co loi khi update articles');
                }
            });
        }
    };
    RssArticlesComponent.prototype.makeRead = function (article) {
        if (!article.isRead) {
            article.isRead = true;
            this.articleService.updateArticle(article)
                .then(function (article) {
                if (article === null) {
                    alert('co loi khi update');
                }
            });
        }
    };
    RssArticlesComponent.prototype.deleteChecked = function () {
        var _this = this;
        var articles = this.articles.filter(function (a) { return a.checked; });
        if (articles.length !== 0) {
            this.unCheckAll();
            this.articleService.deleteArticles(articles)
                .then(function (a) {
                if (a !== null) {
                    _this.deleteArticles(a);
                }
                else {
                    alert('có lỗi xảy ra không xóa được mở console để biết thêm');
                }
            });
        }
    };
    RssArticlesComponent.prototype.deleteArticles = function (articles) {
        var _this = this;
        articles.forEach(function (a) {
            var i = _this.articles.indexOf(a);
            _this.articles.splice(i, 1);
        });
    };
    RssArticlesComponent.prototype.sort = function (rule) {
        switch (rule) {
            case this.sortRule.ByRead:
                this.articles.sort(this.SortByRead);
                break;
            case this.sortRule.ByNewest:
                this.articles.sort(this.SortByNewest);
                break;
            case this.sortRule.ByABC:
                this.articles.sort(this.SortByABC);
                break;
            default:
                this.articles.sort(this.SortByABC);
                break;
        }
    };
    RssArticlesComponent.prototype.SortByRead = function (a1, a2) {
        if (!a1.isRead) {
            return -1;
        }
        else {
            return 1;
        }
    };
    RssArticlesComponent.prototype.SortByNewest = function (a1, a2) {
        if (a1.publishDate > a2.publishDate) {
            return -1;
        }
        else {
            return 1;
        }
    };
    RssArticlesComponent.prototype.SortByABC = function (a1, a2) {
        if (a1.title > a2.title) {
            return 1;
        }
        else {
            return -1;
        }
    };
    __decorate([
        core_1.Input(), 
        __metadata('design:type', Array)
    ], RssArticlesComponent.prototype, "articles", void 0);
    RssArticlesComponent = __decorate([
        core_1.Component({
            selector: 'feed-articles',
            templateUrl: '/app/components/rss-articles-component/rss-articles.component.html'
        }), 
        __metadata('design:paramtypes', [article_service_1.ArticleService])
    ], RssArticlesComponent);
    return RssArticlesComponent;
}());
exports.RssArticlesComponent = RssArticlesComponent;
//# sourceMappingURL=rss-articles.component.js.map