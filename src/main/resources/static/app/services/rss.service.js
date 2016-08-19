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
var Feed_1 = require('../model/Feed');
var Utility_1 = require('../model/Utility');
var manyRss = [];
var RssService = (function () {
    function RssService(http) {
        this.http = http;
        this.rssUrl = '/rss-app';
        this.sessionUser = 'myuser';
    }
    // get array of rss
    RssService.prototype.getManyRss = function () {
        return this.get(this.rssUrl + '/' + this.sessionUser)
            .then(function (res) {
            var obj = JSON.parse(res.text(), Utility_1.Utility.parseJsonToFeed);
            var feeds = [];
            for (var i in obj) {
                feeds.push(new Feed_1.Feed(obj[i]));
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
            "link": link,
            "user": this.sessionUser
        };
        return this.post(JSON.stringify(json), this.rssUrl).then(function (res) {
            var obj = JSON.parse(res.text(), Utility_1.Utility.parseJsonToFeed);
            var feed = new Feed_1.Feed(obj);
            return feed;
        }).catch(function (err) {
            console.error('addLink: an error ' + err);
            return null;
        });
    };
    // refesh the feed
    RssService.prototype.refeshFeed = function (feed) {
        var json = {
            "user": this.sessionUser,
            "feed": feed
        };
        return this.put(JSON.stringify(json), this.rssUrl)
            .then(function () { return feed; })
            .catch(function (error) {
            console.error('refeshFeed: ' + error);
            return null;
        });
    };
    // call get to server
    RssService.prototype.get = function (url) {
        return this.http.get(url)
            .toPromise()
            .then(function (res) {
            console.log('get status code: ' + res.status);
            // console.log('body response: ' + res.text());
            return res;
        })
            .catch(this.handleError);
    };
    // call post to server
    RssService.prototype.post = function (json, url) {
        console.log('post chay voi gia tri: ' + JSON.stringify(json));
        var headers = new http_1.Headers({
            'Content-Type': 'application/json'
        });
        return this.http.post(url, json, headers)
            .toPromise()
            .then(function (res) {
            console.log('post status code: ' + res.status);
            // console.log('body response: ' + res.text());
            return res;
        })
            .catch(this.handleError);
    };
    // call put to server
    RssService.prototype.put = function (json, url) {
        console.log('chay put voi gia tri: ' + json);
        var headers = new http_1.Headers({
            'Content-Type': 'application/json'
        });
        return this.http.put(url, json, headers)
            .toPromise()
            .then(function (res) {
            console.log('put status code: ' + res.status);
            // console.log('body response: ' + res.text());
            return res;
        })
            .catch(this.handleError);
    };
    // handle error
    RssService.prototype.handleError = function (error) {
        console.error('An error occurred', error.json());
        switch (error.status) {
            case 406:
                alert(error.json() || error);
                break;
            case 404:
                alert(error.json() || error);
                break;
            default:
                alert('có lỗi lạ: ' + (error.json() || error));
                break;
        }
        return Promise.reject(error);
    };
    RssService = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], RssService);
    return RssService;
}());
exports.RssService = RssService;
//# sourceMappingURL=rss.service.js.map