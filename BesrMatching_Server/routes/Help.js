const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
router.get('/', function (req, res) {
    console.log('<<Help>>');
});

router.post('/Myinfo', function (req, res) {
    
    console.log('<<Help/Myinfo_Post>>');
 
    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음

        
        input_data_array.push(inputData.mail);
        input_data_array.push(inputData.phone);
        input_data_array.push(inputData.location);
        input_data_array.push(inputData.position);
        input_data_array.push(inputData.id);// json->array

        console.log('input_data : ' + input_data_array); 

        var sql_insert = 'UPDATE best_matching.user SET mail=?, phone=?, location=?, position=? WHERE id=? ';
        dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query Update success');
                res.json({ "result": "Success" });
            } else {
                console.log('Query Error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});

router.get('/Myinfo/:id', function (req, res) {
    console.log('<<Help/Myinfo_Get>>');
    var Mid = req.params.id;
    var search_data_array = [];
    //var Data = JSON.parse(data); // JSON data 받음
    var sql;
    console.log('id = '+ Mid);
    sql = 'select id, name, team, email, phone, location, position from best_matching.user where id= ?';
    search_data_array.push(Mid);

    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result": "Success)');
            res.json({ "result": "Success", Myinfo : rows});
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});


module.exports = router;