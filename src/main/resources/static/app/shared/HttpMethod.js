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
var HttpMethod = (function () {
    function HttpMethod(http) {
        this.http = http;
    }
    HttpMethod.prototype.get = function (url, param) {
        return this.http.get(url, { search: param })
            .toPromise()
            .then(function (res) {
            console.log('get status code: ' + res.status);
            // console.log('body response: ' + res.text());
            return res;
        })
            .catch(this.handleError);
    };
    // call post to server
    HttpMethod.prototype.post = function (json, url, param) {
        console.log('post chay voi gia tri: ' + JSON.stringify(json));
        var headers = new http_1.Headers({
            'Content-Type': 'application/json'
        });
        return this.http.post(url, json, { headers: headers, search: param })
            .toPromise()
            .then(function (res) {
            console.log('post status code: ' + res.status);
            // console.log('body response: ' + res.text());
            return res;
        })
            .catch(this.handleError);
    };
    // call put to server
    HttpMethod.prototype.put = function (json, url, param) {
        console.log('chay put voi gia tri: ' + json);
        var headers = new http_1.Headers({
            'Content-Type': 'application/json'
        });
        return this.http.put(url, json, { headers: headers, search: param })
            .toPromise()
            .then(function (res) {
            console.log('put status code: ' + res.status);
            // console.log('body response: ' + res.text());
            return res;
        })
            .catch(this.handleError);
    };
    // call delete to server
    HttpMethod.prototype.delete = function (url, param) {
        console.log('chay delete');
        return this.http.delete(url, { search: param })
            .toPromise()
            .then(function (res) {
            console.log('delete status code: ' + res.status);
            console.log('delete body: ' + res.text());
            return res;
        })
            .catch(this.handleError);
    };
    // handle error
    HttpMethod.prototype.handleError = function (error) {
        console.error('An error occurred', JSON.stringify(error.json()));
        var objError = error.json();
        switch (error.status) {
            case 406:
                alert(objError.message || error);
                break;
            case 404:
                alert(objError.message || error);
                break;
            default:
                alert('có lỗi lạ: ' + (objError.message || error));
                break;
        }
        return Promise.reject(error);
    };
    HttpMethod = __decorate([
        core_1.Injectable(), 
        __metadata('design:paramtypes', [http_1.Http])
    ], HttpMethod);
    return HttpMethod;
}());
exports.HttpMethod = HttpMethod;
//# sourceMappingURL=HttpMethod.js.map