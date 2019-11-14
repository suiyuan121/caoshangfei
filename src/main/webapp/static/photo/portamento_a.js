
//导航
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

//列表隔行换色
$(function(){
	$('table > tbody:odd').addClass('odd');
	$('table > tbody:even').addClass('even');
});
});
