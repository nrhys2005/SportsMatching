const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


router.post('/upload', function (req, res) {

    console.log('<<Image/upload>>');

    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음
        console.log('input_data : ' + inputData); 
        input_data_array.push(inputData.id);// json->array
        input_data_array.push(inputData.image);


        console.log("id : "+ inputData.id)
        console.log("이미지 : "+inputData.image);
        var sql_insert = 'INSERT INTO best_matching.team (team_name, phonenumber, age_avg, level, location,week,comment) VALUES(?, ?, ?, ?, ?, ?, ?)';
        dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query insert success');
            } else {
                console.log('Query Error : ' + err);
            }
        });
        var sql_update = 'update user set team_name = ? where id = ? ';
        var update_data_array = [];
        update_data_array.push(inputData.team_name);
        update_data_array.push(req.session.user.user_id);
        dbconn.query(sql_update, update_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query Update Success');
                res.json( {"result": "Success"});
                
            } else {
                console.log('Query Update Error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});



module.exports = router;
