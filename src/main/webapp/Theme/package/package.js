 				var $table = $('#data_table');
 				var $table_res = $("#data_table_res");
 				var p = 10;  
                var stop = 0; 
                var isShowInfo = true;
                $(document).ready(function ()
                {
                    sysdef.sysInit();
                    page_funs.p_init();
                    page_funs.p_upload_zip();
                    page_funs.p_load_style();
                });

                var page_funs = function ()
                {
                    'gff-pagefuns';
                    return {
                    	p_init: function ()
                    	{
	                        var self = this;
	                        self.p_packLoadAndShowTable();
	                        self.loadFormValidater();
                    	},
                        p_add: function ()
                        {
                            $("#t").val('add');
                            $("#myModal_Title").html(' 添加');
                            sysdef.p_modal_show();
                            $('#package_info').data('bootstrapValidator').resetForm(true);
                            $('#package_info')[0].reset();
                            page_funs.p_get_icon_url();
                        },
                        p_selectApps:function()
                        {
                            sysdef.p_modal_show("#myModal_selectapps");
                            page_funs.p_packLoadAndShowTable_res();
                        },
                        //  加载数据
                        p_packLoadAndShowTable :function (params)
                        {
                            params = params ? params : '';
                            if ($table.hasClass('table')){
                                $table.bootstrapTable("refreshOptions", { url: serverUrl+'/pack/getHistory?'+params});}
                            else
                                $table.bootstrapTable({ url: serverUrl+'/pack/getHistory' });
                        },
                        loadFormValidater: function ()
                        {
                            $('#package_info').bootstrapValidator().on('success.form.bv', function (e)
                            {
                                e.preventDefault();
                                
                                var suffix = $("#txt_APPLICATIONID").val();
                             
                                var prefix = "com.mycheering.launcher.auto.";
                                $("#txt_APPLICATIONID").val(prefix+suffix);
                                
                                
                                var submData = $('#package_info').serialize();
                                var index = submData.lastIndexOf('=');
                                var t = submData.substring(index+1);
                                var url;
                                if (t=='add') {
									url = serverUrl+"/pack/generateApk?"+submData;
								}
                                
                                $('#myModal1').modal('show');  
                                page_funs.start_progress();
                                
                                $.ajax({
                                    type: "get",
                                    url: url,
                                    data: submData,
                                    success: function (data) //data {"message":"success","status":1}
                                    {
                                    	if (data==''){
											alert("没有权限！");
											return;
										}
                                        var p_data = eval('(' + data + ')');//data;
                                        if (p_data.status == '1')
                                        {
                                        	stop = 1;
                                            $('#package_info').data('bootstrapValidator').resetForm(true);
                                            $table.bootstrapTable("refresh");
                                            sysdef.p_modal_hide();
                                        }
                                        else{
                                        	//打包失败隐藏
                                            $('#myModal1').modal('hide');
                                        	alert( p_data.message + ":打包失败");
                                        }
                                            
                                    },
                                    error: function (MLHttpRequest, textStatus, errorThrown)
                                    {
                                        console.log('loadFormValidater-error::' + errorThrown);
                                        //打包失败隐藏
                                        $('#myModal1').modal('hide');
                                        alert("打包失败！");
                                        
                                    }
                                });
                            });
                        },
                        verify:function(){
                        	var suffix = $("#txt_APPLICATIONID").val();
                            var identifier= /^[A-Za-z_\$]+[A-Za-z_\$\\d]+$/;//判断字符串是否标识符
                            if(!identifier.test(suffix)||suffix.indexOf(" ")>-1){
                            	alert("应用包名不规范！\n不能以数字开头且不能包含空格");
                            }
                        },
                        isnum:function(){
                        	var channel = $("#txt_THEMECHANNEL").val();
                            var reg= /^[0-9]+$/;//判断渠道是否为数字
                            if(!reg.test(channel)){
                            	alert("渠道必须为整数！");
                            }
                        },
                        start_progress:function() {  
                            p += 4; 
                            if(stop==1){
                            	$("#test").css("width", "100%");
                            	setTimeout(" $('#myModal1').modal('hide'); ", 1000);
                            	return;
                            }
                            $("#test").css("width", p + "%");  
                            var timer = setTimeout("page_funs.start_progress()", 1000);  
                        },
                        oper_fun: function (val, row, index)
                        {
                            var oper_str = ' <button type="button" class="btn btn-info " onclick="page_funs.oper_download(' + row.id + ')" ><i class="fa fa-paste"></i> 下载</button>'
                           // +' <button type="button" class="btn btn-info " onclick="page_funs.oper_edit(' + row.id + ')" ><i class="fa fa-paste"></i> 替换</button>'
                            +' <button type="button" class="btn btn-warning "  onclick="page_funs.oper_del(' +  row.id + ')" ><i class="fa fa-trash-o"></i> 删除</button>';
                            return oper_str;
                            //btn-white
                        },
                        oper_download: function (id)
                        {
                            var row = $table.bootstrapTable('getRowByUniqueId', id);
                            window.location.href = downloadUrl + row.url;
                        },
                
                    oper_del: function (id)
                    {
                        if (confirm('确认删除？'))
                        {
                            var row = $table.bootstrapTable('getRowByUniqueId', id);
                            var p_id = id;
                            $.ajax({
                                type: "get",
                                url: serverUrl+"/pack/del",
                                data: { cid: p_id, t: 'del' },
                                success: function (data)
                                {
                                	if (data==''){
										alert("没有权限！");
										return;
									}
                                    var p_data = eval('(' + data + ')');//data;
                                    if (p_data.status == '1')
                                    {
                                        $table.bootstrapTable("refresh");
                                        sysdef.p_modal_hide();
                                    }
                                    else
                                        alert( p_data.message);
                                },
                                error: function (MLHttpRequest, textStatus, errorThrown)
                                {
                                    console.log('oper_del-error::' + errorThrown);
                                }
                            });
                        }
                    },
                p_search: function ()
                {
                    var taskname = $("#txt_namepkg").val();
                    var params='stxt=' + taskname;
                    page_funs.p_packLoadAndShowTable(params);
                },
              
                time_format: function (val, row, index)
            	{
            	    return val.substring(0,val.indexOf("."));
            	},
                p_upload_zip: function()
                {
                    
                    var p_id = "f_addADDRESS";
                    var f_callBack = function (ret, retColObj)
                    {
                        var rjson = ret;
                        $("button[type='submit']").removeAttr('disabled');
                        if(retColObj){
                        	retColObj[0].parentNode.removeChild(retColObj[0]);
                        }
                        if (rjson.data==null) {
							alert(rjson.message);
							return;
						}
                        var str = rjson.data;
                        if(str.indexOf('test')==0){
                        	alert("zip包名不能以test开头，请重命名后上传！")
                        	return;
                        }
                        $("#txt_THEMENAME").val(str);
                    };
                    var posturl = serverUrl +"/pack/uploadZip";
                    mwebupher.c_ZipUploader(posturl, p_id, f_callBack);
                	
                },
                p_show_info: function()
                {
                	if(isShowInfo){
                		isShowInfo=false;
                		alert('chrome浏览器中，如果点击上传卡顿或没反应，请禁用设置后重试：\nchrome设置-隐私设置和安全性-保护您和您的设备不受危险网站的侵害');
                	}
                },
                p_get_icon_url:function(){
                	
                	$.ajax({
                        type: "GET",
                        url: serverUrl+"/pack/getIconUrl",
                        //data: {appId:appId},
                        dataType: "json",
                        //async:false,
                        success: function(data){
                            /* $("#myCarousel").attr('style','display:inline;width:244px;height:264px;');*/
                             $(".carousel-indicators li").remove();
                             $(".carousel-inner div").remove();
                             for(var i = 0;i<data.length;i++){
                            	 if (i==0) {
                            		 $(".carousel-indicators").append("<li data-target='#myCarousel' data-slide-to='"+i+"' class='active'></li>");
                            		 $(".carousel-inner").append("<div class='item active' ><img id='" +data[i].iconId+ "' src='" +data[i].iconUrl+ "' style='width:244px;height:200px;' onclick='page_funs.p_select_style(this.id,this.src);'/></div>");
								 }else{
									 $(".carousel-indicators").append("<li data-target='#myCarousel' data-slide-to='"+i+"'></li>");
									 $(".carousel-inner").append("<div class='item' ><img id='" +data[i].iconId+ "' src='" +data[i].iconUrl+ "' style='width:244px;height:200px;' onclick='page_funs.p_select_style(this.id,this.src);'/></div>");
								 }
                            	
                             }
                             $("#myModal_AddOrEdit").attr("style","overflow:auto;display:block;");
                        }                  
                    });
                },
                p_select_style:function(iconId,iconUrl){
//                	if(confirm("确认选择此icon样式？")){
                    	$("#txt_ICONSTYLE").attr('value',iconUrl);
                    	$("#txt_ICONSTYLEID").attr('value',iconId);
//                	}
                },
                oper_imgurl: function (val, row, index)
                {
                    return '<a class="show-img" href="' + val + '" target="_blank" title="查看大图" ><img width="30" src="' + val + '" /></a>';
                },
                p_load_style:function(){
                	$(".sys_item_spec .sys_item_specpara").each(function(){
                		var i=$(this);
                		var p=i.find("ul>li");
                		p.click(function(){
                			if(!!$(this).hasClass("selected")){
                				$(this).removeClass("selected");
                				i.removeAttr("data-attrval");
//                				 $("#aaa").val("");
                			}else{
                				p.removeClass("selected");
                				$(this).addClass("selected");
                				i.attr("data-attrval",$(this).attr("data-aid"))
                			}
                		})
                	})
                }
       }
}();
