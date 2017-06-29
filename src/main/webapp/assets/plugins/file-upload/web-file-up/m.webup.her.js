/*
 * m.webup.her  上传文件 扩展方法
 * Date: 2016.02.26
 * Version: 0.0.1
 */
// 上传文件


var mwebupher = function ()
{
    "gff-funs";
    return {
        c_fobj: {
            f: WebUploader,
            op: {
                swf: 'img/Uploader.swf',
                server: '',
                button: '<div  class="wup-add"></div>',
                pick: { id: '', multiple: !1 },
                resize: !1,
                accept: {
                    title: "Images",
                    extensions: "png,jpg,jpeg",
                    //mimeTypes: "image/*"
                    /*参照 https://github.com/fex-team/webuploader/issues/1920 */
                    mimeTypes: 'image/png,image/jpg,image/jpeg' 
                },
                compress: !1,
                auto: !0,
                fileNumLimit: 10,
                fileSizeLimit: 1024 * 1024 * 1024*2,
                fileSingleSizeLimit: 1024 * 1024 * 2,    //2M,
                formData: { }
            },
        },
        // 单张icon上传 p_posturl 上传地址    p_id 控件id
        c_SingleIconUploader: function (p_posturl, p_id, p_callback)
        {
            var self = this, $ = jQuery;
            var options = self.c_fobj.op;
            options.accept = {
                title: "Images",
                extensions: "png,jpg,jpeg",
                //mimeTypes: "image/*"
                /*参照 https://github.com/fex-team/webuploader/issues/1920 */
                mimeTypes: 'image/png,image/jpg,image/jpeg' 
            };
            options.pick = { id: "#" + p_id, multiple: !1 },
            options.server = p_posturl;
            var a = self.c_fobj.f.create(options);
            a.on('fileQueued', function (file)
            {
                var $li = $('<div id="' + p_id + '_' + file.id + '" class="file-item">' +
                            '<div class="info">' + file.name + '</div>' +
                        '</div>');
                $("#" + p_id).find(".wup-add").html($li);

            });
            a.on('uploadProgress', function (file, percentage)
            {
                $("#" + p_id).find(".wup-add").addClass('wup-add-ed');
                var $li = $('#' + p_id + '_' + file.id),
                    $percent = $li.find('.progress span');
                if (!$percent.length)
                    $percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
                $percent.css('width', percentage * 100 + '%');
            });
            a.on('uploadSuccess', function (file, ret)
            {
                $('#' + p_id + '_' + file.id).addClass('upload-state-done');
                    p_callback ? p_callback(ret) : "";
            });
            a.on("error", function (e)
            {
                "Q_TYPE_DENIED" == e ? alert("您选择的文件格式不正确，请选择 图片！") : -1 != e.indexOf("EXCEED_SIZE") && alert("您选择的文件大小超出限制，请选择小于 200kb图片！")
            });
            a.on('uploadComplete', function (file)
            {
            });
        },
        // 多张图片上传
        c_MultipleImgUploader: function (p_posturl, p_id, p_callback)
        {
            var self = this, $ = jQuery;
            var options = self.c_fobj.op;
            options.accept = {
                title: "Images",
                extensions: "png,jpg,jpeg",
                //mimeTypes: "image/*"
                /*参照 https://github.com/fex-team/webuploader/issues/1920 */
                mimeTypes: 'image/png,image/jpg,image/jpeg' 
            };
            options.pick = { id: "#" + p_id, multiple: !0 },
            options.server = p_posturl;
            var a = self.c_fobj.f.create(options);
            a.on('fileQueued', function (file)
            {
                var $li = $('<div id="' + p_id + '_' + file.id + '" class="file-item">' +
                            '<div class="info">' + file.name + '</div>' +
                        '</div>');
                $("#" + p_id).before($li);

            });
            a.on('uploadProgress', function (file, percentage)
            {
                var $li_i = $('#' + p_id + '_' + file.id),
                    $percent = $li_i.find('.progress span');
                if (!$percent.length)
                    $percent = $('<p class="progress"><span></span></p>').appendTo($li_i).find('span');
                $percent.css('width', percentage * 100 + '%');
            });
            a.on('uploadSuccess', function (file, ret)
            {
                $('#' + p_id + '_' + file.id).addClass('upload-state-done').html("<img src='" + ret.fileurl + "' /><div class='f-item-oper' data-imgsrc='" + ret.fileurl + "'><i title='删除' class='fa fa-trash-o'></i></div>");
                p_callback ? p_callback(ret) : "";
            });
            a.on("error", function (e)
            {
                "Q_TYPE_DENIED" == e ? alert("您选择的文件格式不正确，请重新选择！") : -1 != e.indexOf("EXCEED_SIZE") && alert("您选择的文件大小超出限制，请重新选择！")
            });
            a.on('uploadComplete', function (file)
            {
            });
        },
        // 上传单张图片 上传
        c_SinPicImgUploader: function (p_posturl, p_id, p_callback)
        {
            var self = this, $ = jQuery;
            var options = self.c_fobj.op;
            options.accept = {
                title: "Images",
                extensions: "png,jpg,jpeg",
                //mimeTypes: "image/*"
                /*参照 https://github.com/fex-team/webuploader/issues/1920 */
                mimeTypes: 'image/png,image/jpg,image/jpeg' 
            };
            options.pick = { id: "#" + p_id, multiple: !0 },
            options.server = p_posturl;
            var a = self.c_fobj.f.create(options);
            a.on('fileQueued', function (file)
            {
                var $li = $('<div id="' + p_id + '_' + file.id + '" class="file-item">' +
                            '<div class="info">' + file.name + '</div>' +
                        '</div>');
                $("#" + p_id).find(".wup-add").html($li);

            });
            a.on('uploadProgress', function (file, percentage)
            {
                $("#" + p_id).find(".wup-add").addClass('wup-add-ed');
                var $li = $('#' + p_id + '_' + file.id),
                    $percent = $li.find('.progress span');
                if (!$percent.length)
                    $percent = $('<p class="progress"><span></span></p>').appendTo($li) .find('span');
                $percent.css('width', percentage * 100 + '%');
            });
            a.on('uploadSuccess', function (file,ret)
            {
                $('#' + p_id + '_' + file.id).addClass('upload-state-done');
                    p_callback ? p_callback(ret) : "";
            });
            a.on("error", function (e)
            {
                "Q_TYPE_DENIED" == e ? alert("您选择的文件格式不正确，请重新选择！") : -1 != e.indexOf("EXCEED_SIZE") && alert("您选择的文件大小超出限制，请重新选择！")
            });
            a.on('uploadComplete', function (file)
            {
            });
        },
        // 上传单张 小图片 
        c_SinSPicImgUploader: function (p_posturl, p_id, p_callback)
        {
            var self = this, $ = jQuery;
            var options = self.c_fobj.op;
            options.accept = {
                title: "Images",
                extensions: "png,jpg,jpeg",
                //mimeTypes: "image/*"
                /*参照 https://github.com/fex-team/webuploader/issues/1920 */
                mimeTypes: 'image/png,image/jpg,image/jpeg' 
            };
            options.pick = { id: "#" + p_id, multiple: !0 },
            options.server = p_posturl;
            options.fileSingleSizeLimit = 1024 * 1024 * 0.2; //200kb
            var a = self.c_fobj.f.create(options);
            a.on('fileQueued', function (file)
            {
                var $li = $('<div id="' + p_id + '_' + file.id + '" class="file-item">' +
                            '<div class="info">' + file.name + '</div>' +
                        '</div>');
                $("#" + p_id).find(".wup-add").html($li);

            });
            a.on('uploadProgress', function (file, percentage)
            {
                $("#" + p_id).find(".wup-add").addClass('wup-add-ed');
                var $li = $('#' + p_id + '_' + file.id),
                    $percent = $li.find('.progress span');
                if (!$percent.length)
                    $percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
                $percent.css('width', percentage * 100 + '%');
            });
            a.on('uploadSuccess', function (file, ret)
            {
                $('#' + p_id + '_' + file.id).addClass('upload-state-done');
                    p_callback ? p_callback(ret) : "";
            });
            a.on("error", function (e)
            {
                "Q_TYPE_DENIED" == e ? alert("您选择的文件格式不正确，请选择 图片！") : -1 != e.indexOf("EXCEED_SIZE") && alert("您选择的文件大小超出限制，请选择小于 200kb图片！")
            });
            a.on('uploadComplete', function (file)
            {
            });
        },
        c_ApkUploader: function (p_posturl, p_id, p_callback)
        {
            var self = this, $ = jQuery;
            var options = jQuery.extend(true, {}, self.c_fobj.op);
            options.accept = {
                title: "Android 程序安装包 (.apk)",
                extensions: "apk",
                mimeTypes: "application/vnd.android.package-archive"
            };
            options.pick = { id: "#" + p_id, multiple: !1 };
            options.button = '<div  class="wup-btn-add"></div>';
            options.server = p_posturl;
            options.fileSingleSizeLimit = 1024 * 1024 * 1024 * 2;    //2G

            var a = self.c_fobj.f.create(options);
            a.on('fileQueued', function (file)
            {
                $(".f-item-btn").length > 0 ? $(".f-item-btn").remove() : "";
                var $li = $('<div id="' + p_id + '_' + file.id + '" class=" f-item-btn">' +
                            '<div class="info" title="' + file.name + '">' + file.name + '</div>' +
                        '</div>');
                $("#" + p_id).after($li);
                $("#" + p_id).attr('disabled', 'disabled');

                var $percent = $li.find('.progress_s span');
                if (!$percent.length)
                    $percent = $('<div class="progress_s"><span></span></div>').appendTo($li).find('span');
                $percent.css('width', 0.05 * 100 + '%');

            });
            a.on('uploadProgress', function (file, percentage)
            {
                //$("#" + p_id).find(".wup-btn-add").addClass('wup-add-ed');
                var $li = $('#' + p_id + '_' + file.id),
                    $percent = $li.find('.progress_s span');
                if (!$percent.length)
                    $percent = $('<div class="progress_s"><span></span></div>').appendTo($li).find('span');
                if (percentage > 0.05)
                    $percent.css('width', (percentage - 0.05) * 100 + '%');
            });
            a.on('uploadSuccess', function (file, ret)
            {
               var  retColObj= $('#' + p_id + '_' + file.id).addClass('upload-state-done');
                p_callback ? p_callback(ret, retColObj) : "";
            });
            a.on("error", function (e)
            {
                e == 'F_DUPLICATE'?alert("当前已上传了 此文件！"):'';
                "Q_TYPE_DENIED" == e ? alert("您选择的文件格式不正确，请选择 APK！") : -1 != e.indexOf("EXCEED_SIZE") && alert("您选择的文件大小超出限制，请选择小于 2GB APK！");
            });
            a.on('uploadComplete', function (file)
            {
            });
        },
        c_ZipUploader: function (p_posturl, p_id, p_callback)
        {
            var self = this, $ = jQuery;
            var options = jQuery.extend(true, {}, self.c_fobj.op);
            options.accept = {
                title: "Android 程序资源包 (.zip)",
                extensions: "zip",
                mimeTypes: "application/x-zip-compressed"
            };
            options.pick = { id: "#" + p_id, multiple: !1 };
            options.button = '<div  class="wup-btn-add"></div>';
            options.server = p_posturl;
            options.fileSingleSizeLimit = 1024 * 1024 * 10;    //10M

            var a = self.c_fobj.f.create(options);
            a.on('fileQueued', function (file)
            {
                $(".f-item-btn").length > 0 ? $(".f-item-btn").remove() : "";
                var $li = $('<div id="' + p_id + '_' + file.id + '" class=" f-item-btn">' +
                            '<div class="info" title="' + file.name + '">' + file.name + '</div>' +
                        '</div>');
                $("#" + p_id).after($li);
                $("#" + p_id).attr('disabled', 'disabled');

                var $percent = $li.find('.progress_s span');
                if (!$percent.length)
                    $percent = $('<div class="progress_s"><span></span></div>').appendTo($li).find('span');
                $percent.css('width', 0.05 * 100 + '%');

            });
            a.on('uploadProgress', function (file, percentage)
            {
                //$("#" + p_id).find(".wup-btn-add").addClass('wup-add-ed');
                var $li = $('#' + p_id + '_' + file.id),
                    $percent = $li.find('.progress_s span');
                if (!$percent.length)
                    $percent = $('<div class="progress_s"><span></span></div>').appendTo($li).find('span');
                if (percentage > 0.05)
                    $percent.css('width', (percentage - 0.05) * 100 + '%');
            });
            a.on('uploadSuccess', function (file, ret)
            {
               var  retColObj= $('#' + p_id + '_' + file.id).addClass('upload-state-done');
                p_callback ? p_callback(ret, retColObj) : "";
            });
            a.on("error", function (e)
            {
                e == 'F_DUPLICATE'?alert("当前已上传了 此文件！"):'';
                "Q_TYPE_DENIED" == e ? alert("您选择的文件格式不正确，请选择 zip！") : -1 != e.indexOf("EXCEED_SIZE") && alert("您选择的文件大小超出限制，请选择小于 10M zip！");
            });
            a.on('uploadComplete', function (file)
            {
            });
        },
        c_VideoUploader: function (p_posturl, p_id, p_callback)
        {
            var self = this, $ = jQuery;
            var options = jQuery.extend(true, {}, self.c_fobj.op);
            options.accept = {
                title: "视频文件",
                extensions: "mp4,m4v,3gp,flv",
                mimeTypes: "application/octet-stream"
            };
            options.pick = { id: "#" + p_id, multiple: !1 };
            options.button = '<div  class="wup-btn-add"></div>';
            options.server = p_posturl;
            options.fileSingleSizeLimit = 1024 * 1024 * 1024 * 2;    //2G

            var a = self.c_fobj.f.create(options);
            a.on('fileQueued', function (file)
            {
                $(".f-item-btn").length > 0 ? $(".f-item-btn").remove() : "";
                var $li = $('<div id="' + p_id + '_' + file.id + '" class=" f-item-btn">' +
                            '<div class="info" title="' + file.name + '">' + file.name + '</div>' +
                        '</div>');
                $("#" + p_id).after($li);
                $("#" + p_id).attr('disabled', 'disabled');

                var $percent = $li.find('.progress_s span');
                if (!$percent.length)
                    $percent = $('<div class="progress_s"><span></span></div>').appendTo($li).find('span');
                $percent.css('width', 0.05 * 100 + '%');

            });
            a.on('uploadProgress', function (file, percentage)
            {
                //$("#" + p_id).find(".wup-btn-add").addClass('wup-add-ed');
                var $li = $('#' + p_id + '_' + file.id),
                    $percent = $li.find('.progress_s span');
                if (!$percent.length)
                    $percent = $('<div class="progress_s"><span></span></div>').appendTo($li).find('span');
                if (percentage > 0.05)
                    $percent.css('width', (percentage - 0.05) * 100 + '%');
            });
            a.on('uploadSuccess', function (file, ret)
            {
                var retColObj = $('#' + p_id + '_' + file.id).addClass('upload-state-done');
                p_callback ? p_callback(ret, retColObj) : "";
            });
            a.on("error", function (e)
            {
                e == 'F_DUPLICATE' ? alert("当前已上传了 此文件！") : '';
                "Q_TYPE_DENIED" == e ? alert("您选择的文件格式不正确，请选择 APK！") : -1 != e.indexOf("EXCEED_SIZE") && alert("您选择的文件大小超出限制，请选择小于 2GB APK！");
            });
            a.on('uploadComplete', function (file)
            {
            });
        },
        // 上传 单个 文件  @1 地址 @2 容器id @3 上传完成方法  @4 添加文件时方法
        c_SinExcelImgUploader: function (p_posturl, p_id, p_callback, p_callback_FQue)
        {
            var self = this, $ = jQuery;
            var options = self.c_fobj.op;
            options.accept = {
                title: "File",
                extensions: "xls,xlsx,csv",
                mimeTypes: "application/vnd.ms-excel,.xlsx"  //application/vnd.ms-excel
            };
            options.pick = { id: "#" + p_id, multiple: !0 };
            options.server = p_posturl;
            //options.fileSingleSizeLimit = 1024 * 1024 * 2; //2 M
            var a = self.c_fobj.f.create(options);
            a.on('fileQueued', function (file)
            {
                p_callback_FQue ? p_callback_FQue(file) : "";
                var $li = $('<div id="' + p_id + '_' + file.id + '" class="file-item">' +
                            '<div class="info">' + file.name + '</div>' +
                        '</div>');
                $("#" + p_id).find(".wup-add").html($li);

            });
            a.on('uploadProgress', function (file, percentage)
            {
                $("#" + p_id).find(".wup-add").addClass('wup-add-ed');
                var $li = $('#' + p_id + '_' + file.id),
                    $percent = $li.find('.progress span');
                if (!$percent.length)
                    $percent = $('<p class="progress"><span></span></p>').appendTo($li).find('span');
                $percent.css('width', percentage * 100 + '%');
            });
            a.on('uploadSuccess', function (file, ret)
            {
                $('#' + p_id + '_' + file.id).addClass('upload-state-done');
                    p_callback ? p_callback(ret) : "";
            });
            a.on("error", function (e)
            {
                "Q_TYPE_DENIED" == e ? alert("您选择的文件格式不正确") : -1 != e.indexOf("EXCEED_SIZE") && alert("您选择的文件大小超出限制")
            });
            a.on('uploadComplete', function (file)
            {
            });
        },
    }
}();