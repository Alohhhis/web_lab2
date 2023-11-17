window.addEventListener("load", () => {
    const canvas = document.querySelector("#canvas");
    let radiusInput = document.querySelector(".r");
    let r = radiusInput.value;
    const ctx = canvas.getContext("2d");
    const side = canvas.width;
    const centerX = canvas.width / 2;
    const centerY = canvas.height / 2;
    ctx.lineWidth = 2;
    const k = 2;
    const MIN_X = -5;
    const MAX_X = 3;
    const MAX_Y = 5
    const MIN_Y = -5;
    const elements = document.getElementsByClassName("r");
    ctx.strokeRect(centerX - side / 2, centerY - side / 2, side, side);

    drawGraph(r);

    canvas.addEventListener("click", function (event) {
        let x = event.offsetX;
        let y = event.offsetY;

        let rect = canvas.getBoundingClientRect();
        let clientX = (event.clientX - rect.left - 200) / (side / 3) * r * k;
        let clientY = ((-1) * (event.clientY - rect.top - 200)) / (side / 3) * r * k;

        ctx.fillStyle = "#6ba8d3";
        ctx.beginPath();
        ctx.arc(x,y,4,0,Math.PI * 2);
        ctx.fill();
        ctx.stroke();
        ctx.closePath();

        console.log("Coordinate x: " + clientX,
            "Coordinate y: " + clientY);

        if (isPointInsideGraph(clientX, clientY, r)) {
            // Отправка AJAX-запроса
            $.ajax({
                type: "GET",
                url: "/ControllerServlet",
                async: false,
                data: {"x": clientX, "y": clientY, "r": r},
                success: function (data) {
                    window.location.replace('./result.jsp');
                },
                error: function (xhr, textStatus, err) {
                    showError(document.getElementById('buttons-table'), "readyState: " + xhr.readyState + "\n" +
                        "responseText: " + xhr.responseText + "\n" +
                        "status: " + xhr.status + "\n" +
                        "text status: " + textStatus + "\n" +
                        "error: " + err);
                }
            });
        } else {
            // Вывод сообщения, что точка не попала на поле
            alert('Точка не попала на поле');
        }
    })


    for (const element of elements) {
        element.addEventListener('change', function (){
            let r = element.value;
            ctx.clearRect(2, 2, side / 1.01, side / 1.01);
            drawGraph(r);
        });
    }

    function drawGraph(r){
        // график 1 четверть
        ctx.fillStyle = "#4f9947";
        const radius = side / 3 * (r / k);
        const startAngle = Math.PI * 1.5;
        const endAngle = Math.PI * 2;
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.arc(centerX, centerY, radius, startAngle, endAngle, false);
        ctx.fill();
        ctx.closePath();

        // график 2 четверть
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(centerX + side / 3 * (r / k),centerY);
        ctx.lineTo(centerX , centerY + side/6 * (r / k));
        ctx.fill();
        ctx.closePath();

        // график 3 четверть
        ctx.beginPath();
        ctx.moveTo(centerX, centerY);
        ctx.lineTo(centerX, centerY + side/6 * (r / k));
        ctx.lineTo(centerX - side/3 * (r / k), centerY + side/6 * (r / k));
        ctx.lineTo(centerX - side/3 * (r / k), centerY);
        ctx.fill();
        ctx.closePath();

        // second lab graph
        // график 1 четверть пуста

        //x axis
        ctx.fillStyle = "#000000";
        ctx.beginPath();
        ctx.moveTo(centerX - side/2, centerY);
        ctx.lineTo(centerX + side/2, centerY);
        ctx.stroke();
        ctx.closePath();

        //y axis
        ctx.beginPath();
        ctx.moveTo(centerX, centerY + side/2);
        ctx.lineTo(centerX, centerY - side/2);
        ctx.stroke();
        ctx.closePath();

        //x arrow
        ctx.beginPath();
        ctx.moveTo(centerX + side/2, centerY);
        ctx.lineTo(centerX + side/2 - 10, centerY + 10);
        ctx.lineTo(centerX + side/2 - 10, centerY - 10);
        ctx.lineTo(centerX + side/2, centerY);
        ctx.fill();
        ctx.closePath();

        //y arrow
        ctx.beginPath();
        ctx.moveTo(centerX, centerY - side/2);
        ctx.lineTo(centerX + 10, centerY - side/2 + 10);
        ctx.lineTo(centerX - 10, centerY - side/2 + 10);
        ctx.lineTo(centerX, centerY - side/2);
        ctx.fill();
        ctx.closePath();

        //пометки на числовой оси

        ctx.beginPath();
        ctx.moveTo(centerX + side/6 * (r/k), centerY);
        ctx.lineTo(centerX + side/6 * (r/k), centerY + 5);
        ctx.lineTo(centerX + side/6 * (r/k), centerY - 5);
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(centerX - side/6 * (r/k), centerY);
        ctx.lineTo(centerX - side/6 * (r/k), centerY + 5);
        ctx.lineTo(centerX - side/6 * (r/k), centerY - 5);
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(centerX, centerY + side/6 * (r/k));
        ctx.lineTo(centerX - 5, centerY + side/6 * (r/k));
        ctx.lineTo(centerX + 5, centerY + side/6 * (r/k));
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(centerX, centerY - side/6 * (r/k));
        ctx.lineTo(centerX - 5, centerY - side/6 * (r/k));
        ctx.lineTo(centerX + 5, centerY - side/6 * (r/k));
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(centerX + side/3 * (r/k), centerY);
        ctx.lineTo(centerX + side/3 * (r/k), centerY + 5);
        ctx.lineTo(centerX + side/3 * (r/k), centerY - 5);
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(centerX - side/3 * (r/k), centerY);
        ctx.lineTo(centerX - side/3 * (r/k), centerY + 5);
        ctx.lineTo(centerX - side/3 * (r/k), centerY - 5);
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(centerX, centerY + side/3 * (r/k));
        ctx.lineTo(centerX - 5, centerY + side/3 * (r/k));
        ctx.lineTo(centerX + 5, centerY + side/3 * (r/k));
        ctx.stroke();
        ctx.closePath();

        ctx.beginPath();
        ctx.moveTo(centerX, centerY - side/3 * (r/k));
        ctx.lineTo(centerX - 5, centerY - side/3 * (r/k));
        ctx.lineTo(centerX + 5, centerY - side/3 * (r/k));
        ctx.stroke();
        ctx.closePath();

        ctx.fillStyle = "black";
        ctx.font = "22px bold monospace";

        ctx.beginPath();
        ctx.fillText("R", centerX - 30, centerY - side/3 * (r/k));
        ctx.closePath();

        ctx.beginPath();
        ctx.fillText("R/2", centerX - 40, centerY - side/6 * (r/k));
        ctx.closePath();

        ctx.beginPath();
        ctx.fillText("-R/2", centerX - 45, centerY + side/6 * (r/k));
        ctx.closePath();

        ctx.beginPath();
        ctx.fillText("-R", centerX - 35, centerY + side/3 * (r/k));
        ctx.closePath();

        ctx.beginPath();
        ctx.fillText("-R/2", centerX - side/6 * (r/k) - 10, centerY - 10);
        ctx.closePath();

        ctx.beginPath();
        ctx.fillText("-R", centerX - side/3 * (r/k) - 5 , centerY - 10);
        ctx.closePath();

        ctx.beginPath();
        ctx.fillText("R/2", centerX + side/6 * (r/k) - 10, centerY - 10);
        ctx.closePath();

        ctx.beginPath();
        ctx.fillText("R", centerX + side/3 * (r/k) - 5 , centerY - 10);
        ctx.closePath();
    }

    function isPointInsideGraph(x, y, r) {
        // Проверка внутри круга
        const insideCircle = (x >= 0 && y >= 0 && Math.sqrt(x*x + y*y) <= r);

        // Проверка внутри прямоугольника
        const insideRectangle = (x <= 0 && x >= -r && y <= 0 && y >= -r/2);

        // Проверка внутри треугольника
        const insideTriangle = (x >= 0 && y <= 0 && x + 2 * y >= 0 && x*x + y*y <= (r/2)*(r/2));

        return insideCircle || insideRectangle || insideTriangle;
    }


})

