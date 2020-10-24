const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀 생성
router.post('/', function (req, res) {

    console.log('<<Team/create>>');
    var result_code = 404;
        var input_data_array= [];
        var inputData = req.body; // JSON data 받음
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
        
        var sql_update = 'update best_matching.user set team_name = ? where id = ? ';
        var update_data_array = [];
        update_data_array.push(inputData.team_name);
        update_data_array.push(inputData.master_id);
        dbconn.query(sql_update, update_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query Update Success');
                console.log(rows);
                result_code=200;
                res.json( {"result": result_code});
                
            } else {
                console.log('Query Update Error : ' + err);
                result_code=404;
                res.json({ "result": result_code });
            }
        });
});
module.exports = router;