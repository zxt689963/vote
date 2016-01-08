
var picUri = '/vote/page/images/';
$(document).ready(function() {
	
	$("a#example1").fancybox({
		'titleShow'		: false
	});

	$("a#example2").fancybox({
		'titleShow'		: false,
		'transitionIn'	: 'elastic',
		'transitionOut'	: 'elastic'
	});

	$("a#example3").fancybox({
		'titleShow'		: false,
		'transitionIn'	: 'none',
		'transitionOut'	: 'none'
	});

	$("a#example4").fancybox();

	$("a#example5").fancybox({
		'titlePosition'	: 'inside'
	});

	$("a#example6").fancybox({
		'titlePosition'	: 'over'
	});

	$("a[rel=example_group]").fancybox({
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'titlePosition' 	: 'over',
		
	});

	/*
	*   Examples - various
	*/

	$("#various1").fancybox({
		'titlePosition'		: 'inside',
		'transitionIn'		: 'none',
		'transitionOut'		: 'none'
	});

	$("#various2").fancybox();

	$("#various3").fancybox({
		'width'				: '75%',
		'height'			: '75%',
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none',
		'type'				: 'iframe'
	});

	$("#various4").fancybox({
		'padding'			: 0,
		'autoScale'			: false,
		'transitionIn'		: 'none',
		'transitionOut'		: 'none'
	});
	
	var url = '/vote/vote/read';
	$.ajax({
		url : url,
		type : 'post',
		dataType : 'json',
		success : function(data) {
			success(data);
			$("a[rel=example_group]").fancybox({
				'transitionIn'		: 'none',
				'transitionOut'		: 'none',
				'titlePosition' 	: 'over',
				
			});
		},
		error : function() {}
	});
}); 

/*$(document).ready(function() {
	
	*   Examples - images
	

	
});*/

function success(data) {
	var str = '';
	for(var o in data){
		str = '<div class="photo-1" >' +
		    	'<ul>' +
		    	'<a href="'+picUri+data[o].pic+'" class="photo-img" rel="example_group"> <img src="'+picUri+data[o].pic+'" style="border: 0px;"> </a>' +
			        '<li class="photo-title">'+data[o].picName+'</li>' +
			       // '<li class="photo-shoot">作者:'+data[o].author+'</li>' +
			        '<li class="photo-film">描述:'+data[o].description+'</li>' +
			        '<li class="photo-support"><img src="images/zan.png" /><a onclick="javascript:voting('+data[o].id+')" title="点赞">'+data[o].voteState+'</a><span>已赞（'+data[o].votedNumber+'）</span></li>' +
				' </ul>' +
			'</div>';
   	 	$('#photo').append(str);
    }
}

/**
 * 实现投票功能
 */
function voting(id) {
	var url = '/vote/vote/vote';
	$.ajax({
		url : url,
		type : 'post',
		dataType : 'json',
		data : {json : '{"id":'+id+'}'},
		success : function(data) {
			if (data.errorCode == 10039) {
				alert("你已经投票过了");
			} else if (data.errorCode == 2) {
				location.href = "/vote/manage/login/index.html";
			} else {
				location.reload(); 
			}
		},
		error : function() {}
	});
}