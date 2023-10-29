// Изменение выбранного бокса для X
function changeActiveCheckboxX(id) {
    let buttons = document.querySelectorAll('.x');
    for (let i = 0; i < buttons.length; i++) {
        if (buttons[i].id !== id) {
            buttons[i].disabled = false;
        } else {
            document.getElementById(id).disabled = true;
        }
    }
}
// Изменение выбранного бокса для R
function changeActiveCheckboxR(id) {
    let buttons = document.querySelectorAll('.r');
    for (let i = 0; i < buttons.length; i++) {
        if (buttons[i].id !== id) {
            buttons[i].disabled = false;
        } else {
            document.getElementById(id).disabled = true;
            drawCanvas(document.getElementById(id).value);
            selectedRValue = document.getElementById(id).value;
        }
    }
}
function changeActiveButtonY(r, y) {
    document.querySelector('.y').value = (-(r * 0.01) * y + (r * 1.5)).toFixed(2);
}
function changeActiveButtonX(r, x) {
    xValue = ((r * 0.01) * x - (r * 1.5)).toFixed(2);
}
function checkOnActiveR() {
    let buttons = document.querySelectorAll('.r');
    for (let i = 0; i < buttons.length; i++) {
        if (buttons[i].disabled) {
            return true;
        }
    }
    return false;
}
