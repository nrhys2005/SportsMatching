const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//내정보(post)------------------------------------------
router.post('/', function (req, res) {
    console.log('<<Help/Myinfo_Post>>');
    req.on('data', (data) => {
        
        var result_code=404;

        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음

        input_data_array.push(inputData.mail);
        input_data_array.push(inputData.phone);
        input_data_array.push(inputData.location);
        input_data_array.push(inputData.age);
        input_data_array.push(inputData.position);
        input_data_array.push(inputData.id);// json->array

        console.log('input_data : ' + input_data_array); 

        var sql_insert = 'UPDATE best_matching.user SET email=?, phone=?, location=?, age=?, position=? WHERE id=? ';
        dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query Update success');
                console.log(rows);
                result_code=200;
                res.json({ "result": result_code});
            } else {
                console.log('Query Error : ' + err);
                result_code=404;
                res.json({ "result": result_code });
            }
        });
    });
});
//내정보(get)------------------------------------------
router.get('/', function (req, res) {
    console.log('<<Help/Myinfo_Get>>');
    
    var user_id = req.query.id;
    
    var data_array = [];
    
    var result_code=404;
    
    
    var sql = 'select id, name, team_name, email, phone, location, age, position from best_matching.user where id= ?';
    console.log('id = '+ user_id);
    data_array.push(user_id);
   
    dbconn.query(sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            console.log(rows);
            result_code=200;
            res.json({ "result": result_code, Myinfo : rows});
        } else {
            console.log('Query Select Error : ' + err);
            result_code=404;
            res.json({ "result": result_code });
        }
    });
});
module.exports = router;