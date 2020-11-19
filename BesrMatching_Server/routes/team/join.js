const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀 가입신청
router.post('/', function (req, res) {
    console.log('<<Team/join>>');
    var result_code = 404;
    var check_data_array = [];
    var data_array = [];
    check_data_array.push(req.body.team_name);
    check_data_array.push(req.body.user_id);
    data_array.push("Waiting");
    data_array.push(req.body.user_id);
    var check_sql = 'select * from best_matching.team_waiting where team_name = ? and user_id = ?'
    dbconn.query(check_sql, check_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            var update_sql = 'update best_matching.user set wait_state = ? where id = ?';
            dbconn.query(update_sql, data_array, function (err, rows, fields) {//DB connect
                if (!err) {
                    console.log('Query update success(user_info)');
                } else {
                    console.log('Query Update Error(user_info) : ' + err);
                    //res.json({ "result": 400 });
                }
            });
            if (rows.length == 0) {
                var sql = 'insert into best_matching.team_waiting(team_name,user_id) values(?,?)';
                dbconn.query(sql, check_data_array, function (err, rows, fields) {//DB connect
                    if (!err) {
                        console.log('Query insert success');
                        result_code = 200;
                        res.json({ "result": result_code });
                    } else {
                        console.log('Query insert error : ' + err);
                        result_code = 404;
                        res.json({ "result": result_code, "err": err });
                    }
                });
            }
            else {
                console.log('Query select success(Duplicate application)');
                result_code = 202;
                res.json({ "result": result_code });
            }
        } else {
            console.log('Query update error : ' + err);
            result_code = 404;
            res.json({ "result": result_code, "err": err });
        }
    });
});


//팀 가입신청 승인
router.post('/agreement', function (req, res) {
    console.log('<<Team/agreement>>');
    var result_code = 404;
    var data_array = [];
    var team_name = req.body.team_name;
    //var nu = null;
    var user_id = req.body.user_id;
    var update_sql = 'update best_matching.user set team_name = ?,wait_state = ? where user.id = ?';
    console.log(team_name + ',' + user_id);
    data_array.push(team_name);
    data_array.push(null);
    data_array.push(user_id);

    dbconn.query(update_sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            var sql = 'update best_matching.team set member_count = member_count + 1 where team_name = ?';
            dbconn.query(sql, team_name, function (err, rows, fields) {//DB connect
                if (!err) {
                    console.log('Query update success');
                } else {
                    console.log('Query Update Error : ' + err);
                }
            });
            var delete_sql = 'DELETE from team_waiting where user_id = ?';
            dbconn.query(delete_sql, user_id, function (err, rows, fields) {//DB connect
                if (!err) {
                    console.log('Query delete success');
                    result_code = 200;
                    res.json({ "result": result_code });

                } else {
                    console.log('Query Update Error : ' + err);
                    result_code = 404;
                    res.json({ "result": result_code });
                }
            });

        } else {
            console.log('Query Update Error : ' + err);
            result_code = 404;
            res.json({ "result": result_code });
        }
    });
});
module.exports = router;