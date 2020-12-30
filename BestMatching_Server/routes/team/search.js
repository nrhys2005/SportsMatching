const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//팀 검색
router.get('/:search', function (req, res) {
    console.log('<<Team/search>>');
    var result_code = 404;
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
                result_code=202;
                res.json({ "result": result_code });
            }
            else {
                console.log('Query Select Success(result": "Success)');
                //console.log(rows);
                result_code=200;
                res.json({ "result": result_code ,team_info : rows});
            }

        } else {
            console.log('Query Select Error : ' + err);
            result_code=404;
            res.json({ "result": result_code });
        }
    });
});
module.exports = router;