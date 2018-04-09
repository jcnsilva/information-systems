$(document).ready(function(){
	
	$(".season").hide();
	$(".episodes").hide();
	
    $(".serie").on("click", function(){
    	$(this).find(".season").slideToggle("slow");
    });
    
    $(".season").on("click", function(event){
    	event.stopPropagation();
    	$(this).find(".episodes").slideToggle("slow");
    });
    
    $(".inner-element").on("click", function(event){
    	event.stopPropagation();
    });
    
});

