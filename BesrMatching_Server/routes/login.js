var express = require('express');
var router = express.Router();

var dbConObj = require('../config/db_info');	//디비 정보 import
var dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 

router.post('/', function (req, res) {
    console.log('<<Login>>');

    req.on('data', (data) => {
        inputData = JSON.parse(data);
        var login_id = inputData.user_id;
        var login_pwd = inputData.user_pwd;

        var sql = 'SELECT * FROM user'; 
        dbconn.query(sql,function (err, rows, fields) {
            if(!err){
                var check = false;
                for(var i =0;i<rows.length;i++){
                    if(rows[i].user_id==login_id&& rows[i].user_pwd ==login_pwd){
                        console.log(login_id+ ' login!!');
                        res.json({"result" : "Success"});
                        check=true;
                        break;
                    }
                }
                if(!check){
                    console.log('login Failed');
                    res.json({"result" : "No find"});
                }
            }else{
                res.send(err);
                console.log(err);
            }
        });
    });

    
    
});

module.exports = router;