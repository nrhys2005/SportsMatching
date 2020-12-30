const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


router.post('/upload', function (req, res) {

    console.log('<<Image/upload>>');
    

    var input_data_array = [];

    console.log('input_data : ' + req.file);

    input_data_array.push("1");
    input_data_array.push(req.file);


    console.log("id :   " )
    console.log("이미지 : " + req.file);
    var sql_insert = 'INSERT INTO best_matching.ground_image (id, image) VALUES(?, ?)';
    dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query insert success');
        } else {
            console.log('Query Error : ' + err);
        }
    });
    var sql = 'select image from best_matching.ground_image where ?';
    var select_data_array = [];
    select_data_array.push("1");
    dbconn.query(sql, select_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success');
            res.json(rows[0]);

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });

});



module.exports = router;
