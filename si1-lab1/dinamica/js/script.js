/**
 * @author Júlio
 */

$(document).ready(function(){
	$("#finalize").hide();
	
	$("#addinterest").keypress(function(event){
		if(event.wich == 13 || event.keyCode == 13){ //13 é o código da tecla enter
			event.preventDefault();
			
			if($("#addinterest").val() == "") return;
			
			var interest = "<span class='interest limitedwidth'>" + $(this).val() + "</span>";
			var btnlearned = 	"<button class='addlearned btn btn-sm btn-primary pull-right extramargin'>" +
									"<div class='glyphicon glyphicon-arrow-right'>" +
									"</div>" +
								"</button>";
			var btnremove = "	<button class='deleteitem btn btn-sm btn-danger pull-right extramargin'>" + 
									"<div class='glyphicon glyphicon-remove'></div>"+
								"</button>";
			
			$("#tolearn").append("<li class='list-group-item'>" + interest  + btnremove + btnlearned + "</li>");
			$(this).val("");
		}
	});
	
	$("#tolearn").on("click", ".addlearned", function(event){
		event.preventDefault();
		
		var itemlist = $(this).parent();
		var interest = "<span class='interest limitedwidth'>" + itemlist.find("span").text() + "</span>";
		var btntolearn = 	"<button class='removelearned btn btn-sm btn-primary pull-right extramargin'>" + 
								"<div class='glyphicon glyphicon-arrow-left'></div>"+
							"</button>";
		var btnremove = 	"<button class='deleteitem btn btn-sm btn-danger pull-right extramargin'>" +
								"<div class='glyphicon glyphicon-remove'></div>" +
							"</button>";
		
		$("#learned").append("<li class='list-group-item'>" + interest  + btnremove + btntolearn + "</li>");
		itemlist.remove();
	});
	
	$("#learned").on("click", ".removelearned", function(event){
		event.preventDefault();
		
		var itemlist = $(this).parent();
		var interest = "<span class='interest limitedwidth'>" + itemlist.find("span").text() + "</span>";
		
		var btnlearned = 	"<button class='addlearned btn btn-sm btn-primary pull-right extramargin'>" + 
								"<div class='glyphicon glyphicon-arrow-right'></div>"+
							"</button>";
		var btnremove = 	"<button class='deleteitem btn btn-sm btn-danger pull-right extramargin'>" +
								"<div class='glyphicon glyphicon-remove'></div>" +
							"</button>";
							
		$("#tolearn").append("<li class='list-group-item'>" + interest  + btnremove + btnlearned + "</li>");
		itemlist.remove();
	});
	
	$("#tolearn, #learned").on("click", ".deleteitem", function(event){
		event.preventDefault();
		
		$(this).parent().remove();
	});
	
	$("#send").on("click", function(event){
		event.preventDefault();
		
		$("#cadinterests").hide();
		$("#finalize").fadeIn("slow");
	});
});
