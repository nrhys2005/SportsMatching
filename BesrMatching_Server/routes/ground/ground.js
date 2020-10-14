const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//쓰나?? 자신의 위치가 항상 존재한다는 전제하에 search.js만 있어도 될듯 
router.get('/', function (req, res) {
    var sql = 'select * from ground';
    dbconn.query(sql, function (err, rows, fields) {//DB connect
        var List = new Array();
      
        if (!err) {
            console.log('Query Select Success');
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
module.exports = router;