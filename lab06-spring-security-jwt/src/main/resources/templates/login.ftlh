<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Тест</title>
</head>
<body>
<div id="login_section" style="display:block">
    <form id="form" method="post">
        <div><input name="username" type="text" placeholder="username"/></div>
        <div><input name="password" type="password" placeholder="password"/></div>
        <div><input type="button" value="OK" onclick="login()"/></div>
    </form>
</div>

<div id="content_section" style="display:none">
    <h1>Данные по бронированиям в гостинице:</h1>
    <div>
        <table id="booking_table">

        </table>
    </div>
</div>

<div id="booking_data"></div>

<div id="edit_form" style="display:none; margin-top:20px;">
    <h3>Редактирование бронирования</h3>

    <input type="hidden" id="edit_id">

    <div>
        Arrival date:
        <input id="edit_arrival">
    </div>

    <div>
        Staying date:
        <input id="edit_staying">
    </div>

    <button onclick="save_booking()">Сохранить</button>
</div>

<script>
    let jwt_token;
    function login() {

        const formDataToJson = (formData) => JSON.stringify(Object.fromEntries(formData));
        const formElement = document.getElementById('form');
        const jsonData = formDataToJson(new FormData(formElement));
        console.log(jsonData)

        fetch('http://localhost:8091/api/auth/login', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json;charset=utf-8'
            },
            body: jsonData
        })
            .then(response => response.json())
            .then(json => {
                let token = json.token;
                jwt_token = token;
                //localStorage.setItem('jwt_token', token);
                //window.location = '/app';
                document.getElementById("login_section").style.display="none";
                document.getElementById("content_section").style.display="block";

                show_content();
            });
    }

    function show_content() {
        show_bookings();
    }

    function show_bookings() {
        fetch('http://localhost:8091/api/booking/allview', {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + jwt_token
            }
        })
            .then(response => response.json())
            .then(json => {
                make_booking_table(json);
            });
    }

    function make_booking_table(data) {
        let tbl = document.getElementById("booking_table");
        tbl.innerHTML = "";
        for (let i = 0; i < data.bookings.length; i++) {
            let tr = document.createElement('tr');
            let td_room = document.createElement('td');

            td_room.innerHTML = "<span onclick='open_booking("
                + data.bookings[i].id + ")'>" + data.bookings[i].id + "</span>";
            tr.append(td_room);

            let td_arrivaldate = document.createElement('td');
            td_arrivaldate.innerHTML = data.bookings[i].arrivaldate;
            tr.append(td_arrivaldate);

            let td_stayingdate = document.createElement('td');
            td_stayingdate.innerHTML = data.bookings[i].stayingdate;
            tr.append(td_stayingdate);

            tbl.append(tr);
        }
    }



    function open_booking(booking_id) {
        fetch('http://localhost:8091/api/booking/get/' + booking_id, {
            method: 'GET',
            headers: {
                'Authorization': 'Bearer ' + jwt_token
            }
        })
            .then(response => response.json())
            .then(json => {
                fill_booking(json);
            });
    }

    function fill_booking(data) {
        document.getElementById("edit_id").value = data.id;
        document.getElementById("edit_arrival").value = data.arrivaldate;
        document.getElementById("edit_staying").value = data.stayingdate;

        document.getElementById("edit_form").style.display = "block";
    }

    function save_booking() {
        let id = document.getElementById("edit_id").value;
        let arrival = document.getElementById("edit_arrival").value;
        let staying = document.getElementById("edit_staying").value;

        fetch('http://localhost:8091/api/booking/update', {
            method: 'POST',
            headers: {
                'Authorization': 'Bearer ' + jwt_token,
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                id: id,
                arrivaldate: arrival,
                stayingdate: staying
            })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Ошибка сохранения");
                }
                return response;
            })
            .then(() => {
                alert("Сохранено");
                show_bookings();
                document.getElementById("edit_form").style.display = "none";
            })
            .catch(err => alert(err.message));
    }

</script>


</body>
</html>