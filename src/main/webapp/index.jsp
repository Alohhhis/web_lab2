<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<head>
  <meta charset="UTF-8">
  <meta name="author" content="Alohhis"/>
  <title>Lab 2</title>
   <link rel="stylesheet" href="styles/index.css">


</head>

<body>
  <style>
     .x {
      background-color: #6ad341;
      color: #2b2b36;
      border: none;
      padding: 10px 20px;
      transition: transform 0.2s, filter 0.2s;
      cursor: pointer;
    }

     .x.active {
      transform: scale(1.1);
    }

     .x:active {
      filter: brightness(1.2);
    }

    .x:not(.active) {
      filter: brightness(0.8);
    }
  </style>

<header>
  <h1> Докшина Алёна, P3221, Вариант №2178</h1>
</header>
<main>
  <section id="data-input">
    <h2>Ввод данных:</h2>
    <div class="input-block">
      <form id="forms" method="get">
        <div class="input_values">
          Введите X:<br>
          <br>
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_-5"
                 value="-5">
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_-4"
                 value="-4">
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_-3"
                 value="-3">
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_-2"
                 value="-2">
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_-1"
                 value="-1">
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_0" value="0">
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_1" value="1">
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_2" value="2">
          <input type="button" name="x_coordinate" onclick="changeActiveCheckboxX(id)" class="x" id="x_value_3" value="3">
        </div>
        <br>


        <script>
          const buttonsX = document.querySelectorAll('.x');

          buttonsX.forEach(button => {
            button.addEventListener('click', () => {
              // Удаление класса active со всех кнопок "x"
              buttonsX.forEach(btn => {
                btn.classList.remove('active');
              });

              // Добавление класса active к нажатой кнопке "x"
              button.classList.add('active');
            });
          });
        </script>

        <div class="input_values">
          <label for="y_value">Введите Y:</label> <br>
          <br>
          <input style="margin-bottom: 10px" type="text" name="y_coordinate" class="y" id="y_value"
                 placeholder="[-3..5]" autocomplete="off"><br>
          <span id="note">*Происходит округление значений до 2 знаков после запятой</span>
        </div>
        <br>


        <div class="input_values">Введите R:<br>
          <br>
          <input type="radio" name="r_coordinate" id="r_value_1" class="r" value="1">
          <label for="r_value_1" class="r_l">1</label>

          <input type="radio" name="r_coordinate" id="r_value_1.5" class="r" value="1.5">
          <label for="r_value_1.5" class="r_l">1.5</label>

          <input type="radio" name="r_coordinate" id="r_value_2" class="r" value="2">
          <label for="r_value_2" class="r_l">2</label>

          <input type="radio" name="r_coordinate" id="r_value_2.5" class="r" value="2.5">
          <label for="r_value_2.5" class="r_l">2.5</label>

          <input type="radio" name="r_coordinate" id="r_value_3" class="r" value="3">
          <label for="r_value_3" class="r_l">3</label>
        </div>

        <div class="input_values">
          <button type="submit" class="submit">Отправить</button>
          <button type="reset" class="reset">Сбросить</button>
        </div>

        <div id="message" class="validation_message">
        </div>
      </form>
    </div>
  </section>

  <section id="graph">
    <h2 class="center">График:</h2>
    <div class="graph">
      <canvas class="canvas" id="canvas" width="400" height="400"> </canvas>
    </div>
  </section>
</main>
<!--<script src="js/index/updater.js"></script>
<script src="js/ErrorMessage.js"></script>-->
<script src="js/GraphDrawer.js"></script>
</body>
</html>