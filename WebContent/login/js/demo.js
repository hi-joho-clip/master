var WindowHeight = $(window).height(); 
$(function(){
if(WindowHeight > 0){ 
$('.wh').css('height',WindowHeight+'px');
}
});

$(function() {
$('.DSmenu').click(function () {
$(this).toggleClass('action');
$('.section').toggleClass('blur');
});
});
