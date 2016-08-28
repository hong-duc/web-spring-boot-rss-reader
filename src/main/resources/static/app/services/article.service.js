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
var http_1 = require('@angular/http');
require('rxjs/add/operator/toPromise');
var HttpMethod_1 = require('../shared/HttpMethod');
var ArticleService = (function () {
    function ArticleService(http) {
        this.http = http;
        this.articleUrl = '/rss-app/article';
        this.sessionUser = 'myuser';
    }
    /**
     * hàm dùng để yêu cầu delete 1 article
     *
     * @param {Article} article
     * @returns {Promise<Article>}
     */
    ArticleService.prototype.deleteArticle = function (article) {
        var url = this.articleUrl.concat('/delete/', this.sessionUser, '/');
        var param = new http_1.URLSearchParams();
        param.append('feedTitle', article.feedTitle);
        param.append('articleTitle', article.title);
        console.log('the link: ' + url);
        return this.http.delete(url, param).then(function (res) { return article; })
            .catch(function (error) {
            console.error('deleteArticle error: ' + error);
            return null;
        });
    };
    /**
     * hàm dùng để yêu cầu delete các
     * articles
     *
     * @param {Article[]} articles
     * @returns {Promise<Article[]>}
     */
    ArticleService.prototype.deleteArticles = function (articles) {
        var url = this.articleUrl.concat('/deletes/', this.sessionUser, '/');
        var param = new http_1.URLSearchParams();
        param.append('feedTitle', articles[0].feedTitle);
        var json = [];
        articles.forEach(function (a) { return json.push(a.title); });
        return this.http.put(JSON.stringify(json), url, param)
            .then(function (res) {
            console.log('deleteArticles response body: ' + res.text());
            return articles;
        })
            .catch(function (error) {
            console.error('deleteArticles error: ' + error);
            return null;
        });
    };
    /**
     * hàm dùng để yêu cầu update giá trị isRead
     * của articles
     *
     * @param {Article[]} articles các articles cần update
     * @returns {Promise<Article[]>}
     */
    ArticleService.prototype.updateArticles = function (articles) {
        var url = this.articleUrl.concat('/updates/', this.sessionUser, '/');
        var param = new http_1.URLSearchParams();
        param.append('feedTitle', articles[0].feedTitle);
        var json = [];
        articles.forEach(function (a) { return json.push(a.title); });
        return this.http.put(JSON.stringify(json), url, param)
            .then(function (res) { return articles; })
            .catch(function (error) {
            console.error('updateArticles error: ' + error);
            return null;
        });
    };
    ArticleService.prototype.updateArticle = function (article) {
        var url = this.articleUrl.concat('/update/', this.sessionUser, '/');
        var param = new http_1.URLSearchParams();
        param.append('feedTitle', article.feedTitle);
        param.append('articleTitle', article.title);
        param.append('isRead', '' + article.isRead);
        return this.http.put('', url, param)
            .then(function (res) { return article; })
            .catch(function (error) {
            console.error('updateArticle error: ' + error);
            return null;
        });
    };
    ArticleService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [HttpMethod_1.HttpMethod])
    ], ArticleService);
    return ArticleService;
}());
exports.ArticleService = ArticleService;
//# sourceMappingURL=article.service.js.map