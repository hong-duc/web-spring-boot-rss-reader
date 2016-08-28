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
require('rxjs/add/operator/toPromise');
var index_1 = require('../index');
var HttpMethod_1 = require('../shared/HttpMethod');
var manyRss = [];
var RssService = (function () {
    function RssService(http) {
        this.http = http;
        this.rssUrl = '/rss-app';
        this.sessionUser = 'myuser';
    }
    // get array of rss
    RssService.prototype.getManyRss = function () {
        return this.http.get(this.rssUrl + '/' + this.sessionUser)
            .then(function (res) {
            var obj = JSON.parse(res.text(), index_1.Utility.parseJsonToFeed);
            var feeds = [];
            for (var i in obj) {
                feeds.push(new index_1.Feed(obj[i]));
            }
            return feeds;
        })
            .catch(function (err) {
            console.error('getManyRss: error ' + err);
            return null;
        });
    };
    // add new link
    RssService.prototype.addLink = function (link) {
        var json = {
            'link': link,
            'user': this.sessionUser
        };
        return this.http.post(JSON.stringify(json), this.rssUrl).then(function (res) {
            var obj = JSON.parse(res.text(), index_1.Utility.parseJsonToFeed);
            var feed = new index_1.Feed(obj);
            return feed;
        }).catch(function (err) {
            console.error('addLink: an error ' + err);
            return null;
        });
    };
    // refesh the feed
    RssService.prototype.refeshFeed = function (feed) {
        var json = {
            'user': this.sessionUser,
            'feed': feed
        };
        return this.http.put(JSON.stringify(json), this.rssUrl)
            .then(function (res) {
            var obj = JSON.parse(res.text(), index_1.Utility.parseJsonToFeed);
            var feed = new index_1.Feed(obj);
            return feed;
        })
            .catch(function (error) {
            console.error('refeshFeed: ' + error);
            return null;
        });
    };
    RssService.prototype.deleteFeed = function (feed) {
        var url = this.rssUrl + '?link=' + feed.link + '&user=' + this.sessionUser;
        return this.http.delete(url)
            .then(function (res) { return feed; })
            .catch(function (error) {
            console.error('deleteFeed co error: ' + error);
            return null;
        });
    };
    RssService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [HttpMethod_1.HttpMethod])
    ], RssService);
    return RssService;
}());
exports.RssService = RssService;
//# sourceMappingURL=rss.service.js.map