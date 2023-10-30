let xValues = document.querySelectorAll('.x');
let yValues = document.querySelector('.y');
let rValues = document.querySelectorAll('.r');
let canvas = document.getElementById("canvas");
let dots = new Map();
let coordinateX;
let coordinateY;
let selectedRValue = 1;
let xValue;
let yValue;
let rValue;
let infoBox = document.querySelector('.validation_message');
let infoValidation = '';

$(document).ready(function (){
    drawCanvas();
    for (let pairs of dots.entries()) {
        putDot(pairs[0], pairs[1]);
    }
})

canvas.addEventListener('click', ev => {
    if (checkOnActiveR()) {
        coordinateX = ev.offsetX;
        coordinateY = ev.offsetY;
        putDot(coordinateX, coordinateY);
    } else {
        alert("Значение R не выбрано!")
    }
})

//Отправка запроса при нажатии кнопки
$("#forms").on("submit", function (event) {
    event.preventDefault();
    infoValidation = "";
    if (checkInputValues(xValues, yValues, rValues)) {
        yValue = Number(yValues.value.trim()).toFixed(2);
        $.post("./controller", {x_coordinate: xValue, y_coordinate: yValue, r_coordinate: rValue, time: new Date().getTimezoneOffset()}).done(function (data) {
            $(".check_button").attr("disabled", false);
            dots.set(coordinateX, coordinateY);
            JSON.stringify(Object.fromEntries(dots));
            window.location.replace("result.jsp");
        }).fail(function () {
            $(".check_button").attr("disabled", false);
        })
    }
})