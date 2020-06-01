const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
//////////////////////옛날버전(DB수정필요)///////////////////////////
router.post('/recruit', function (req, res) {
    
    console.log('<<match/recruit>>');
 
    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음

        input_data_array.push(inputData.title);// json->array
        input_data_array.push(inputData.phonenumber);
        input_data_array.push(inputData.age_range);
        input_data_array.push(inputData.level);
        input_data_array.push(inputData.location);
        input_data_array.push(inputData.week);
        input_data_array.push(inputData.comment);

        console.log('input_data : ' + input_data_array); 

        var sql_insert = 'INSERT INTO best_matching.match (name, phonenumber, age_range, level, location,week,comment) VALUES(?, ?, ?, ?, ?, ?, ?)';
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
//////////////////////옛날버전(DB수정필요)///////////////////////////
router.get('/search:search', function (req, res) {
    console.log('<<match/search>>');
    var search = req.params.search;
    var search_data_array = [];
    //var Data = JSON.parse(data); // JSON data 받음
    var sql;
    console.log('Search ='+ search);
    if (search == "none") {
        sql = 'select * from team';
    }
    else {
        sql = 'select * from match_notice where title like ? ';
        search_data_array.push("%" + search + "%");
    }

    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                res.json({ "result": "Success",rows });
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});


module.exports = router;