$(function(){
    $.get('menu-lateral.html', function(data){
        $('#menu-lateral').html(data);
    });
    $.get('menu-superior.html', function(resposta){
        $('#menu-superior').html(resposta);
    });
});