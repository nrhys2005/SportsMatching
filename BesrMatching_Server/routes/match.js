const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
router.get('/', function (req, res) {
    console.log('<<Login>>');
});

router.post('/create', function (req, res) {
    
    console.log('<<match/create>>');
 
    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음

        input_data_array.push(inputData.title);// json->array
        input_data_array.push(inputData.ground_name);
        input_data_array.push(inputData.date);
        input_data_array.push(inputData.start_time);
        input_data_array.push(inputData.end_time);
        input_data_array.push(inputData.cost);
        input_data_array.push(inputData.max_user);
        let today = new Date();   
        input_data_array.push(today);
        var user_id =inputData.user_id;

        console.log('input_data : ' + input_data_array); 

        var sql_insert = 'INSERT INTO best_matching.match (title, ground_name, date, start_time, end_time,cost,max_user,create_time) VALUES(?, ?, ?, ?, ?, ?,?,?)';
        dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                //console.log('Query insert success');
                //res.json({ "result": "Success" });
                //console.log(rows);

                var match_sql = 'select * from best_matching.match order by create_time DESC limit 1';

                dbconn.query(match_sql,"", function (err, rows, fields) {//DB connect
                    if (!err) {
                        if (rows.length == 0) {
                            console.log('Query Select Success("result": "no find")');
                            res.json({ "result": "no find" });
                        }
                        else {
                            //console.log('Query Select Success(result": "Success)');
                            var match_user_array= [];
                            match_user_array.push(user_id);
                            match_user_array.push(rows[0].id);
                            console.log("matching_user("+user_id + ", "+ rows[0].id+")");
                            var insert_sql = "INSERT INTO best_matching.matching_user(user_id,match_id) values(?,?)";
                            dbconn.query(insert_sql, match_user_array, function (err, rows, fields) {//DB connect
                                if (!err) {
                                    if (rows.length == 0) {
                                        console.log('Query insert success("result": "no find")');
                                        res.json({ "result": "no find" });
                                    }
                                    else {
                                        console.log('Query insert success(result": "Success)');
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


                // var insert_data_array = [];
                // insert_data_array.push(inputData.user_id);
                // insert_data_array.push(match_id);
                
            } else {
                console.log('Query Error : ' + err);
                res.json({ "result": err });
            }
        });
        
    });
});

router.get('/search/:search', function (req, res) {
    console.log('<<match/search>>');
    var search = req.params.search;
    var search_data_array = [];
    //var Data = JSON.parse(data); // JSON data 받음
    var sql;
    console.log('Search = '+ search);
    if (search == "none") {
        sql = 'select * from best_matching.match';
    }
    else {
        sql = 'select * from best_matching.match where match.title like ?';
        search_data_array.push('%' + search + '%');
    }

    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                res.json({ "result": "Success",match_info : rows });
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});
router.post('/join', function (req, res) {
    console.log('<<match/join>>');
    req.on('data', (data) => {
        var inputData = JSON.parse(data); // JSON data 받음
        var user_id = inputData.user_id;
        var match_id = inputData.match_id;

        console.log("cancel id, match_id= " + user_id+ match_id);
        var sql = 'select * from best_matching.match where id = ?';

        dbconn.query(sql, match_id, function (err, rows, fields) {//DB connect
            if (!err) {
                if (rows.length == 0) {
                    console.log('Query Select Success("result": "no find")');
                    res.json({ "result": "no find" });
                }
                else {
                    console.log('11111111111');
                    var max_user = rows[0].max_user;
                    var count_sql = 'select count(*) as count from best_matching.match, best_matching.matching_user where matching_user.match_id =match.id and matching_user.user_id= ?';
                    dbconn.query(count_sql, user_id, function (err, rows, fields) {//DB connect
                        if (!err) {
                            if (rows.length == 0) {
                                console.log('Query Select Success("result": "no find")');
                                res.json({ "result": "no find" });
                            }
                            else {
                                if (rows[0].count >= max_user) {
                                    console.log('Query Select Success(result": "matching is full")');
                                    res.json({ "result": "full" });
                                }
                                else {
                                    console.log('222222222');
                                    var insert_data_array = [];
                                    insert_data_array.push(user_id);
                                    insert_data_array.push(match_id);
                                    var insert_sql = "INSERT INTO best_matching.matching_user(user_id,match_id) values(?,?)";
                                    dbconn.query(insert_sql, insert_data_array, function (err, rows, fields) {//DB connect
                                        if (!err) {
                                            if (rows.length == 0) {
                                                console.log('Query insert success("result": "no find")');
                                                res.json({ "result": "no find" });
                                            }
                                            else {
                                                console.log('Query insert success(result": "Success)');
                                                res.json({ "result": "Success" });
                                            }

                                        } else {
                                            console.log('Query insert error : ' + err);
                                            res.json({ "result": err });
                                        }
                                    });
                                }
                            }

                        } else {
                            console.log('Query Select Error : ' + err);
                            res.json({ "result": err });
                        }
                    });
                }

            } else {
                console.log('Query Select Error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});

router.get('/mymatching_list/:user_id', function (req, res) {
    console.log('<<match/mymatching_list>>');
    var user_id = req.params.user_id;
    //var Data = JSON.parse(data); // JSON data 받음
    console.log('Search = '+ user_id);
    var sql = 'select * from best_matching.match, best_matching.matching_user where matching_user.match_id =match.id and matching_user.user_id= ?';

    dbconn.query(sql, user_id, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                res.json({ "result": "Success",mymatch_list_info : rows });
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});
router.get('/mymatching/:match_id', function (req, res) {
    console.log('<<match/mymatching>>');
    var search_match_id = req.params.match_id;
    //var Data = JSON.parse(data); // JSON data 받음
    console.log('Search = '+ search_match_id);
    var sql = 'select * from best_matching.match where match.id = ?';

    dbconn.query(sql, search_match_id, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                res.json({ "result": "Success",mymatch_info : rows });
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});

router.post('/mymatching_cancel', function (req, res) {
    console.log('<<match/mymatching_cancel>>');
    req.on('data', (data) => {
        var inputData = JSON.parse(data); // JSON data 받음
        var user_id = inputData.user_id;
        var match_id = inputData.match_id;
        var delete_data_array = [];
        delete_data_array.push(user_id);
        delete_data_array.push(match_id);
        console.log('cancel id, match_id= ' + user_id, match_id);
        var sql = "DELETE FROM matching_user WHERE user_id = ? and match_id = ?";

        dbconn.query(sql, delete_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                if (rows.length == 0) {
                    console.log('Query delete success("result": "no find")');
                    res.json({ "result": "no find" });
                }
                else {
                    console.log('Query delete success(result": "Success)');
                    res.json({ "result": "Success" });
                }

            } else {
                console.log('Query delete error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});

module.exports = router;