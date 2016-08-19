"use strict";
var Article = (function () {
    function Article(obj) {
        this.title = obj.title;
        this.link = obj.link;
        this.publishDate = obj.publishDate;
    }
    Article.prototype.getDate = function () {
        var date = this.publishDate.getDate();
        var month = this.publishDate.getMonth() + 1;
        var year = this.publishDate.getFullYear();
        return date + '-' + month + '-' + year;
    };
    Article.prototype.toJSON = function () {
        var dateString = this.publishDate.toISOString();
        var json = {
            "title": this.title,
            "link": this.link,
            "publishDate": dateString
        };
        return json;
    };
    return Article;
}());
exports.Article = Article;
//# sourceMappingURL=Article.js.map