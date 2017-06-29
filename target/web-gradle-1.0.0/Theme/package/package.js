 				var $table = $('#data_table');
 				var $table_res = $("#data_table_res");
 				var p = 10;  
                var stop = 0; 
                $(document).ready(function ()
                {
                    sysdef.sysInit();
                    page_funs.p_init();
                    page_funs.p_upload_zip();
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
                                        else
                                            alert( p_data.message);
                                    },
                                    error: function (MLHttpRequest, textStatus, errorThrown)
                                    {
                                        console.log('loadFormValidater-error::' + errorThrown);
                                        alert("打包失败！");
                                    }
                                });
                            });
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
                            var oper_str = ' <button type="button" class="btn btn-info " onclick="page_funs.oper_download(' + row.id + ')" ><i class="fa fa-paste"></i> 下载</button>';
                           // +' <button type="button" class="btn btn-info " onclick="page_funs.oper_edit(' + row.id + ')" ><i class="fa fa-paste"></i> 替换</button>'
                            //+' <button type="button" class="btn btn-warning "  onclick="page_funs.oper_del(' +  row.id + ')" ><i class="fa fa-trash-o"></i> 删除</button>';
                            return oper_str;
                            //btn-white
                        },
                        oper_download: function (id)
                        {
                            var row = $table.bootstrapTable('getRowByUniqueId', id);
                            window.location.href = downloadUrl + row.url;
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
                        $("#txt_THEMENAME").val(rjson.data);
                    };
                    var posturl = serverUrl +"/pack/uploadZip";
                    mwebupher.c_ZipUploader(posturl, p_id, f_callBack);
                	
                }
                
       }
}();
