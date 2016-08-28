"use strict";
var Article_1 = require('./Article');
var Feed = (function () {
    function Feed(obj) {
        this.title = obj.title;
        this.link = obj.link;
        this.articles = [];
        for (var i in obj.articles) {
            this.articles.push(new Article_1.Article(obj.articles[i], this.title));
        }
    }
    return Feed;
}());
exports.Feed = Feed;
//# sourceMappingURL=Feed.js.map