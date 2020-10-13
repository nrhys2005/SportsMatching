const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

router.post('/', function (req, res) {

    console.log('<<ground/create>>');

    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음

        //input_data_array.push(inputData.id);// json->array
        input_data_array.push(inputData.name);
        input_data_array.push(inputData.latitude);
        input_data_array.push(inputData.longtitude);
        input_data_array.push(inputData.price);
        console.log('input_data : ' + input_data_array); 

        var sql_insert = 'INSERT INTO best_matching.ground ( name, latitude, longtitude, price) VALUES(?, ?, ?, ?)';
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