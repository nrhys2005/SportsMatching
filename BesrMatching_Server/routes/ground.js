const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


router.get('/', function (req, res) {
    var sql = 'select * from ground';
    dbconn.query(sql, function (err, rows, fields) {//DB connect
        var List = new Array();
        if (!err) {
            console.log('Query Select Success');
            console.log(rows);

            for (var i = 0; i < rows.length; i++) {
                var data = new Object();
                data.name = rows[i].name;
                data.lat = rows[i].latitude;
                data.lon = rows[i].longtitude;
                data.price = rows[i].price;

                List.push(data);
            }
            var jsonData = JSON.stringify(List);
            var senddata = JSON.parse(jsonData);
            console.log(senddata);
            info = jsonData;

            //var team_info = JSON.stringify(rows);

            res.json(
                {
                    ground_info : info,
                    result: 'Success'
                });
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});


router.get('/search', function (req, res) {
    console.log('<<ground/search>>');
    var latitude = req.query.latitude;
    var longtitude = req.query.longtitude;
    var sql;
    var search_data_array = [];
    if(latitude=="none" || longtitude=="none"){
        sql = 'select * from ground';
    }
    else{
        sql = 'select * from ground';
    }
    console.log('search(latitude, longtitude) : ' + search_data_array);

    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success');
                res.json({ "result": "Success", groud_info : rows});
            }
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});

router.post('/create', function (req, res) {

    console.log('<<ground/create>>');

    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음

        input_data_array.push(inputData.id);// json->array
        input_data_array.push(inputData.name);
        input_data_array.push(inputData.latitude);
        input_data_array.push(inputData.longtitude);
        input_data_array.push(inputData.price);
        console.log('input_data : ' + input_data_array); 

        var sql_insert = 'INSERT INTO best_matching.ground (id, name, latitude, longtitude, price) VALUES(?, ?, ?, ?, ?)';
        dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query insert success');
                res.json({ "result": "Success" });
            } else {
                console.log('Query Error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});

module.exports = router;