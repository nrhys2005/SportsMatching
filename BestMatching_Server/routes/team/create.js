const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀 생성
router.post('/', function (req, res) {
    console.log('<<Team/create>>');
    var result_code;
    
    var input_data_array = [];
    input_data_array.push(req.body.team_name);// json->array
    input_data_array.push(req.body.master_id);// json->array
    input_data_array.push(req.body.phonenumber);
    input_data_array.push(req.body.age_avg);
    input_data_array.push(req.body.level);
    input_data_array.push(req.body.location);
    input_data_array.push(req.body.week);
    input_data_array.push(req.body.comment);
    input_data_array.push(1);

    var sql_insert = 'INSERT INTO best_matching.team (team_name,master_id, phonenumber, age_avg, level, location,week,comment,member_count) VALUES(?, ?, ?, ?, ?, ?, ?, ?)';
    dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query insert success');
        } else {
            console.log('Query Error : ' + err);
        }
    });

    var sql_update = 'update best_matching.user set team_name = ? where id = ? ';
    var update_data_array = [];
    update_data_array.push(req.body.team_name);
    update_data_array.push(req.body.master_id);
    dbconn.query(sql_update, update_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Update Success');
            result_code = 200;
            res.json({ "result": result_code });

        } else {
            console.log('Query Update Error : ' + err);
            result_code = 404;
            res.json({ "result": result_code });
        }
    });
});
module.exports = router;