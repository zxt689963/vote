var tool={
	defaultPageNo : 1,
	pageSize : 25,
	pageSize50 : 50,
	outTime:0.2,
	timer:undefined
}
// 默认异步错误函数
tool.defaultError =function() {	}
// 默认异步提交完成空处理方法
tool.defaultComplete = function(xhr,statusTxt) {
	if(xhr.status==886){
		//session失效弹窗提示
		var arr=tool.popConfirmWindow("用户失效","可能由于您长时间没操作本系统，请重新登录再操作！");
		var _btn=arr[1];
		_btn.click(function(){
			arr[0].window('close', true); 
			location.href="/manage/login";
		});
	}
}
/**
 * 对象置换，并将args作为参数传入
 * @param {} fn 作为参数的回调函数
 * @param {} args 参数数组
 */
tool.doCallBack = function(fn, args) {
	/**
	 * var data=args[0];
	 * 如果需要，可以将响应数据data进行统一过滤
	 */
	fn.apply(this, args);
}
/**
 * 异步请求成功回调函数，组装成实际所需响应函数
 * @param {} callback 实际所需回调函数
 * @param {} arrParam 参数数组
 */
tool.opeCallBack = function(callback, arrParam) {
	var do_self = this;
	return function(data) {
		var tempArr = [data];
		//数组置换追加并将data放入第一个位置
		Array.prototype.push.apply(tempArr, arrParam);
		tool.doCallBack(callback, tempArr);
	}
}
tool.defaultOpt={
	type : 'post',
	dataType : 'json',
	clearForm : false,
	resetForm : false,
	beforeSubmit : function() {},
	error : tool.defaultError,
	complete : tool.defaultComplete
}
/**
 * 异步请求
 * @param {} requestUrl 请求地址
 * @param {} callBack 响应成功后回调函数
 * @param {} reqParam 请求参数
 * @param {} arrParam 传递参数数组
 */
tool.asyncroReq=function(requestUrl,callBack,reqParam,arrParam){
	if(arrParam == undefined){
		arrParam=[];
	}
	if(reqParam == undefined){
		reqParam={};
	}
	var defaultOpts=tool.defaultOpt;
	var opt={
		url : requestUrl,
		data : {
			"json" : reqParam
		},
		success : tool.opeCallBack(callBack, arrParam)
	}
	$.extend(defaultOpts,opt);
	$.ajax(defaultOpts);
}
/**
 * form表单异步请求
 * @param {} requestUrl 请求地址
 * @param {} callBack 响应成功后回调函数
 * @param {} arrParam 传参数组
 * @param {} opts  组装异步请求 对象
 * @return {} 返回组装对象
 */
tool.asyncroReqByForm = function(requestUrl, callBack, arrParam,opts) {
	if(arrParam==undefined){
		arrParam=[];
	}
	if(opts==undefined){
		opts={};
	}
	var options = {
		url : requestUrl,
		type : 'post',
		dataType : 'json',
		clearForm : false,
		resetForm : false,
		beforeSubmit : function() {
			
		},
		error : tool.defaultError,
		complete : tool.defaultComplete,
		success : tool.opeCallBack(callBack, arrParam)
	};
	$.extend(options,opts);
	return options;
};
/**
 * @param {} paramName 静态页面 从地址栏中左边的参数名
 * @return {} 返回参数值
 */
tool.getParam = function(paramName) {
	var uri = window.location.search;
	var re = new RegExp("" + paramName + "=([^&?]*)", "ig");
	return ((uri.match(re)) ? (uri.match(re)[0].substr(paramName.length + 1)) : null);
}
tool.delaypop = function(msg,limittime){
	var _height = $(window).height();  
	var _width = $(document).width();
	var _html =$("<div id='loading' style='width:100%;height:100%;position:fixed;left:0;top:0;background-color: #777;"+
				"z-index: 1402;filter:alpha(opacity=80);opacity:0.8;-moz-opacity:0.8;overflow:hidden;'>"+ 
				"<div style='position:fixed;cursor:wait;width:300px;line-height:60px;height:60px;padding-left:32px;background:#fff url(/common/images/loading.gif) no-repeat scroll 8px 22px;border:2px solid #ccc;color:#000;'>"+ 
				msg+"</div></div>");
	$('body').prepend(_html);
	var _child=$("#loading").children(":eq(0)");
	var _cur_wid=_child.outerWidth();
	var _h=_child.height();
	var _top=(_height-_h)/3;
	var _left=(_width-_cur_wid)/2;
	_child.css({"left":_left+"px","top":_top+"px"});
	if(limittime!=undefined){
		var delaytime=limittime;
		tool.timer = setInterval(function() {
	        if (delaytime == 0) {
	        	$("#loading").remove();
	            clearInterval(tool.timer);
	        } else {
	        	delaytime--;
	        }
	    }, 1000);
	}
	
}
/*------------------------------------------分页搜索栏   开始-------------------------------------------------*/
/**
 * 组装分页栏
 * @param {} perSize分页大小
 * @param {} cur_pageno 跳转页码
 * @param {} total_count 总记录数
 * @param {} _outerbox 分页栏 包装父元素
 * @param {} jumpcallback 跳转函数
 */
tool.loadPageLabel = function(perSize, cur_pageno, total_count, _outerbox,jumpcallback) {
	var temp_count = parseInt(total_count);
	var temp_size = parseInt(perSize);
	var temp_pageno = parseInt(cur_pageno);
	var pageCount = 0;
	//计算 总页码
	if (total_count > 0) {
		pageCount = temp_count % temp_size == 0
				? temp_count / temp_size
				: Math.ceil(temp_count / temp_size);
	} else {
		temp_pageno = 0;
	}
	//父元素内清空
	_outerbox[0].innerHTML = '';
	var _cur_span = $("<span class=\"txt-cur-p\"></span>");
	_cur_span.html(temp_pageno);
	var _cur_label = $("<span class=\"txt-lbl\">/</span>");
	var _cur_total = $("<span class=\"txt-total-p\"></span>");
	_cur_total.html(pageCount);
	var _txt_label = $("<span class=\"txt-lbl\">页</span>");
	var _total_count = $("<span class=\"txt-lbl\"></span>");
	_total_count.html("共&nbsp;"+total_count+"&nbsp;条&nbsp;");
	_total_count.appendTo(_outerbox);
	_cur_span.appendTo(_outerbox);
	_cur_label.appendTo(_outerbox);
	_cur_total.appendTo(_outerbox);
	_txt_label.appendTo(_outerbox);
	
	if (pageCount > 0) {
		var _prev_a = $("<a></a>");
		_prev_a.addClass("btn-prev txt-btn");
		_prev_a.attr("href", "javascript:void(0)");
		_prev_a.html("上一页");
		var _next_a = $("<a></a>");
		_next_a.addClass("btn-next txt-btn");
		_next_a.attr("href", "javascript:void(0)");
		_next_a.html("下一页");
		var _jump_a = $("<a></a>");
		_jump_a.addClass("btn-jump txt-btn");
		_jump_a.attr("href", "javascript:void(0)");
		_jump_a.html("跳转");
		_jump_a.click(function(){
			var _left=tool.getElementLeft(_jump_a[0]);
			var _top=tool.getElementTop(_jump_a[0]);
			_top=_top+_jump_a.height();
			tool.jumpdetail(_jump_a, perSize, pageCount,_left,_top ,jumpcallback);
		});
		_prev_a.click(function() {
			var _cur_txt = $(this).attr("jump-txt");
			var cur_no = parseInt(_cur_txt);
			jumpcallback(perSize, _cur_txt);
		});
		_next_a.click(function() {
			var _cur_txt = $(this).attr("jump-txt");
			var cur_no = parseInt(_cur_txt);
			jumpcallback(perSize, _cur_txt);
		});
		if (temp_pageno > 1) {
			_prev_a.attr("jump-txt", temp_pageno - 1);
			_prev_a.appendTo(_outerbox);
		}
		if (temp_pageno < pageCount) {
			_next_a.attr("jump-txt", temp_pageno + 1);
			_next_a.appendTo(_outerbox);
		}
		//大于3页的出现跳转链接
		if (pageCount > 3) {
			_jump_a.attr("jump-txt", pageCount);
			_jump_a.appendTo(_outerbox);
			_jump_a.click(function() {
				var _cur_txt = $(this).attr("jump-txt");
				var cur_no = parseInt(_cur_txt);
				var target_pop_lay;
				if ($(".pop-pager").length == 0) {
					target_pop_lay = tool.jumpdetail($(this), temp_size, pageCount, jumpcallback);
				} else {
					target_pop_lay = $(".pop-pager:eq(0)");
				}
				var _top = tool.getElementTop($(this)[0])	+ $(this).parent().height();
				var _left = tool.getElementLeft($(this)[0])	+ $(this).width() - target_pop_lay.width();
				target_pop_lay.css({
					"left" : _left + 'px',
					"top" : _top + 'px'
				});
				target_pop_lay.show();
			});

		}

	}

}
/**
 * 分页栏 跳转页码框
 * @param {} parentObj 跳转标签对象
 * @param {} perSize 分页大小
 * @param {} pageCount 总页码
 * @param {} _left 定位---左边坐标
 * @param {} _top 定位---顶部坐标
 * @param {} callback 跳转实际页码回调函数
 * @return {} 返回弹窗层
 */
tool.jumpdetail = function(parentObj, perSize, pageCount,_left,_top,callback) {
	var pop_box=$("<div id=\"pop_pager\" class=\"pop-pager\"></div>");
	pop_box[0].innerHTML="";
	pop_box.append("跳转至");
	var _input = $("<input type=\"text\" page-size=\""+pageCount+"\">");
	var _a = $("<a class=\"pop-btn\" href=\"javascript:void(0)\">确定</a>");
	_input.keypress(function(event) {
		var eventObj = event || e;
		var keyCode = eventObj.keyCode || eventObj.which;
		if ((keyCode >= 48 && keyCode <= 57) || keyCode == 8) {
			return true;
		} else {
			return false;
		}
	});
	$(document).bind('click', function(e) {
		var e = e || window.event; // 浏览器兼容性
		var elem = e.target || e.srcElement;
		if ($(elem).html() == parentObj.text()|| $(elem).html() == pop_box.children().text()) {
			
		} else {
			pop_box.remove();
			$(this).unbind('click');
		}
	});
	pop_box.click(function(event) {
		event.stopPropagation();
	});
	_input.click(function(event) {
		event.stopPropagation();
	});
	//监听页码输入框属性变化，允许在总页码内范围内大小输入
	_input.bind('input propertychange', function(e) { 
		var cur_v=$(this).val();
		var max_no=$(this).attr("page-size");
		max_no=parseInt(max_no);
		if(isNaN(cur_v)){
			//输入非数字，直接赋值1
			cur_v=1;
		}else{
			if(cur_v>max_no){
				cur_v=max_no; 
			}else if(cur_v<1){
				cur_v=1; 
			}
		}
		$(this).val(cur_v);
	});
	//执行跳转点击
	_a.click(function() {
		if ($.trim(_a.prev().val()).length == 0) {
			_a.prev().focus();
		} else {
			callback(perSize, _a.prev().val());
			_a.parent().remove();
		}
	});
	_input.appendTo(pop_box);
	pop_box.append("页");
	_a.appendTo(pop_box);
	_input[0].focus();
	pop_box.css({"left":_left+'px',"top":_top+'px'});	
	pop_box.show();
	pop_box.appendTo($('body'));
	return pop_box;
}
/**
 * 生成 搜索框
 * @param {} searchwords 搜索关键词
 * @param {} default_tip 输入框提示语
 * @param {} _box 搜索框 内嵌父元素
 * @return {}  返回 输入框与搜索按钮组成的数组
 */
tool.getSearchBox = function(searchwords,default_tip,_box){
	_box[0].innerHTML='';
	var in_search=$("<input type=\"text\" name=\"searchkey\" class=\"search-input\" value=\""+searchwords+"\" placeholder=\""+default_tip+"\">");
	var _btn=$("<a href=\"javascript:void(0)\" class=\"x-btn search-btn\">搜 索</a>");
	in_search.appendTo(_box);
	_btn.appendTo(_box);
	var obj={
		keytarget : in_search,
		btntarget : _btn
	};
	return obj;
}
tool.getRadioTool = function(_box){
	var _span=$("<span class=\"radio\"></span>");
	var _input_en =$("<input type=\"radio\"  id=\"r_enable\" value=\"Y\" checked=\"checked\"  name=\"is_enable\" >");
	var _lbl_en =$("<label class=\"label-new\" for=\"r_enable\">启用</label>");
	var _input_dis =$("<input type=\"radio\"  id=\"r_disable\" value=\"\"  name=\"is_enable\" >");
	var _lbl_dis =$("<label class=\"label-new\" for=\"r_disable\">未启用</label>");
	_input_en.appendTo(_span);
	_lbl_en.appendTo(_span);
	_input_dis.appendTo(_span);
	_lbl_dis.appendTo(_span);
	_span.appendTo(_box);
	var _radio={};
	_radio.chk=_input_en;
	_radio.unchk=_input_dis;
	return _radio;
}
/**
 * 获取元素定位 靠左距离
 * @param {} element 目标元素
 * @return {} 返回 靠左距离（数字类型）
 */
tool.getElementLeft = function(element) {
	var actualLeft = element.offsetLeft;
	var current = element.offsetParent;
	while (current !== null) {
		actualLeft += current.offsetLeft;
		current = current.offsetParent;
	}
	return actualLeft;
}
/**
 * 获取元素定位 靠顶距离
 * @param {} element 目标元素
 * @return {} 返回 靠顶距离（数字类型）
 */
tool.getElementTop = function(element) {
	var actualTop = element.offsetTop;
	var current = element.offsetParent;
	while (current !== null) {
		actualTop += current.offsetTop;
		current = current.offsetParent;
	}
	return actualTop;
}
/*------------------------------------------分页搜索栏   结束-------------------------------------------------*/
/*------------------------------------------cookie 操作开始---------------------------------------------------*/
//设置cookie
tool.setCookie = function(cookieName, cookieValue) {
	var str = cookieName + "=" + cookieValue;
	if (tool.outTime < 0) {
		var mm = tool.outTime * 3600 * 1000 * 24;
		var date = new Date();
		date.setTime(date.getTime() + mm);
		str += ";expires=" + date.toGMTString();
	}
	document.cookie = str;
}
// 删除COOIKIE
tool.delCookie = function(cookieName) {
	var date = new Date();
	//为了删除指定名称的cookie，可以将其过期时间设定为一个过去的时间
	date.setTime(date.getTime() - 10000);
	document.cookie = cookieName + "=0;expires="+ date.toGMTString();
}
// 得到COOKIE的值
tool.getCookie = function(cookieName) {
	var arrCookie = document.cookie.split(";");
	var cur_cookie="";
	for (var i = 0; i < arrCookie.length; i++) {
		var arrName = arrCookie[i].split("=");
		if (arrName[0] == cookieName) {
			cur_cookie=arrName[1];
			break;
		}
	}
	return cur_cookie;
}
tool.setCookieByDynomic = function(cookieName, cookieValue) {
	var str = cookieName + "=" + cookieValue;
	var date = new Date();
	date.setTime(date.getTime() + 100);
	str += ";expires=" + date.toGMTString();
	document.cookie = str;
}
/*------------------------------------------cookie 操作结束---------------------------------------------------*/
/*-------------------------------------------数据表格封装函数 开始---------------------------------------------------*/
//获取grid 点击行id
tool.getLineId = function($gridObj,columnKey,callback,title){
	var row =$gridObj.datagrid('getSelections');
	var len=row.length;
	var keyParam="";
	if (len>0) {
		if(row[0][columnKey] ==undefined){
			len=0;
		}
	}
	if (len>0) {
		for(var i=0;i<len;i++){
			if(i>0){keyParam+=",";}
			keyParam+=row[i][columnKey];
		}
		if(keyParam.substr(0,1)==","){
			keyParam=keyParam.substr(1);
		}
		if($.trim(keyParam).length>0){
			callback(keyParam);
			return true;
		}
	}else{
		$.messager.alert('提示',title,'info');
		return false;
	}
}
//删除一行或多行
tool.delRow = function($gridObj,columnKey,callback,_btn,is_del_all){
	var row =$gridObj.datagrid('getSelections');
	var len=row.length;
	if (len>0) {
		if(row[0][columnKey] ==undefined){
			len=0;
		}
	}
	if (len>0) {
		if($.trim(columnKey).length==0||columnKey==null){//删除所有
				$.messager.confirm('确认','您确认要删除您选定的行么?',function(isConfirmed){
					if (isConfirmed){
						for(var i=len-1;i>-1;i--){
							var currentIndex=$gridObj.datagrid("getRowIndex",row[i]);
							$gridObj.datagrid('deleteRow', currentIndex);
						}
					}
					$gridObj.datagrid('clearSelections');
					$gridObj.datagrid('resize');
				});
		}else{
			var keyParam="";
			for(var i=0;i<len;i++){
				if(i>0){keyParam+=",";}
				keyParam+=row[i][columnKey];
			}
			if(keyParam.substr(0,1)==","){
				keyParam=keyParam.substr(1);
			}
			$.messager.confirm('确认','您确认要删除您选定的行么?',function(isConfirmed){
				if (isConfirmed){
					$gridObj.datagrid('clearSelections');
					$gridObj.datagrid('resize');
					callback(keyParam,_btn);
				}
			});
		}
		
	}else{
		$.messager.alert('提示','请选择至少一项!','info');
	}
};
tool.myview =function(){
	var view =  $.extend({},$.fn.datagrid.defaults.view,{
		onBeforeRender: function(target, rows){
			$.fn.datagrid.defaults.view.onBeforeRender.call(this,target);
	        var opts = $(target).datagrid('options');
	        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
	       // vc.children('div.datagrid-empty').remove();
	        if (!$(target).datagrid('getRows').length){
	            var d = $('<div class="datagrid-empty" style="border-bottom:1px dotted #ccc;line-height:80px;"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
	            d.css({
	                position:'absolute',
	                left:0,
	                top:50,
	                width:'100%',
	                textAlign:'center',
	                vAlign : "middle",
	                color: 'red',
	                display:'none'
	            });
	        }
		},
	    onAfterRender:function(target){
	    	$.fn.datagrid.defaults.view.onAfterRender.call(this,target);
	        var opts = $(target).datagrid('options');
	        var vc = $(target).datagrid('getPanel').children('div.datagrid-view');
	        vc.children('div.datagrid-empty').remove();
	        if (!$(target).datagrid('getRows').length){
	        	
	            var d = $('<div class="datagrid-empty" style="border-bottom:1px dotted #ccc;line-height:80px;"></div>').html(opts.emptyMsg || 'no records').appendTo(vc);
	            d.css({
	                position:'absolute',
	                left:0,
	                top:50,
	                width:'100%',
	                textAlign:'center',
	                vAlign : "middle",
	                color: 'red',
	                display:'none'
	            });
	           // vc.children('div.datagrid-empty').show()
	        }else{
	        	 vc.children('div.datagrid-empty').hide()
	        }
	    }
	});
	return view;
};
/**
 * 方法说明 用于创建grid,将一些常用默认配置项归纳，通过options扩展配置项，当options中存在默认配置项，则将覆盖默认配置项，否则将追加
 * @method createGrid
 * @param {object} el 对应的生成grid的目标对象
 * @param {object} options 对应的生成grid的配置项，
 * @return {object} 返回一个grid对象
 */
tool.createGrid = function($el,options){
	var defaultOpts = {
		fitColumns : true,
		fit:false,
		view: tool.myview(),
		emptyMsg: '没有记录',
		fixed:false,
		rownumbers:true,
		nowrap : true,
		singleSelect : false,
		selectOnCheck: true,
		checkOnSelect: true,
		showHeader : true,
		showFooter : false,
		onClickRow : function(index,row){
			$(this).datagrid("unselectAll")
		}
	};
	/*if(options.onDblClickRow){
		var oldFun  = options.onDblClickRow;
		options.onDblClickRow = function(index,row){
			var rows = $el.datagrid("getRows"),paramCount = 0,flag = false;
			if(rows.length == 1){
				for(k in rows[0]){
					paramCount ++;
					if(rows[0][k].indexOf("没有相关记录") > 0){
						flag = true;
						break;
					}
				}
				
			}
			if(paramCount == 1 && flag){
				return ;
			}
			oldFun(index,row);
		}
	}*/
	$.extend(defaultOpts,options);
	$el.datagrid(defaultOpts);
	$el.datagrid('clearSelections');
	$el.datagrid('clearChecked');
};
	/*
	 * $Obj,---grid 依赖对象
	 * jsonDB,----加载数据
	 * id_key,---主键ID
	 * load_msg----提示消息
	 * columnArr,-----列标题
	 * isFit,-----是否自动增大
	 * dbclkCallBack,----双击行 回调函数
	 * errorCallBack，----加载数据出错 回调函数
	 * */
tool.gridByFit = function($Obj,jsonDB,id_key,load_msg,columnArr,dbclkCallBack,errorCallBack){
	$Obj.datagrid({
		data : jsonDB,
		loadMsg : load_msg,
		fitColumns : true,
		fit:false,
		fixed:false,
		rownumbers:false,
		nowrap : true,
		singleSelect : false,
		selectOnCheck: true,
		checkOnSelect: true,
		showHeader : true,
		showFooter : false,
		idField:id_key,
		columns :columnArr,
		onDblClickRow: dbclkCallBack,
		onLoadError: errorCallBack
	});
	$Obj.datagrid('clearSelections');
	$Obj.datagrid('clearChecked');
};
tool.gridByDefault = function($Obj,jsonDB,id_key,load_msg,columnArr,dbclkCallBack,errorCallBack){
	$Obj.datagrid({
		data : jsonDB,
		loadMsg : load_msg,
		fitColumns : true,
		fit:false,
		fixed:true,
		rownumbers:false,
		nowrap : true,
		singleSelect : false,
		selectOnCheck: true,
		checkOnSelect: true,
		showHeader : true,
		showFooter : false,
		idField:id_key,
		columns :columnArr,
		onDblClickRow: dbclkCallBack,
		onLoadError: errorCallBack
	});
	$Obj.datagrid('clearSelections');
	$Obj.datagrid('clearChecked');
};
//生成树形表格
tool.treeGridByFit = function($Obj,jsonDB,id_key,tree_key,load_msg,columnArr,dbclkCallBack){
    $Obj.treegrid({
		data : jsonDB,
		loadMsg : load_msg,
		idField:id_key,
		treeField:tree_key,
		animate:true,
		checkbox:true,
		collapsible: true,
		lines : true,
		collapsible: true,
		fitColumns: true,
		columns :columnArr,
		onClickCell: dbclkCallBack
    });
}
//生成树形表格
tool.generateTreeGrid = function($obj,options){
    var defaultOpts = {
		animate:true,
		checkbox:true,
		collapsible: true,
		lines : true,
		collapsible: true,
		fitColumns: true
	};
	$.extend(defaultOpts,options);
	$obj.treegrid(defaultOpts);
//    $Obj.treegrid({
//		data : jsonDB,
//		loadMsg : load_msg,
//		idField:id_key,
//		treeField:tree_key,
//		animate:true,
//		checkbox:true,
//		collapsible: true,
//		lines : true,
//		collapsible: true,
//		fitColumns: true,
//		columns :columnArr,
//		onClickCell: dbclkCallBack
//    });
}
/*------------------------------------------数据表格封装函数 开始---------------------------------------------------*/
/*------------------------------------------日期格式化 开始---------------------------------------------------*/
/**
 * 格式化时间到天
 * @param {} time  传入时间对象或字符串
 * @return {String}
 */
tool.translateDate = function(time){
	if(time==undefined||time==''){
		return '';
	}else{
		var date=time;
		if( typeof time ==='string'&&time.indexOf("-")>-1){
			time = time.replace(/-/g,"/");
			date=new Date(time);
		}
		var y = date.getFullYear().toString();
		var m = (date.getMonth()+1).toString();
		var d = date.getDate().toString();
		
		if (m.length < 2){
			 m='0'+m;
		}
		if (d.length < 2){
			 d='0'+d;
		}
		return y+'-'+m+'-'+d;
	}
		
}
/**
 * 格式化时间到分
 * @param {} time  传入时间对象或字符串
 * @return {String}
 */
tool.translateDate2Minu = function(time){
	if(time==undefined||time==''){
		return '';
	}else{
		var date=time;
		if( typeof time ==='string'&&time.indexOf("-")>-1){
			time = time.replace(/-/g,"/");
			date=new Date(time);
		}
		var y = date.getFullYear().toString();
		var m = (date.getMonth()+1).toString();
		var d = date.getDate().toString();
		var h = date.getHours().toString();
		var minu = date.getMinutes().toString();
		if (m.length < 2){
			 m='0'+m;
		}
		if (d.length < 2){
			 d='0'+d;
		}
		if (h.length < 2){
			 h='0'+h;
		}
		if (minu.length < 2){
			 minu='0'+minu;
		}
		return y+'-'+m+'-'+d+' '+h+':'+minu;
	}	
}
/*------------------------------------------日期格式化 结束---------------------------------------------------*/
/*------------------------------------------浮点数计算 开始---------------------------------------------------*/
//浮点数相加
tool.floatAdd = function(arg1,arg2){
	var r1,r2,m;
	try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0}
	try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0}
	m=Math.pow(10,Math.max(r1,r2))
	return (arg1*m+arg2*m)/m;
}
//强制转化为保留两位小数为的浮点数
tool.changeXDecimalFloat = function (no,x) {
	//no 保留小数位
	//x  元数据
	var f_x = parseFloat(x);
    if (isNaN(f_x)) {
        return '';
    }else{
    	var f_x = Math.round(x * 100) / 100;
	    var s_x = f_x.toString();
	    var pos_decimal = s_x.indexOf('.');
	    if (pos_decimal < 0) {
	        pos_decimal = s_x.length;
	        s_x += '.';
	    }
	    while (s_x.length <= pos_decimal + no) {
	        s_x += '0';
	    }
	    return s_x;
    }	    
}
/*------------------------------------------浮点数计算 结束---------------------------------------------------*/
/**
 * 根据分隔符‘-’取左右值
 * @param {} serno 左右索引，0-左边；1-右边
 * @param {} cls_Str 传入值
 * @return {} 返回指定值
 */
tool.analyzeStr = function(serno,cls_Str){
	var backStr="";
	if(cls_Str.indexOf("-")>0&&serno>-1&&serno<2){
		var arrStr=cls_Str.split("-");
		if(serno==0){
			backStr=arrStr[0];
		}else if(serno==1){
			backStr=arrStr[1];
		}		
	}
	return backStr;
}
/*------------------------------------------弹窗函数  开始---------------------------------------------------*/
/**
 * 修改密码弹出框
 * @param {} box_title 弹出框标题
 * @return {} 返回 (is_new_build-是否第一次生成窗口，$win-弹出层，_sub-确认按钮)组成的数组
 */
tool.passLay = function(box_title){
	var _self=this;
	var _dialog_lay ,_sub;
	var is_new_build=false;
	if($("#usr_dialog").length>0){
		_dialog_lay=$("#usr_dialog");
		_sub=$(".sub-tip:eq(0)");
		_dialog_lay.find("input").val("");
    	_dialog_lay.find(".tip-err-msg").hide();
    	_dialog_lay.find(".tip-err-msg").html("");
    	_dialog_lay.find(".tip-content").removeClass("tip-content");
	}else{
		is_new_build=true;
		_dialog_lay=$("<div id=\"usr_dialog\" class=\"usr-dialog\"></div>");
		var _unit0=$("<div class=\"usr-line\"></div>");
		var _left_titile0=$("<label>原密码：</label>");
		_left_titile0.appendTo(_unit0);
		var ori_name="password";
		var _ori_in=$("<input class=\"in-content\" data-msg=\"请输入密码\" type=\"password\">");
		_ori_in.attr("name",ori_name);
		_ori_in.appendTo(_unit0);
		var _tip0=$("<div class=\"tip-err-msg\"></div>");
		_tip0.attr("for-label",ori_name);
		_tip0.appendTo(_unit0);
		_unit0.appendTo(_dialog_lay);
		_self.tipcls(_ori_in);
		
		var _unit1=$("<div class=\"usr-line\"></div>");
		var _left_titile0=$("<label>新密码：</label>");
		_left_titile0.appendTo(_unit1);
		var new_name="newPassword";
		var new_id="newpass";
		var _new_in=$("<input class=\"in-content\" id=\"newpass\" data-msg=\"密码长度不少于6位\" type=\"password\">");
		_new_in.attr("name",new_name);
		_new_in.attr("id",new_id);
		_new_in.appendTo(_unit1);
		var _tip1=$("<div class=\"tip-err-msg\"></div>");
		_tip1.attr("for-label",new_name);
		_tip1.appendTo(_unit1);
		_unit1.appendTo(_dialog_lay);
		_self.tipcls(_new_in);
		
		var _unit2=$("<div class=\"usr-line\"></div>");
		var _left_titile1=$("<label>确认密码：</label>");
		_left_titile1.appendTo(_unit2);
		var repeat_name="moreNewPassword";
		var _new_in_repeat=$("<input class=\"in-content\" data-target=\""+new_id+"\" data-msg=\"密码输入不一致\" type=\"password\">");
		_new_in_repeat.attr("name",repeat_name);
		_new_in_repeat.appendTo(_unit2);
		var _tip2=$("<div class=\"tip-err-msg\"></div>");
		_tip2.attr("for-label",repeat_name);
		_tip2.appendTo(_unit2);
		_unit2.appendTo(_dialog_lay);
		_self.tipcls(_new_in_repeat);
		
		var _unit3=$("<div class=\"usr-line\"></div>");
		var tip_name="response_tip";
		var _tip3=$("<div class=\"tip-err-msg sub-tip\"></div>");
		_tip3.attr("for-label",tip_name);
		_tip3.appendTo(_unit3);
		_sub=$("<a href=\"javascript:void(0)\" class=\"sub-link\"></a>");
		_sub.html("确认");
		_sub.appendTo(_unit3);
		_unit3.appendTo(_dialog_lay);
		_dialog_lay.appendTo($('body'));
	}
	_dialog_lay.show();
	var $win=_self.popWindow(_dialog_lay,box_title,'30%');
	var arr=new Array();
	arr.push(is_new_build,$win,_sub);
	return arr;
}
/**
 * 获取焦点和失去焦点时 操作提示框
 * @param {} target 目标对象
 */
tool.tipcls = function(target){
	target.focus(function(){
		$(this).removeClass("tip-content");
		$(this).next().html("");
		$(this).addClass("in-tip");
	});
	target.blur(function(){
		$(this).removeClass("in-tip");
	});
}
/**
 * 生成确认弹出框
 * @param {} box_title 弹出框标题
 * @param {} content_tip 弹出框内容提示
 * @return {} 返回 ($win-弹出框对象,_btn_l-确认按钮 )数组
 */
tool.popConfirmWindow = function(box_title,content_tip){
	var _btn_l,_btn_r,_tip_box,_tip_title;
	if($("#dialogConfirm").length>0){
		$("#dialogConfirm")[0].innerHTML='';
		$("#dialogConfirm").remove();
	}
	_btn_l=$("<a></a>");
	_btn_l.addClass("x-btn warn-btn");
	_btn_l.html("确认");
	
	_btn_r=$("<a></a>");
	_btn_r.addClass("x-btn warn-btn ");
	_btn_r.html("取消");
	
	_tip_title=$("<div></div>");
	_tip_title.html(content_tip);
	_tip_title.addClass("pop-title");
	
	var _btn_line = $("<div ></div>");
	_btn_line.addClass("btn-line");
	var _tip_box=$("<div  id=\"dialogConfirm\"></div>");
	_tip_box.addClass("pop-window-box");
	_tip_title.appendTo(_tip_box);
	_btn_l.appendTo(_btn_line);
	_btn_r.appendTo(_btn_line);
	_btn_line.appendTo(_tip_box);
	_tip_box.appendTo($('body'));
	_tip_box.show();
	
	var $win=tool.popWindow(_tip_box,box_title);
	$win.window('open');
	_btn_r.click(function(){
		$win.window('close', true); 
	});
	var arr=new Array();
	arr.push($win,_btn_l);
	return arr;
}
/**
 * 弹出框封装函数
 * @param {} _box 封装依赖盒子元素
 * @param {} box_title弹出框标题
 * @param {} _wid 显示宽度，如果未传入获取依赖元素宽度
 * @return {} 返回弹出框对象
 */
tool.popWindow = function(_box,box_title,_wid,options){
	if(_wid ==undefined){
		_box.width();
	}
	var _options={
		shadow: true,
		modal: true,
		title:box_title,
		width:_wid,
		top:200,
		collapsible:false,
		closable:true,
		closed:true,
		resizable:false,
		minimizable:false,
		maximizable:false,
		draggable:true,
		inline:false,
		onBeforeClose:function(){ 
			$win.window('close', true); 
		}
	}
	if(options!=undefined){
		$.extend(_options,options);
	}
	$win=_box.window(_options);
	return $win;
}
tool.assessWindow = function(_box,box_title,_wid,_hei){
	$win=tool.popWindow(_box,box_title,_wid,{height:_hei});
	return $win;
}
/*------------------------------------------弹窗函数  结束---------------------------------------------------*/
tool.getDateTime = function(){
	var dt = new Date();
	var dtVal = dt.getFullYear()+"-"+((dt.getMonth()+1)<10 ? "0"+(dt.getMonth()+1) : (dt.getMonth()+1))+"-"+(dt.getDate() <10 ? ("0"+dt.getDate()) : dt.getDate());
	dtVal+=" "+(dt.getHours()<10 ? ("0"+dt.getHours()) :(dt.getHours()) )+":"+(dt.getMinutes()<10 ? ("0"+dt.getMinutes()) :(dt.getMinutes()) )+":"+(dt.getSeconds()<10 ? ("0"+dt.getSeconds()) :(dt.getSeconds()) );
	return dtVal;
};
tool.gridExtend = function(){
	$.extend($.fn.datagrid.methods, {
		editCell: function(jq,param){
			return jq.each(function(){
				var opts = $(this).datagrid('options');
				var fields = $(this).datagrid('getColumnFields',true).concat($(this).datagrid('getColumnFields'));
				for(var i=0; i<fields.length; i++){
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor1 = col.editor;
					if (fields[i] != param.field){
						col.editor = null;
					}
				}
				$(this).datagrid('beginEdit', param.index);
	            var ed = $(this).datagrid('getEditor', param);
	            if (ed){
	                if ($(ed.target).hasClass('textbox-f')){
	                    $(ed.target).textbox('textbox').focus();
	                } else {
	                    $(ed.target).focus();
	                }
	            }
				for(var i=0; i<fields.length; i++){
					var col = $(this).datagrid('getColumnOption', fields[i]);
					col.editor = col.editor1;
				}
			});
		},
	    enableCellEditing: function(jq){
	        return jq.each(function(){
	            var dg = $(this);
	            var opts = dg.datagrid('options');
	            opts.oldOnClickCell = opts.onClickCell;
	            opts.onClickCell = function(index, field){
	                if (opts.editIndex != undefined){
	                    if (dg.datagrid('validateRow', opts.editIndex)){
	                        dg.datagrid('endEdit', opts.editIndex);
	                        opts.editIndex = undefined;
	                    } else {
	                        return;
	                    }
	                }
	                dg.datagrid('selectRow', index).datagrid('editCell', {
	                    index: index,
	                    field: field
	                });
	                opts.editIndex = index;
	                opts.oldOnClickCell.call(this, index, field);
	            }
	        });
	    }
	});
};
tool.searchModule = {
		inputEl : null,
		init:function(renderTo,doSearch,placeholder){
			var ph = placeholder ? placeholder : "输入关键字查询";
			var _input = $("<input type=\"text\" autocomplete=\"off\" class=\"search-input\" placeholder=\""+ph+"\" >");
			var _btn = $("<a class=\"x-btn search-btn-n\">搜索</a>");
			renderTo.append(_btn).append(_input);
			_btn.bind("click",function(e){
				doSearch();
			});
			_input.bind("keydown",function(e){
				if(e.keyCode == "13"){
					doSearch();
					return ;
				}
			});
			this.inputEl = null;
			this.inputEl = _input;
		},
		getValue : function(){
			return this.inputEl.val();
		},
		setValue : function(val){
			return this.inputEl.val(val);
		}
		
};
tool.pager = {
		initPager:function(renderTo){
			var content = $("<div class=\"pagercls\"></div>");
			var span_content = $("<span style='display:inline-block;'></span>");
			var totalSpan = $("<span style='margin-right:5px'>共&nbsp;<span id='tatal_count'>0</span>&nbsp;条</span>");
			var to_span = $("<span style='margin-right:5px'><span id='current_page'>1</span>&nbsp;/&nbsp;<span id='total_page'>1</span>&nbsp;页</span>");
			var to_pre = $("<a id='preBtn'style='display:none;margin-right:5px;cursor:pointer;cursor:hand;' ><span><span>上一页</span></span></a>");
			var to_next = $("<a id='nextBtn'style='display:none;margin-right:5px;cursor:pointer;cursor:hand;'><span><span>下一页</span></span></a>");
			var jump = $("<a id='jumpTo_btn' style='display:none;cursor:pointer;cursor:hand;' ><span><span>跳转</span></span></a>")
			var jumpto = $("<div id='jump_to_content' class=\"easyui-panel panel-body panel-body-noheader\" style=\"display:none;position:absolute;top:25px;right:5px;width:145px;line-height:20px;padding:5px 0 5px 10px;z-index:554;\">跳转至&nbsp;<input id='jump_input' class=\"textbox\"  style=\"width:30px;height:20px;border-radius:0px\" >&nbsp;页<a id='jump_btn' class=\"easyui-linkbutton l-btn l-btn-small l-btn-plain\" data-options=\"plain:true\" ><span class=\"l-btn-left\"><span class=\"l-btn-text\">确定</span></span></a></div>")
			span_content.append(totalSpan).append(to_span).append(to_pre).append(to_next).append(jump);
			content.append(span_content).append(jumpto);
			renderTo.append(content);
		},
		setPager:function(total,pageNum,pageSize){
			total = total ? total :0;
			pageNum = pageNum ? pageNum :tool.defaultPageNo
			pageSize = pageSize ? pageSize  : tool.pageSize;
			var pageCount = total==0?(total+1):Math.ceil(total/pageSize);
			$("#tatal_count").html("").html(total);
			$("#current_page").html("").html(pageNum);
			$("#total_page").html("").html(pageCount);
			if(pageNum<pageCount){
				$("#nextBtn").show();
			}else{
				$("#nextBtn").hide();
			}
			if(pageNum>1){
				$("#preBtn").show();
			}else{
				$("#preBtn").hide();
			}
			if(pageCount>2){
				$("#jumpTo_btn").show()
			}else{
				$("#jumpTo_btn").hide()
			}
		},
		eventBind :function(fn,paramArr){
			if(!paramArr){
				paramArr = [];
			}
			$("#nextBtn").bind("click",function(){
				var tempArr = [($("#current_page").html()-0+1),tool.pageSize];
				//数组置换追加并将data放入第一个位置
				Array.prototype.push.apply(tempArr, paramArr);
				tool.doCallBack(fn,tempArr);
			}).hover(function(){
				$(this).css("text-decoration","underline")
			},function(){
				$(this).css("text-decoration","none")
			})
			$("#preBtn").bind("click",function(){
				var tempArr = [($("#current_page").html()-1),tool.pageSize];
				//数组置换追加并将data放入第一个位置
				Array.prototype.push.apply(tempArr, paramArr);
				tool.doCallBack(fn,tempArr);
			}).hover(function(){
				$(this).css("text-decoration","underline")
			},function(){
				$(this).css("text-decoration","none")
			})
			$("#jumpTo_btn").bind("click",function(){
				$("#jump_input").val("");
				$("#jump_to_content").show();
				return false;
			}).hover(function(){
				$(this).css("text-decoration","underline")
			},function(){
				$(this).css("text-decoration","none")
			})
			$("#jump_input").bind("keyup",function(e){
				var val = $("#jump_input").val().trim()-0;
				if(e.keyCode == "13" && val!="" && !isNaN(val)){
					var total = $("#total_page").html()-0
					if(val>total){
						val = total;
					}
					var tempArr = [val,tool.pageSize];
					Array.prototype.push.apply(tempArr, paramArr);
					tool.doCallBack(fn,tempArr);
					$("#jump_to_content").hide();
				}
			});
			$("#jump_btn").bind("click",function(){
				var val = $("#jump_input").val().trim()-0;
				if(isNaN(val)){
					var total = $("#total_page").html()-0
					if(val>total){
						val = total;
					}
					var tempArr = [val,tool.pageSize];
					Array.prototype.push.apply(tempArr, paramArr);
					tool.doCallBack(fn,tempArr);
				}
			});
			$(document).bind("click",function(e){
				if(!($(e.target).parents("div[id='jump_to_content']").length>0 || $(e.target).attr("id")=="jump_to_content")){
					$("#jump_to_content").hide();
				}
			})
		}
};
/********************************************错误码维护**************************************************************************/
tool.codeMap = {
		constant : {
			info_10000 : "操作成功 ",
			info_10001 : "操作失败 ",
			info_10002 : "JSON格式不正确 ",
			info_10003 : "参数异常 ",
			info_10004 : "同名错误 ",
			info_10005 : "请求URL错误 ",
			info_10006 : "新增成功  ",
			info_10007 : "修改成功 ",
			info_10008 : "删除成功 ",
			info_10009 : "保存成功",
			info_10010 : "新增失败 ",
			info_10011 : "修改失败 ",
			info_10012 : "删除失败 ",
			info_10013 : "保存失败 ",
			info_10015 : "存在子项，无法直接删除",
			info_10016 : "商品已有库存，无法直接删除",
			info_10017 : "该类别已绑定相关商品，无法直接删除",
			info_10018 : "该类别已绑定相关业务，无法直接删除",
			info_10019 : "该类别已绑定相关商品，无法直接删除",
			info_10020 : "您还未设置审批通道，请联系管理员",
			info_10021 : "提交成功",
			info_10022 : "提交失败",
			info_10023 : "单号重复",
			info_10024 : "审核成功",
			info_10025 : "审核失败",
			info_10026 : "编码重复",
			info_10027 : "请先保存采购入库单，再进行串号扫描",
			info_10029 : "付款成功",
			info_10030 : "付款失败",
			info_10031 : "取消成功",
			info_10032 : "取消失败",
			info_10033 : "串号输入有误",
			info_10034 : "上传成功",
			info_10035 : "上传失败",
			info_10036 : "库存不存在",
			info_10037 : "串号已被销售",
			info_10038 : "该单已经提交"
			
							
		},
		getMsg : function(code){
			return this.constant["info_"+code];
		}
};
tool.railList = {
		map : {
			route_1 : "杭州地铁1号线(往文泽路)",
			route_2 : "杭州地铁1号线(往临平)",
			route_3 : "杭州地铁2号线",
			route_4 : "杭州地铁4号线"
		},
		route : [{
			id : "route_0",
			text : "请选择地铁路线"
		},{
			id : "route_1",
			text : "杭州地铁1号线(往文泽路)"
		},{
			id : "route_2",
			text : "杭州地铁1号线(往临平)"
		},{
			id : "route_3",
			text : "杭州地铁2号线"
		},{
			id : "route_4",
			text : "杭州地铁4号线"
		}],
	detail : {
		route_0 : [],
		route_1 :[
                  {
                      "id": "湘湖",
                      "text" : "湘湖"
                  },
                  {
                  	 "id": "滨康路",
                       "text" : "滨康路"
                  },
                  {
                  	"id": "西兴",
                      "text" : "西兴"
                  },
                  {
                  	"id": "滨和路",
                      "text" : "滨和路"
                  },
                  {
                  	"id": "江陵路",
                      "text" : "江陵路"
                  },
                  {
                  	"id": "近江",
                      "text" : "近江"
                  },
                  {
                  	"id": "婺江路",
                      "text" : "婺江路"
                  },
                  {
                  	"id": "城站",
                      "text" : "城站"
                  },
                  {
                  	"id": "定安路",
                      "text" : "定安路"
                  },
                  {
                  	"id": "龙翔桥",
                      "text" : "龙翔桥"
                  },
                  {
                  	"id": "凤起路",
                      "text" : "凤起路"
                  },
                  {
                  	"id": "武林广场",
                      "text" : "武林广场"	
                  },
                  {
                  	"id": "西湖文化广场",
                      "text" : "西湖文化广场"	
                  },
                  {
                  	"id": "打铁关",
                      "text" : "打铁关"	
                  },
                  {
                  	"id": "闸弄口",
                      "text" : "闸弄口"	
                  },
                  {
                  	"id": "火车东站",
                      "text" : "火车东站"	
                  },
                  {
                  	"id": "彭埠",
                      "text" : "彭埠"	
                  },
                  {
                  	"id": "七堡",
                      "text" : "七堡"	
                  },
                  {
                  	"id": "九和路",
                      "text" : "九和路"
                  },
                  {
                  	"id": "九堡",
                      "text" : "九堡"
                  },
                  {
                  	"id": "客运中心",
                      "text" : "客运中心"
                  },
                  {
                  	"id": "下沙西",
                      "text" : "下沙西"
                  },
                  {
                  	"id": "金沙湖",
                      "text" : "金沙湖"
                  },
                  {
                  	"id": "高沙路",
                      "text" : "高沙路"
                  },
                  {
                  	"id": "文泽路",
                      "text" : "文泽路"
                  }
              ],
              route_2 : [
                 {
                 	"id": "湘湖",
                     "text" : "湘湖"
                 },
                 {
                 	"id": "滨康路",
                     "text" : "滨康路"
                 },
                 {
                 	"id": "西兴",
                     "text" : "西兴"
                 },
                 {
                 	"id": "滨和路",
                     "text" : "滨和路"
                 },
                 {
                 	"id": "江陵路",
                     "text" : "江陵路"
                 },
                 {
                 	"id": "近江",
                     "text" : "近江"
                 },
                 {
                 	"id": "婺江路",
                     "text" : "婺江路"
                 },
                 {
                 	"id": "城站",
                     "text" : "城站"
                 },
                 {
                 	"id": "定安路",
                     "text" : "定安路"
                 },
                 {
                 	"id": "龙翔桥",
                     "text" : "龙翔桥"
                 },
                 {
                 	"id": "凤起路",
                     "text" : "凤起路"
                 },
                 {
                 	"id": "武林广场",
                     "text" : "武林广场"
                 },
                 {
                 	"id": "西湖文化广场",
                     "text" : "西湖文化广场"
                 },
                 {
                 	"id": "打铁关",
                     "text" : "打铁关"
                 },
                 {
                 	"id": "闸弄口",
                     "text" : "闸弄口"
                 },
                 {
                 	"id": "火车东站",
                     "text" : "火车东站"
                 },
                 {
                 	"id": "彭埠",
                     "text" : "彭埠"
                 },
                 {
                 	"id": "七堡",
                     "text" : "七堡"
                 },
                 {
                 	"id": "九和路",
                     "text" : "九和路"
                 },
                 {
                 	"id": "九堡",
                     "text" : "九堡"
                 },
                 {
                 	"id": "客运中心",
                     "text" : "客运中心"
                 },
                 {
                 	"id": "乔司南",
                     "text" : "乔司南"
                 },
                 {
                 	"id": "乔司",
                     "text" : "乔司"
                 },
                 {
                 	"id": "翁梅",
                     "text" : "翁梅"
                 },
                 {
                 	"id": "余杭高铁",
                     "text" : "余杭高铁"
                 },
                 {
                 	"id": "南苑",
                     "text" : "南苑"
                 },
                 {
                 	"id": "临平",
                     "text" : "临平"
                 }
             ],
             route_3 : [
                        {
                        	"id": "钱江路",
                            "text" : "钱江路"
                        },
                        {
                        	"id": "盈丰路",
                            "text" : "盈丰路"
                        },
                        {
                        	"id": "飞虹路",
                            "text" : "飞虹路"
                        },
                        {
                        	"id": "振宁路",
                            "text" : "振宁路"
                        },
                        {
                        	"id": "建设三路",
                            "text" : "建设三路"
                        },
                        {
                        	"id": "建设一路",
                            "text" : "建设一路"
                        },
                        {
                        	"id": "人民广场",
                            "text" : "人民广场"
                        },
                        {
                        	"id": "杭发广",
                            "text" : "杭发广"
                        },
                        {
                        	"id": "人民路",
                            "text" : "人民路"
                        },
                        {
                        	"id": "潘水",
                            "text" : "潘水"
                        },
                        {
                        	"id": "曹家桥",
                            "text" : "曹家桥"
                        },
                        {
                        	"id": "朝阳村",
                            "text" : "朝阳村"
                        }
                    ],
             route_4 :[
                       {
                       	"id": "彭埠",
                           "text" : "彭埠"
                       },
                       {
                       	"id": "火车东站",
                           "text" : "火车东站"
                       },
                       {
                       	"id": "新风",
                           "text" : "新风"
                       },
                       {
                       	"id": "新塘",
                           "text" : "新塘"
                       },
                       {
                       	"id": "景芳",
                           "text" : "景芳"
                       },
                       {
                       	"id": "钱江路",
                           "text" : "钱江路"
                       },
                       {
                       	"id": "江锦路",
                           "text" : "江锦路"
                       },
                       {
                       	"id": "市民中心",
                           "text" : "市民中心"
                       },
                       {
                       	"id": "城星路",
                           "text" : "城星路"
                       },
                       {
                       	"id": "近江",
                           "text" : "近江"
                       }
                   ]
             
	}
}