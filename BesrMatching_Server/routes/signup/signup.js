var express = require('express');
var router = express.Router();

var dbConObj = require('../../config/db_info');	//디비 정보 import
var dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 

router.post('/signup', function (req, res) {
    
    console.log('<<Signup/signup>>');

    
    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음

        input_data_array.push(inputData.id);// json->array
        input_data_array.push(inputData.pw);
        input_data_array.push(inputData.name);
        input_data_array.push(inputData.email);
        console.log('input_data : ' + input_data_array); // 회원가입 내용 출력

        var sql_insert = 'INSERT INTO best_matching.user (id, pw, name , email) VALUES(?, ?, ?, ?)';
        dbconn.query(sql_insert,input_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query insert success');
                res.json({"result" : "Success"});
            } else {
                console.log('Query Error : ' + err);
                res.json({"result" : err});
            }
        });
    });

    
    
});

router.post('/check', function (req, res, next) {
    console.log('<<Signup/check>>');
    
    req.on('data', (data) => {
        inputData = JSON.parse(data);
        var find_id = inputData.id;

        var sql = 'SELECT * WHERE id = '+ inputData.id +' FROM user'; 
        dbconn.query(sql,function (err, rows, fields) {
            if(!err){
                var check = false;
                for(var i =0;i<rows.length;i++){
                    if(rows[i].id==find_id){
                        console.log('duplication');
                        res.json({"result" : "duplication"});
                        check=true;
                        break;
                    }
                }
                if(!check){
                    console.log('no duplication');
                    res.json({"result" : "no duplication"});
                }
            }else{
                res.send(err);
                console.log(err);
            }
        });
    });
    
});

module.exports = router;