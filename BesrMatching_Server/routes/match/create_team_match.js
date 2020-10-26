const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
var mysql = require('node-mysql');
router.post('/', function (req, res) {
    
    console.log('<<match/create_team_match>>');
 
    var input_data_array = [];
    let start_time = new Date((req.body.date + " " + req.body.start_time + ":00"));
    let end_time = new Date(req.body.date + " " + req.body.end_time + ":00");
    let today = new Date();
    //var user_id = req.body.user_id;
    
    input_data_array.push(req.body.title);// json->array
    input_data_array.push(req.body.ground_name);
    input_data_array.push(start_time);
    input_data_array.push(end_time);
    input_data_array.push(req.body.cost);
    input_data_array.push(req.body.max_user);
    input_data_array.push(req.body.min_user);
    input_data_array.push(today);
    input_data_array.push(req.body.user);

    var sql_insert = 'INSERT INTO best_matching.team_match (title, ground_name, start_time, end_time,cost,max_user,min_user,create_time, participants) VALUES(?, ?, ?, ?, ?,?,?,?,?)';
    dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            var match_sql = 'select * from best_matching.team_match order by create_time DESC limit 1';

            dbconn.query(match_sql, "", function (err, rows, fields) {//DB connect
                if (!err) {
                    if (rows.length == 0) {
                        console.log('Query Select Success("result": "no find")');
                        res.json({ "result": "no find" });
                    }
                    else {
                        //console.log('Query Select Success(result": "Success)');
                        var match_user_array = [];
                       
                        var match_id = rows[0].id;
                        for(var i=0;i<req.body.user;i++){
                            match_user_array.push([req.body.member_info[i],match_id]);
                           
                        }
                        console.log("team_matching_user",match_user_array, "");
                        var insert_sql = "INSERT INTO best_matching.team_matching_user(user_id,team_match_id) values ?;";
                        dbconn.query(insert_sql, [match_user_array], function (err) {//DB connect
                            if (!err) {
                                console.log('team_matching_user insert success');
                                res.json({ "result": "Success" });

                            } else {
                                console.log('Query insert error : ' + err);
                                res.json({ "result": "fail" });
                            }
                        });

                    }

                } else {
                    console.log('Query Select Error : ' + err);
                    res.json({ "result": err });
                }
            });

        } else {
            console.log('Query Error : ' + err);
            res.json({ "result": err });
        }
    });

});
router.get('/booking_list', function (req, res) {  
    console.log('<<match/team_match/booking_list>>');

    var sql = 'select * from best_matching.book_list,best_matching.ground where book_list.ground_id = ground.id and book_list.user_id = ?';
    dbconn.query(sql, req.query.user_id, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log("match "+req.query.ground_id)
            res.json({ "result": 'Success_booking', rows });
        } else {
            console.log('' + err);
            res.json({ "result":  'fail'});
        }
    });
});

router.get('/member_list', function (req, res) {  
    console.log('<<match/team_match/member_list>>');
    var sql=  'select * from best_matching.user where team_name= ?';

    dbconn.query(sql, req.query.team_name, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            res.json({ "result": 'Success_member', member_info : rows});
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result":  'fail'});
        }
    });
});
module.exports = router;

