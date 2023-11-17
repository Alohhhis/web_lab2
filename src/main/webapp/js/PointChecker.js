const canvas = document.querySelector('canvas');
const MIN_X = -5;
const MAX_X = 3;
const MAX_Y = 5
const MIN_Y = -5;
const MIN_COORD = 0;
const MAX_COORD = canvas.width;
const side = canvas.width;
const k = 2;
let rect = canvas.getBoundingClientRect();

document.addEventListener('DOMContentLoaded', function () {
    const canvas = document.querySelector('canvas');
    const ctx = canvas.getContext('2d');
    const points = loadPoints() || []; // Загружаем точки из локального хранилища

    points.forEach(point => drawSavedPoint(point));


    canvas.addEventListener('click', function (event) {
        let x = event.clientX;
        let y = event.clientY;


        x -= rect.left;
        y -= rect.top;

        const point = { x, y };
        points.push(point);
        savePoints(points);

        drawSavedPoint(point);

        checkClick(x, y);



    });
    function drawSavedPoint(point) {
        ctx.fillStyle = 'red';
        ctx.beginPath();
        ctx.arc(point.x, point.y, 3, 0, Math.PI * 2);
        ctx.fill();
        ctx.closePath();

    }

    function checkClick(x, y) {
        if (!((x >= MIN_COORD && x <= MAX_COORD) && (y >= MIN_COORD && y <= MAX_COORD))) {
            showError('Вы не попали в область');
            return;
        }

        const rInput = document.querySelector('input[name="r"]:checked');
        if (!rInput) {
            showError('R не выбран');
            return;
        }

        let radius = rInput.value;
        let clientX = (x - rect.left - 200) / (side / 3) * radius * k;
        let clientY = ((-1) * (y - rect.top - 200)) / (side / 3) * radius * k;
        if (!(clientX >= MIN_X && clientX <= MAX_X && clientY >= MIN_Y && clientY <= MAX_Y)) {
            showError('Выход значений за пределы допустимого');
            return;
        }

        $.ajax({
            type: "GET",
            url: "/ControllerServlet",
            data: {"x": clientX, "y": clientY, "r": radius},
            success: function (data) {
                window.location.replace('./result.jsp');
            },
            error: function (xhr, textStatus, err) {
                showError(document.getElementById('buttons-div'), "readyState: " + xhr.readyState + "\n" +
                    "responseText: " + xhr.responseText + "\n" +
                    "status: " + xhr.status + "\n" +
                    "text status: " + textStatus + "\n" +
                    "error: " + err);
            }
        });
    }
    function savePoints(points) {
        // Сохраняем массив точек в локальное хранилище
        localStorage.setItem('savedPoints', JSON.stringify(points));
    }
    function loadPoints() {
        // Загружаем массив точек из локального хранилища
        const savedPoints = localStorage.getItem('savedPoints');
        return savedPoints ? JSON.parse(savedPoints) : null;
    }
});
