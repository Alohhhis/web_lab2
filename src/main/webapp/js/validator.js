document.getElementById('forms').addEventListener('submit', function (e) {
    e.preventDefault();
    let x = document.getElementById('x');
    let y = document.getElementById('y');
    let r = document.querySelectorAll('input[type="radio"]:checked')[0];

    if (validate(x, y, r)) {
        console.log("x:", x.value);
        console.log("y:", y.value);
        console.log("r:", r.value);
        send(x.value, y.value, r.value);
    }
});



function send(x, y, r) {
    $.ajax({
            type: "GET",
        url: "ControllerServlet",
        async: false,
            data: {"x": x, "y": y, "r": r},
            success: function (result) {
                window.location.replace("./result.jsp")
            }, error: function (xhr, textStatus, err) {
                showError(document.getElementById('buttons-div'), "readyState: " + xhr.readyState + "\n" +
                    "responseText: " + xhr.responseText + "\n" +
                    "status: " + xhr.status + "\n" +
                    "text status: " + textStatus + "\n" +
                    "error: " + err);
            }
        }
    )
}

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
    }, 999999);
}

function validate(x, y, r) {
    if (x == null || y == null || r == null) {
        showError(null, "Необходимо выбрать значения X, Y и R");
        return false;
    }
    let replaceDot = val => val.replace(',','.');
    let y1 = replaceDot(y.value);
    if (r == null) {
        showError(r, "Необходимо выбрать значение R");
        return false;
    }
    if (y1 == null || y1==="") {
        showError(y, "Необходимо выбрать значение Y");
        return false;
    }
    if (x==null){
        showError(x,"Необходимо выбрать значение X");
        return false;
    }
    if (y1< -3 || y1 > 5) {
        showError(y, 'Значение Y должно быть числом в пределах от -3 до 5.');
        return false;
    }
    return true;
}


