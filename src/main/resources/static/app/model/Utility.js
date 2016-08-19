"use strict";
var Utility = (function () {
    function Utility() {
    }
    /**
     * chuyển từ json sang Feed
     */
    Utility.parseJsonToFeed = function (key, value) {
        // console.log('key: ' + key);
        // console.log('value: ' + value);
        if (key === 'publishDate') {
            var date = new Date(value);
            return date;
        }
        return value;
    };
    return Utility;
}());
exports.Utility = Utility;
//# sourceMappingURL=Utility.js.map