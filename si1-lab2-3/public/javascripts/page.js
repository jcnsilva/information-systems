$(document).ready(function(){
	
	$(".seasons").hide();
	$(".episodes").hide();
	
	$(".season-label").on("click", function(event){
		event.stopPropagation();
		$(this).find(".episodes").slideToggle("slow");		
	});
	
	$(".serie-label").on("click", function(){
		$(this).find(".seasons").slideToggle("slow");		
	});
	
	
	$(".serie-label").on("mouseleave", function(){
		$(".seasons").slideUp("slow");
	});
	
});

