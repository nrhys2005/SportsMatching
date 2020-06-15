const express = require('express');
const router = express.Router();
const dbConObj = require('../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


router.get('/', function (req, res) {
    var sql = 'select * from ground';
    dbconn.query(sql, function (err, rows, fields) {//DB connect
        var List = new Array();
        if (!err) {
            console.log('Query Select Success');
            console.log("팀 세션 "+ req.session.id)
            for (var i = 0; i < rows.length; i++) {
                var data = new Object();
                data.name = rows[i].name;
                data.lat = rows[i].latitude;
                data.lon = rows[i].longtitude;
                data.price = rows[i].price;

                List.push(data);
            }
            var jsonData = JSON.stringify(List);
            var senddata = JSON.parse(jsonData);
            console.log(senddata);
            info = jsonData;

            //var team_info = JSON.stringify(rows);

            res.json(
                {
                    ground_info : info,
                    result: 'Success'
                });
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});


router.get('/search', function (req, res) {
    console.log('<<ground/search>>');
    var latitude = req.query.latitude;
    var longtitude = req.query.longtitude;
    var sql;
    var search_data_array = [];
    var sort = false;
    if(latitude=="none" || longtitude=="none"){
        sql = 'select * from ground';
    }
    else{
        sql = 'select * from ground';
        sort = true;
        console.log('search(latitude, longtitude) : ' +latitude +longtitude );
    }
    console.log('search(latitude, longtitude) : ' + search_data_array);
    console.log("구장 세션 "+ req.session.id)
    //console.log("세션 아이디"+req.session.user.user_id);
    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select False(no find)');
                res.json({ "result": "no find" });
            }
            else {
                console.log('Query Select Success');
                var list = new Array();
                
                if(sort){
                    for(var i=0; i<rows.length; i++){
                        var data = new Object();
                        data.id = rows[i].id
                        data.name = rows[i].name
                        data.latitude = rows[i].latitude
                        data.longtitude = rows[i].longtitude
                        data.price = rows[i].price
                        data.dis = Math.sqrt(Math.pow((rows[i].latitude-latitude),2)+Math.pow((rows[i].longtitude- longtitude),2))
                        list.push(data);
                    }
                    list.sort((a, b) => parseFloat(a.dis) - parseFloat(b.dis));
                    //data.sort((a, b) => retrun a.dis - b.dis);
                    // data.sort(function(a,b){
                    //     return a.dis < b.dis ? -1 : 1 ;
                    // });
                    //data.sort((a,b)=> a - b );
                    console.log(list);
                    res.json({ "result": "Success", ground_info : list});
                }
                else{
                    res.json({ "result": "Success", ground_info : rows});
                }
                
            }
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});

router.post('/create', function (req, res) {

    console.log('<<ground/create>>');

    req.on('data', (data) => {
        var input_data_array= [];
        var inputData = JSON.parse(data); // JSON data 받음

        //input_data_array.push(inputData.id);// json->array
        input_data_array.push(inputData.name);
        input_data_array.push(inputData.latitude);
        input_data_array.push(inputData.longtitude);
        input_data_array.push(inputData.price);
        console.log('input_data : ' + input_data_array); 

        var sql_insert = 'INSERT INTO best_matching.ground ( name, latitude, longtitude, price) VALUES(?, ?, ?, ?)';
        dbconn.query(sql_insert, input_data_array, function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query insert success');
                res.json({ "result": "Success" });
            } else {
                console.log('Query Error : ' + err);
                res.json({ "result": err });
            }
        });
    });
});

module.exports = router;