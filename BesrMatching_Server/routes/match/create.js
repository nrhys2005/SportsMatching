const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
router.post('/', function (req, res) {
    
    console.log('<<match/create>>');
 
    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음
        let start_time = new Date((inputData.date+ " " +inputData.start_time+":00"));
        let end_time = new Date(inputData.date+ " " +inputData.end_time+":00");
        let today = new Date();
        var user_id =inputData.user_id;

        input_data_array.push(inputData.title);// json->array
        input_data_array.push(inputData.ground_name);
        input_data_array.push(start_time);
        input_data_array.push(end_time);
        input_data_array.push(inputData.cost);
        input_data_array.push(inputData.max_user);
        input_data_array.push(today);
        input_data_array.push(1);
        var check_sql = 'select * from best_matching.match where ground_name = ? order by end_time DESC';
        
        dbconn.query(check_sql,inputData.ground_name, function (err, rows, fields) {//DB connect
            if (!err) {
                var check = true;
                if (rows.length == 0) {
                    console.log('Query Select Success("result": "no find")');
                }
                else {
                    for(var i=0;i<rows.length;i++){
                       if(rows[i].end_time>=start_time && end_time>=rows[i].start_time){
                            check = false;
                            break;
                        }
                    }
                }
                if (!check) {
                    res.json({ "result": "duplicate" });
                    console.log('Match overlap');
                }
                else {
                    var sql_insert = 'INSERT INTO best_matching.match (title, ground_name, start_time, end_time,cost,max_user,create_time, participants) VALUES(?, ?, ?, ?, ?,?,?,?)';
                    dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
                        if (!err) {
                            var match_sql = 'select * from best_matching.match order by create_time DESC limit 1';

                            dbconn.query(match_sql, "", function (err, rows, fields) {//DB connect
                                if (!err) {
                                    if (rows.length == 0) {
                                        console.log('Query Select Success("result": "no find")');
                                        res.json({ "result": "no find" });
                                    }
                                    else {
                                        //console.log('Query Select Success(result": "Success)');
                                        var match_user_array = [];
                                        match_user_array.push(user_id);
                                        match_user_array.push(rows[0].id);
                                        console.log("matching_user(" + user_id + ", " + rows[0].id + ")");
                                        var insert_sql = "INSERT INTO best_matching.matching_user(user_id,match_id) values(?,?)";
                                        dbconn.query(insert_sql, match_user_array, function (err, rows, fields) {//DB connect
                                            if (!err) {
                                                if (rows.length == 0) {
                                                    console.log('Query insert success("result": "no find")');
                                                    res.json({ "result": "no find" });
                                                }
                                                else {
                                                    console.log('matching_user insert success');
                                                    res.json({ "result": "Success" });
                                                }
                                            } else {
                                                console.log('Query insert error : ' + err);
                                                res.json({ "result": err });
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
                }
            } else {
                console.log('' + err);
            }
        });
    });
});

module.exports = router;