const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)


router.get('/', function (req, res) {
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