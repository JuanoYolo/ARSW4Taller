//Traer id del html
var d = document.getElementById("logito");

var lienzo = d.getContext("2d");

var xInicial = 0;
var xFinal = 30;
var yInicial = 0;
var yFinal = 300;

var cont = 0;

while(cont <= 301){

    dibujar("blue",xInicial,yInicial,xFinal,yFinal);
    yInicial = yInicial + 30;
    xFinal = xFinal + 30;
    cont = cont + 30;
}


function dibujar(colorLinea,xInicial,yInicial,xFinal,yFinal){

    lienzo.beginPath();
    lienzo.strokeStyle = colorLinea;
    lienzo.moveTo(xInicial, yInicial);
    lienzo.lineTo(xFinal, yFinal);
    lienzo.stroke();
    lienzo.closePath();
    
}