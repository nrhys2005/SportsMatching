const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


router.get('/', function (req, res) {
    console.log('<<Team/team_waiting_list>>');
    var result_code=404;
    var team_name = req.query.team_name;
    var data_array = [];
    
    var sql;
    
    data_array.push(team_name);
    
    sql ='select user_id, name, age, location, phone, position from best_matching.user, best_matching.team_waiting where user.id=team_waiting.user_id and team_waiting.team_name=?';

    dbconn.query(sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                result_code=202;
                res.json({ "result": result_code });
            }
            else {
                console.log('Query Select Success(result": 200)');
                console.log(rows);
                result_code=200;
                res.json({ "result": result_code ,agree_info : rows});
            }

        } else {
            console.log('Query Select Error : ' + err);
            result_code=404;
            res.json({ "result":result_code,"err" : err });
        }
    });
});