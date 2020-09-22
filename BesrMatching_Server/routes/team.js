const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀 메인 프래그먼트------------------
router.get('/team', function (req, res) {
    console.log('<<Team/team_get>>');

    var user_id = req.query.id;
    var data_array = [];
    var sql = 'SELECT id,master_id,user.team_name FROM user, team where user.id=?';
    console.log('id = '+ user_id);
    data_array.push(user_id);
   
    dbconn.query(sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            console.log(rows);
            res.json({ "result": "Success", team_main : rows});
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });

});

//------------------------------------------
//팀 정보 프래그먼트 ------------------------
router.get('/myteam', function (req, res) {
    console.log('<<Team/myteam_get>>');

    var team_name = req.query.team_name;
    var data_array = [];
    var sql=  'select * from best_matching.team where team_name= ?';
    console.log('team_name = '+ team_name);
    data_array.push(team_name);
   
    dbconn.query(sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            console.log(rows);
            res.json({ "result": "Success", myteam_info : rows});
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });

});

//팀 정보(수정) 프래그먼트 -------------------
router.post('/team_update', function (req, res) {

    console.log('<<Team/myteam_update>>');

    req.on('data', (data) => {
        var update_data_array = [];
        var inputData = JSON.parse(data); // JSON data 받음
        console.log('input_data : ' + inputData);
        var sql_update = 'update best_matching.team set phonenumber = ?, age_avg = ?, level=?, location=?, week=?, comment=? where team_name = ? ';
        update_data_array.push(inputData.phonenumber);
        update_data_array.push(inputData.age_avg);
        update_data_array.push(inputData.level);
        update_data_array.push(inputData.location);
        update_data_array.push(inputData.week);
        update_data_array.push(inputData.comment);
        update_data_array.push(inputData.team_name);
        dbconn.query(sql_update, update_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query Update Success');
                console.log(rows);
                res.json( {"result": "Success"});
                
            } else {
                console.log('Query Update Error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});

//------------------------------------------

//팀원 조회----------------------------------
router.get('/myteam_list', function (req, res) {
    console.log('<<Team/myteam_list_get>>');

    var team_name = req.query.team_name;
    
    var data_array = [];
    var sql=  'select id, name, age, location, phone, position from best_matching.user where team_name= ?';
    console.log('team_name = '+ team_name);
    data_array.push(team_name);

    dbconn.query(sql, data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result: Success)');
            console.log(rows);
            res.json({ "result": "Success", member_info : rows});
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });

});


//------------------------------------------
//팀 탈퇴 버튼 -------------------
router.post('/myteam_drop', function (req, res) {

    console.log('<<Team/myteam_drop>>');

    req.on('data', (data) => {
        var update_data_array = [];
        var inputData = JSON.parse(data); // JSON data 받음
        console.log('input_data : ' + inputData);
        var sql_update = 'update user set team_name = null where id=?';

        update_data_array.push(inputData.id);
       
        dbconn.query(sql_update, update_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query Update Success');
                console.log(rows);
                res.json( {"result": "Success"});
                
            } else {
                console.log('Query Update Error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});

//------------------------------------------
//------------------------------------------
router.post('/create', function (req, res) {

    console.log('<<Team/create>>');

    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음
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
                res.json( {"result": "Success"});
                
            } else {
                console.log('Query Update Error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});

router.get('/search/:search', function (req, res) {
    console.log('<<Team/search>>');
    var search = req.params.search;
    var search_data_array = [];
    //var Data = JSON.parse(data); // JSON data 받음
    var sql;
    //console.log("session~~~~~~~~~~~~~~~~~~~~~~~~~"+req.session.id);
    console.log('Search ='+ search);
    if (search == "none") {
        sql = 'select * from best_matching.team';
    }
    else {
        sql = 'select * from best_matching.team where team_name like ? or location like ?';
        search_data_array.push("%" + search + "%");
        search_data_array.push("%" + search + "%");
    }

    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                console.log(rows);
                res.json({ "result": "Success" ,team_info : rows});
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});

router.post('/join', function (req, res) {
    console.log('<<Team/join>>');
    req.on('data', (data) => {
        var check_data_array= [];
        var Data = JSON.parse(data); // JSON data 받음
        var user_id = Data.user_id;
        check_data_array.push(Data.team_name);
        check_data_array.push(Data.user_id);
        var check_sql = 'select * from best_matching.team_waiting where team_name = ? and user_id = ?'
        dbconn.query(check_sql, check_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                var update_sql = 'update best_matching.user set team name = "Waiting" where user.id = ?';
                dbconn.query(update_sql, user_id, function (err, rows, fields) {//DB connect
                    if (!err) {
                        console.log('Query update success(user_info)');
                    } else {
                        console.log('Query Update Error(user_info) : ' + err);
                        res.json({ "result": 400 });
                    }
                });
                if (rows.length== 0) {
                    var sql = 'insert into best_matching.team_waiting(team_name,user_id) values(?,?)';
                    dbconn.query(sql, check_data_array, function (err, rows, fields) {//DB connect
                        if (!err) {
                            console.log('Query insert success');
                            res.json({ "result": 200 });
                        } else {
                            console.log('Query insert error : ' + err);
                            res.json({ "result": 404,"err":err });
                        }
                    });
                }
                else{
                    console.log('Query select success(Duplicate application)');
                    res.json({ "result": 202 });
                }
            } else {
                console.log('Query update error : ' + err);
                res.json({ "result": 404,"err":err });
            }
        });
    });
});


router.post('/join/agreement', function (req, res) {
    console.log('<<Team/agreement>>');
    req.on('data', (data) => {
        var data_array= [];
        var Data = JSON.parse(data); // JSON data 받음
        var team_name =Data.team_name;
        var user_id = Data.user_id;
        var update_sql = 'update best_matching.user set team name = ? where user.id = ?';
        data_array.push(team_name);
        data_array.push(user_id);

        dbconn.query(update_sql, data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query update success');
                var delete_sql = 'DELETE from best_matching.team_waiting where user_id = ?';
                dbconn.query(delete_sql, data_array, function (err, rows, fields) {//DB connect
                    if (!err) {
                        console.log('Query delete success');
                        res.json( {"result": "Success"});
                        
                    } else {
                        console.log('Query Update Error : ' + err);
                        res.json({ "result": err });
                    }
                });
                
            } else {
                console.log('Query Update Error : ' + err);
                res.json( {"result": err});
            }
        });
    });
});
router.get('/team_waiting_list', function (req, res) {
    console.log('<<Team/team_waiting_list>>');
    var search = req.params.team_name;
    var search_data_array = [];
    //var Data = JSON.parse(data); // JSON data 받음
    var sql;
    //console.log("session~~~~~~~~~~~~~~~~~~~~~~~~~"+req.session.id);
    console.log('Search ='+ search);
    
    sql = 'select * from best_matching.user natural join best_matching.team_waiting ';

    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success(result": 200)');
                console.log(rows);
                res.json({ "result": 200 ,team_info : rows});
            }

        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result":404,"err" : err });
        }
    });
});

module.exports = router;
