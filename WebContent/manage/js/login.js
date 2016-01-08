 $(function() {
    $("#login_ope").click(function() {
        $(".tip-msg").hide();
        if (chkForm.checkvalid($("#inputUsr"), $("#inputPwd"))) {
			tool.asyncroReq('/vote/login/login', chkForm.executefun($("#inputUsr"),$("#inputUsr").prev().children(".tip-msg")),chkForm.jsonStr);
        } else {
            return false;
        }
    });
	$("#rememberme").change(function(){
		var target_id=$(this).attr("target-id");
		var target_obj=$("#"+target_id);
		var target_value=target_obj.val();
		var temp_key=target_obj.attr("data-mark");
		if($(this).prop("checked")){
			if($.trim(target_value).length>0){
				tool.setCookie(temp_key,target_value);
			}
		}else{
			var cur_cookie=tool.getCookie(temp_key);
			if($.trim(cur_cookie).length>0){
				tool.delCookie(temp_key);
			}
		}
	});
	$(".login-content").find("input").each(function(i,item_in){
		if($(this).attr("type")=="text"){
			$(this).val("");
			var key=$(this).attr("data-mark");
			if(key!=undefined){
				var cur_cookie=tool.getCookie(key);
				if($.trim(cur_cookie).length>0){
					$(this).val(cur_cookie);
					chkForm.is_chked=true;
				}
			}
			
		}else if($(this).attr("type")=="password"){
			$(this).val("");
		}
		else if($(this).attr("type")=="checkbox"){
			$(this).prop("checked",chkForm.is_chked);
		}
	});
	document.onkeydown = function(e){ 
	    var ev = document.all ? window.event : e;
	    if(ev.keyCode==13) {
			$("#login_ope").click();
	     }
	}
})
var chkForm = {
	is_chked:false,
    jsonStr : "",
    username:'',
    /*登陆前验证*/
    checkvalid : function(u_obj, pwd_obj) {
    	var _self=this;
        var flag = true;
        var u_obj_prev = u_obj.prev();
        var pwd_obj_prev = pwd_obj.prev();
        var msg_u_obj_prev = u_obj_prev.children(".tip-msg");
        var msg_pwd_obj_prev = pwd_obj_prev.children(".tip-msg");
        var unameval = $.trim(u_obj.val());
        var upwdval = $.trim(pwd_obj.val());
        if (unameval.length == 0) {
            flag = false;
            msg_u_obj_prev.html("用户名不能为空！");
            msg_u_obj_prev.show();
        } else if (upwdval.length == 0) {
            flag = false;
            msg_pwd_obj_prev.html("密码不能为空！");
            msg_pwd_obj_prev.show();
        }
        if (flag) {
            var uvalid = "";
            var msg = '{"user":"' + unameval + '","password":"' + upwdval + '","uvalidcode":"' + $.trim($("#" + uvalid).val()) + '"}';
            //var msg = {"json":"{\"user\":\"" + unameval + "\",\"password\":\"" + upwdval + "\",\"uvalidcode\":\"" + $.trim($("#" + uvalid).val()) + "\"}"};
            chkForm.jsonStr = msg;
            _self.username=unameval;
            msg_u_obj_prev[0].style.top = -16 + 'px';
            msg_u_obj_prev.html("正在提交...");
            msg_u_obj_prev.show();
            return true;
        } else {
            return false;
        }
    },
    executefun : function(user_obj,u_obj) {
        return function(data) {
            if (data.success) {
                u_obj[0].style.top = -16 + 'px';
                u_obj.html(data.message);
                u_obj.show();
                if(typeof(window.localStorage) == 'undefined'){
                	tool.setCookie(user_obj.attr("data-mark"),data.data.staffName);
				}else{
					window.localStorage.removeItem("usr");
                	window.localStorage.setItem("usr",data.data.staffName);
				}
                location.href = "../main";
            } else {
                u_obj[0].style.top = -16 + 'px';
                u_obj.html("登陆失败，用户名或密码不对");
                u_obj.show();
            }
        }
    }
};