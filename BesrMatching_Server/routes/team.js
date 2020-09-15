const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


router.post('/create', function (req, res) {

    console.log('<<Team/create>>');

    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음
        console.log('input_data : ' + inputData); 

        input_data_array.push(inputData.team_name);// json->array
        input_data_array.push(inputData.master_id);// json->array
        input_data_array.push(inputData.phonenumber);
        input_data_array.push(inputData.age_avg);
        input_data_array.push(inputData.level);
        input_data_array.push(inputData.location);
        input_data_array.push(inputData.week);
        input_data_array.push(inputData.comment);

        console.log('input_data : ' + input_data_array); 
        
        var sql_insert = 'INSERT INTO best_matching.team (team_name,master_id, phonenumber, age_avg, level, location,week,comment) VALUES(?, ?, ?, ?, ?, ?, ?, ?)';
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
        update_data_array.push(inputData.id);
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

router.get('/search/:search', function (req, res) {
    console.log('<<Team/search>>');
    var search = req.params.search;
    var search_data_array = [];
    //var Data = JSON.parse(data); // JSON data 받음
    var sql;
    //console.log("session~~~~~~~~~~~~~~~~~~~~~~~~~"+req.session.id);
    console.log('Search ='+ search);
    if (search == "none") {
        sql = 'select * from best_matching.team';
    }
    else {
        sql = 'select * from best_matching.team where team_name like ? or location like ?';
        search_data_array.push("%" + search + "%");
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
                console.log(rows);
                res.json({ "result": "Success" ,team_info : rows});
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});

router.post('/signup', function (req, res) {
    console.log('<<Team/signup>>');
    req.on('data', (data) => {
        var update_data_array= [];
        var Data = JSON.parse(data); // JSON data 받음
        var sql = 'update user set team name = ? where user.id == ?';
        update_data_array.push(Data.team_name);
        update_data_array.push(Data.id);

        dbconn.query(sql, update_data_array, function (err, rows, fields) {//DB connect
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
