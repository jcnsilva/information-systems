$(document).ready(function(){
	
	$("#addDicaDisciplina, #addDicaMaterial, #addDicaConselho, #motivoDiscordancia, #painelMetadicas").hide();
		
	$("#botaoDiscordar").on("click", function(evt){
		evt.preventDefault();
		$("#motivoDiscordancia").slideDown();
	});
	
	$("#addMetadica").on("click", function(evt){
		evt.preventDefault();
		$("#painelMetadicas").slideToggle();
	});
	
	$('#tipoDica').on("change", function() {
        if($(this).val() === "dicaAssunto") {
        	$("#addDicaDisciplina, #addDicaMaterial, #addDicaConselho").hide(function(){
        		$("#addDicaAssunto").fadeIn("slow");
        	});
        } else if($(this).val() === "dicaDisciplina"){
        	$("#addDicaAssunto, #addDicaMaterial, #addDicaConselho").hide(function(){
        		$("#addDicaDisciplina").fadeIn("slow");
        	});
        } else if($(this).val() === "dicaMaterial"){
        	$("#addDicaAssunto, #addDicaDisciplina, #addDicaConselho").hide(function(){
        		$("#addDicaMaterial").fadeIn("slow");
        	});
        } else {
        	$("#addDicaAssunto, #addDicaDisciplina, #addDicaMaterial").hide(function(){
        		$("#addDicaConselho").fadeIn("slow");
        	});            
        } 
	});		
});