﻿/**
* n:sys.index
* v:0.0.1
* a:gff
* d:20151215
**/
//全局的ajax设置
$.ajaxSetup({
    //aysnc: false, // 默认同步加载
    contentType: "application/x-www-form-urlencoded;charset=utf-8",
    //请求失败遇到异常触发
    error: function (XMLHttpReques, textStatus, errorMsg)
    {
        //alert( '发送AJAX请求到"' + this.url + '"时出错[' + XMLHttpReques.status + ']：' + errorMsg );
    },
    //完成请求后触发。即在success或error触发后触发
    complete: function (XMLHttpRequest, textStatus)
    {
        //alert("XMLHttpRequest=" + XMLHttpRequest + "   textStatus=" + textStatus);

        if (XMLHttpRequest.status == 405)
        {
            //alert("登陆超时11");
            //window.parent.location.href = "/login.html";
        }
        else if (XMLHttpRequest.status == 406)
        {
            //window.document.body.innerHTML = "";
            //alert("没有权限");
            //window.parent.location.href = "/login.html";
        }
    }
});
// 方法组
var sysdef = function () {
    'gff-sysdef';
    return {
        sysInit: function () {
            var self = this;
            //self.p_msg_to_close();
            //self.p_msg_close();
        },
        //@id 需带#
        p_modal_show: function (id) {

            var cid = id ? id : '#myModal_AddOrEdit';
            //$(cid).modal({ backdrop: 'static' });
            //$(cid).modal({ backdrop: true });
            $(cid).modal({backdrop: 'static', keyboard: false});
            sysdef.p_is_modalIn = 1;
            $(cid).draggable({ handle: ".modal-header" });
            if ($(cid).find(".model_close").length > 0)
            {
                $(".model_close").off('click');
                $(".model_close").on('click', function ()
                {
                	
                    $(cid).modal('hide');
                });
            }
        },
        p_modal_hide: function (id) {
            var cid = id ? id : '#myModal_AddOrEdit';
            $(cid).modal('hide');
        },
        // 绑定下拉框 @id 按钮id @callback 选择完成 回调方法
        p_bind_sel:function(id,callback)
        {
            $(id).on("click", function ()
            {
                var c = $(this), c_sel = c.next(".dropdown-menu");
                c_sel.children('li').off('click');
                c_sel.children('li').on('click', function ()
                {
                    var c = $(this), c_btn = c.parent().prev(".dropdown-toggle"), c_child = c.children('a');
                    c_btn.children('.name').html(c_child.html());
                    var valid = c_child.attr('data-cid');
                    c_btn.val(valid);
                    callback ? callback(valid) : '';
                });
            });
        },
        // p_name 参数名
        p_url_pram: function (p_name) {
            var name, value;
            var str = location.href; //取得整个地址栏
            var num = str.indexOf("?")
            str = str.substr(num + 1); //取得所有参数   stringvar.substr(start [, length ]
            var arr = str.split("&"); //各个参数放到数组里
            for (var i = 0; i < arr.length; i++) {
                num = arr[i].indexOf("=");
                if (num > 0) {
                    name = arr[i].substring(0, num);
                    value = arr[i].substr(num + 1);
                    //this[name] = value;
                    if (name === p_name)
                        return value;
                }
            }
        }, //设置cookie
        p_setCookie: function (cname, cvalue, exdays)
        {
            var d = new Date();
            d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));  //1 天
            var expires = "expires=" + d.toUTCString();
            window.document.cookie = cname + "=" + cvalue + "; " + expires;
        },
        //获取cookie
        p_getCookie: function (cname)
        {
            var name = cname + "=";
            var ca = window.document.cookie.split(';');
            for (var i = 0; i < ca.length; i++)
            {
                var c = ca[i];
                while (c.charAt(0) == ' ') c = c.substring(1);
                if (c.indexOf(name) != -1) return c.substring(name.length, c.length);
            }
            return "";
        },
        //清除cookie
        p_clearCookie: function (name)
        {
            setCookie(name, "", -1);
        },
        htmlEncodeByRegExp: function (str)
        {
            var s = "";
            if (str.length == 0) return "";
            s = str.replace(/&/g, "&amp;");
            s = s.replace(/</g, "&lt;");
            s = s.replace(/>/g, "&gt;");
            s = s.replace(/ /g, "&nbsp;");
            //s = s.replace(/\'/g, "&#39;");
            s = s.replace(/\"/g, "&quot;");
            return s;
        },
    }
}();
