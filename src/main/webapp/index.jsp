<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="ru">
<head>
  <meta charset="UTF-8">
  <meta name="author" content="Alohhis"/>
  <title>Lab 2</title>
   <link rel="stylesheet" href="styles/index.css">
</head>
<body>
  <header>
    <h1> Докшина Алёна, P3221, Вариант №2178</h1>
  </header>
  <main>
    <section id="data-input">
      <h2>Ввод данных:</h2>
      <div class="input-block">
        <div id="message">
        </div>
        <form id="forms" method="get">
          <div class="input_values">

            <label for="x">Координата X:</label>
            <div id="x_select">
              <select name="x" id="x" required>
                <option value="-5">-5</option>
                <option value="-4">-4</option>
                <option value="-3">-3</option>
                <option value="-2">-2</option>
                <option value="-1">-1</option>
                <option value="0">0</option>
                <option value="1">1</option>
                <option value="2">2</option>
                <option value="3">3</option>
              </select>
            </div>
            <br>
            <br>

            <div class="input_values">
              <label for="y">Введите Y:</label> <br>
              <br>
              <input style="margin-bottom: 10px" type="text" name="y_coordinate" class="y" id="y"
                     placeholder="[-3..5]" autocomplete="off" maxlength="10" required><br>
              <span id="note_y">*Происходит округление значений до 2 знаков после запятой</span>
            </div>
            <br>


            <div class="input_values">Введите R:<br>
              <br>
              <input type="radio" name="r_coordinate" id="r_value_1" class="r" value="1">
              <label for="r_value_1" class="r">1</label>

              <input type="radio" name="r_coordinate" id="r_value_1.5" class="r" value="1.5">
              <label for="r_value_1.5" class="r">1.5</label>

              <input type="radio" name="r_coordinate" id="r_value_2" class="r" value="2">
              <label for="r_value_2" class="r">2</label>

              <input type="radio" name="r_coordinate" id="r_value_2.5" class="r" value="2.5">
              <label for="r_value_2.5" class="r">2.5</label>

              <input type="radio" name="r_coordinate" id="r_value_3" class="r" value="3">
              <label for="r_value_3" class="r">3</label>
            </div>
            <div class="input_values">
              <button type="submit" class="submit">Отправить</button>
              <button type="reset" class="reset" >Сбросить</button>
            </div>


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
    <table class="result">
      <thead>
      <tr>
        <th>X</th>
        <th>Y</th>
        <th>R</th>
        <th>Текущее время</th>
        <th>Статус</th>
        <th>Время выполнения скрипта, нс</th>
      </tr>
      </thead>
      <thbody>
        <c:forEach var="result" items="${resultList}">
          <tr>
            <td>${result.x}</td>
            <td>${result.y}</td>
            <td>${result.r}</td>
            <td>${result.calculatedAt}</td>
            <td>${result.res ? 'попал' : 'мимо'}</td>
            <td>${result.calculationTime}</td>
          </tr>
        </c:forEach>
      </thbody>
    </table>
    </div>
  </main>
  <script>const ctx = "${pageContext.request.contextPath}";</script>
  <script type="text/javascript">
    var resultList = [];

    <c:forEach var="result" items="${resultList}">
    var item = {
      x: ${result.x},
      y: ${result.y},
      r: ${result.r},
      result: ${result.res},
      calculationTime: ${result.calculationTime},
      calculatedAt: "${result.calculatedAt}"
    };
    resultList.push(item);
    drawPoint(${result.x}, ${result.y}, ${result.r});
    </c:forEach>
  </script>
<script src="js/GraphDrawer.js"></script>
<script src="js/validator.js"></script>
</body>
</html>