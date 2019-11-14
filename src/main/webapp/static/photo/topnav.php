$(document).ready(function() {

	$("ul#topnav li").hover(function() {
		$(this).css({ 'background' : '#fff'});
		$(this).find("span").show();
	} , function() {
		$(this).css({ 'background' : 'none'});
		$(this).find("span").hide();
	});

	$("ul#topnav li.user_l").hover(function() {
		$(this).css({ 'background' : 'none'});
		$(this).find("span").show();
	});
	$("ul#topnav li.search").hover(function() {
		$(this).css({ 'background' : 'none'});
		$(this).find("span").show();
	});
});
document.write('\
<ul id="topnav">\
    <li><a href="http://club.qingdaonews.com/" class="pr20">青青岛社区</a></li>\
    <li>\
		<a class="pr20 jt">论坛</a>\
		<span class="club_subnav">\
    <a href="http://club.qingdaonews.com/club_entry_2_2_0_1_0.htm">青岛论坛</a>\
    <a href="http://club.qingdaonews.com/club_entry_1025_2_0_1_0.htm">社会万象</a>\
    <a href="http://club.qingdaonews.com/club_entry_1059_3_0_1_0.htm">市南论坛</a>\
    <a href="http://club.qingdaonews.com/club_entry_128_3_0_1_0.htm">黄岛论坛</a>\
    <a href="http://club.qingdaonews.com/club_entry_27_14_0_1_0.htm">美食广场</a>\
    <a href="http://club.qingdaonews.com/club_entry_26_15_0_1_0.htm">游山玩水</a>\
    <a href="http://club.qingdaonews.com/club_entry_1170_2_0_1_0.htm">我爱打折</a>\
    <a href="http://club.qingdaonews.com/club_entry_1235_9_0_1_0.htm">亲子论坛</a>\
    <a href="http://club.qingdaonews.com/club_entry_20_2_0_1_0.htm">青岛楼市</a>\
    <a href="http://club.qingdaonews.com/club_entry_49_7_0_1_0.htm">家居在线</a>\
    <a href="http://club.qingdaonews.com/club_entry_1115_8_0_1_0.htm">教育论坛</a>\
    <a href="http://club.qingdaonews.com/club_entry_47_8_0_1_0.htm">教师论坛</a>\
    <a href="http://club.qingdaonews.com/club_entry_39_6_0_1_0.htm">车迷论坛</a>\
    <a href="http://club.qingdaonews.com/club_entry_71_5_0_1_0.htm">摄影园地</a>\
    <a href="http://club.qingdaonews.com/club_entry_1243_9_0_1_0.htm">青岛孕妈</a>\
    <a href="http://club.qingdaonews.com/club_entry_33_12_0_1_0.htm">健康有约</a>\
	</span>\
	</li>\
	<li style="margin-left:10px"><a href="http://club.qingdaonews.com/showAnnounce_30_3812941_1_0.htm" class="pr20">联系我们</a></li>\	<li class="p15" style="float: right; background-image: none; background-position: initial initial; background-repeat: initial initial;">\
		<a href="http://club.qingdaonews.com/user/reg.php">注册</a>\
	</li>\
	<li class="p15" style="float: right; background-image: none; background-position: initial initial; background-repeat: initial initial;">\
		<a href="http://club.qingdaonews.com/user/login.php" target="_self">登录</a>\
	</li>\
	<li class="search" style="height:30px">\
	<form>\
		<input type="text" value="找美食 找房子 找打折" onblur="this.value==\'\'?this.value=\'找美食 找房子 找打折\':\'\';" onfocus="this.value==\'找美食 找房子 找打折\'?this.value=\'\':\'找美食 找房子 找打折\';" id="boxUname" name="txtTopic" class="t_i"/>\
		<input name="" id="clubsubkeyword" type="submit" value="" class="s_btn"/>\
	</form>\
	</li>\
 </ul>\
 ');
 var msg_reply = 0;
 if(msg_reply>0) {
    $("#msg_li").css({ 'background' : '#fff'});
   // $("#msg_li span").show();
    $("#msg_li span").fadeToggle("2000",function(){
         $(this).fadeOut(6000,function(){$("#msg_li").css({ 'background' : ''}); });
    });
 }
 $(document).ready(function() {
     var bbsusertmp='';
    if(bbsusertmp){
        $("#cc").hide();
	}
	
	$("#boxUname").bind("keypress",function(e) {
		if (e.keyCode == 13) {
			var sValue = "http://www.baidu.com/s?wd=site:club.qingdaonews.com%20" + $("#boxUname").val();
			window.open(sValue,"_blank");
			e.preventDefault();
		}
	});
	
	$("#clubsubkeyword").on("click",function(e) {
		var sValue = "http://www.baidu.com/s?wd=site:club.qingdaonews.com%20" + $("#boxUname").val();
		window.open(sValue,"_blank");
		e.preventDefault();
	});

 });
