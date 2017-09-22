var app = {
    cache: [],
    getCatalog: function (data, callback) {
        var self = this;
        self.ajax("post", "catalog/cataList", data, callback);
    },
    getShopList: function (data, callback) {
        var self = this;
        self.ajax("post", "shop/getList", data, callback);
    },
    getSiteShopList:function (data,callback) {
        var self = this;
        self.ajax("post", "shop/getSiteList", data, callback);
    },
    getHotShop: function (data, callback) {
        var self = this;
        self.ajax("post", "shop/getList", data, callback);
    },
    getShopDetail: function (data, callback) {
        var self = this;
        self.ajax("get", "shop/getShop", data, callback);
    },
    getProductList: function (data, callback) {
        var self = this;
        self.ajax("post", "prod/getList", data, callback);
    },
    getProductDetail: function (data, callback) {
        var self = this;
        self.ajax("get", "prod/getProduct", data, callback);
    },
    getGeneralizeProduct: function (data, callback) {
        var self = this;
        self.ajax("post", "prod/getList", data, callback);
    },
    getMeta: function (data, callback) {
        var self = this;
        self.ajax("get", "meta/getMeta", data, callback);
    },
    deleteShop: function (data, callback) {
        var self = this;
        self.ajax("get", "shop/delete", data, callback);
    },
    deleteProduct: function (data, callback) {
        var self = this;
        self.ajax("get", "prod/delete", data, callback);
    },
    getSelectedNav: function (navId) {
        setTimeout(function () {
            $(".top-nav ul li").removeClass("top-nav-select");
            if(navId<=5){
                $('.top-nav ul li:eq('+navId+')').addClass("top-nav-select");
            }
        },100);

    },
    addProd: function (data, callback) {
        var self = this;
        self.ajax("post", "prod/add", data, callback);
    },
    updateProduct: function (data, callback) {
        var self = this;
        self.ajax("post", "prod/update", data, callback);
    },
    addShop: function (data, callback) {
        var self = this;
        self.ajax("post", "shop/add", data, callback);
    },
    updateShop: function (data, callback) {
        var self = this;
        self.ajax("post", "shop/update", data, callback);
    },
    batchProdDelete: function (data, callback) {
        var self = this;
        self.ajax("post", "prod/batch/delete", data, callback);
    },
    allProdDelete: function (data, callback) {
        var self = this;
        self.ajax("post", "prod/all/delete", data, callback);
    },
    generatorActicle: function (data, callback) {
        var self = this;
        self.ajax("post", "/article/gene/article", data, callback);
    },
    getArticleDetail: function (data, callback) {
        var self = this;
        self.ajax("post", "article/detail", data, callback);
    },
    deleteArticle: function (data, callback) {
        var self = this;
        self.ajax("get", "article/delete", data, callback);
    },
    getArticleList: function (data, callback) {
        var self = this;
        self.ajax("post", "article/search", data, callback);
    },
    getArticleCount: function (data, callback) {
        var self = this;
        self.ajax("post", "article/count", data, callback);
    },
    getWebGeneralize: function (data, callback) {
        var self = this;
        self.ajax("post", "article/webGeneralize", data, callback);
    },
    batchShopDelete: function (data, callback) {
        var self = this;
        self.ajax("post", "shop/batch/delete", data, callback);
    },
    allShopDelete: function (data, callback) {
        var self = this;
        self.ajax("post", "shop/all/delete", data, callback);
    },
    "ajax": function (type, url, data, callback) {
        var self = this;
        if (self.isParamEmpty(type) || self.isParamEmpty(url) || self.isParamEmpty(data)) {
            return;
        }
        if (type.toLowerCase() == "get") {
            //遍历data的json格式，拼成get参数类型(格式：{"id":"1","name":"name"})
            var param = "";
            if (data) {
                param += "?";
            }
            for (var key in data) {
                param += key + "=" + data[key] + "&";
            }
            param = param.substring(0, param.length - 1);

            $.ajax({
                type: 'get',
                url: url + param,
                success: callback
            });
        } else {
            $.ajax({
                type: "post",
                url: url,
                dataType: 'json',
                contentType: "application/json",
                data: JSON.stringify(data),
                success: callback
            });
        }
    },
    isParamEmpty: function (param) {
        if (param == "" || param == null) {
            console.log("请检查参数，不能为空");
            return true;
        }
    },
    backTop: function () {
        $(window).on('scroll', function () {
            if ($(window).scrollTop() > 100) {
                $('.back-to-top').css("display", 'block');
            } else {
                $('.back-to-top').css("display", 'none');
            }
        });
        $('.back-to-top').on('click', function (e) {
            $('html,body').animate({
                scrollTop: 0
            }, 700);
        });
    }
}

function getParameter(name) {
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    var r = window.location.search.substr(1).match(reg);
    if (r != null)return unescape(r[2]);
}


