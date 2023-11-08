document.getElementById('forms').addEventListener('submit', function (e) {
    e.preventDefault();
    let x = document.querySelectorAll('input[class="x"]:checked')[0];
    let y = document.getElementById('y_value');
    let r = document.querySelectorAll('input[type="radio"]:checked')[0];

    if (validate(x, y, r)) {
        send(x.value, y.value, r.value);
    }
});
function showError(element, message) {
    const errorElement = document.createElement('div');
    errorElement.classList.add('error-message');
    errorElement.textContent = message;
    errorElement.style.color = 'red';
    errorElement.style.fontSize = '20px';
    errorElement.style.textAlign = 'left';
    element.parentNode.insertBefore(errorElement, element.nextSibling);
    setTimeout(function () {
        errorElement.remove();
    }, 2000);
}

function validate(x, y, r) {
    if (r == null) {
        showError(r, "Необходимо выбрать значение R");
        return false;
    }
    if (y == null) {
        showError(y, "Необходимо выбрать значение Y");
        return false;
    }
    if (y ===""){
        showError(y,"Необходимо выбрать значение Y");
        return false;
    }
    if (x==null){
        showError(x,"Необходимо выбрать значение X");
    }
    const yRegex = /^[-+]?\d+(\.\d+)?(,\d+)?([eE][-+]?\d+)?$/;
    if (!yRegex.test(y) || y < -3 || y > 5) {
        showError(y, 'Значение Y должно быть числом в пределах от -3 до 5.');
        return false;
    }
    const rValues = [1, 1.5, 2, 2.5, 3];
    const rNormalized = r.toString().replace(',', '.');
    if (!rValues.includes(parseFloat(rNormalized))) {
        showError(r, 'Значение R должно быть одним из следующих чисел: 1, 1.5, 2, 2.5, 3.');
        return false;
    }
    const xValues = [-5, -4, -3, -2, -1, 0, 1, 2, 3];
    const xNormalized = x.toString().replace(',', '.');
    if (!rValues.includes(parseFloat(rNormalized))) {
        showError(x, 'Значение X должно быть одним из следующих чисел: -5, -4, -3, -2, -1, 0, 1, 2, 3');
        return false;
    }
    return true;
}


function send(x, y, r) {
    $.ajax({
        type: "GET",
        url: "/ControllerServlet",
        async: false,
        data: {"x": x, "y": y, "r": r},
        success: function (data) {
            window.location.replace("./result.jsp")
        }, error: function (xhr, textStatus, err) {
            showError(document.getElementById('buttons-table'), "readyState: " + xhr.readyState + "\n" +
                "responseText: " + xhr.responseText + "\n" +
                "status: " + xhr.status + "\n" +
                "text status: " + textStatus + "\n" +
                "error: " + err);
        }
    })
}