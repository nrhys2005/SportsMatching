const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
 
//매치 검색
router.get('/', function (req, res) {
    console.log('<<match/search>>');
    var search = req.query.search;
    var search_data_array = [];
    let today = new Date(new Date().toISOString(). replace(/T/, ' ').replace(/\..+/, ''));
    
    //var Data = JSON.parse(data); // JSON data 받음
    var sql;
    console.log('Search = '+ search);
    if (search == "none") {
        sql = "select distinct id, title, ground_name, DATE_FORMAT(start_time,'%Y-%m-%d %H:%i') as start_time, DATE_FORMAT(end_time,'%Y-%m-%d %H:%i') as end_time, cost, max_user, create_time, participants from best_matching.match,best_matching.matching_user where match.id = matching_user.match_id and match.end_time>=NOW() and match.max_user <> match.participants and match.participants <> 0 and matching_user.match_id not in (select matching_user.match_id from best_matching.matching_user where user_id = ?)";
        search_data_array.push( req.query.user_id);
    }
    else {
        sql = "select distinct id, title, ground_name, DATE_FORMAT(start_time,'%Y-%m-%d %H:%i') as start_time, DATE_FORMAT(end_time,'%Y-%m-%d %H:%i') as end_time, cost, max_user, create_time, participants from best_matching.match,best_matching.matching_user where match.id = matching_user.match_id and match.end_time>=NOW() and match.max_user <> match.participants and match.participants <> 0 and match.title like ? and matching_user.match_id not in (select matching_user.match_id from best_matching.matching_user where user_id = ?)";
        search_data_array.push( req.query.user_id);
        search_data_array.push('%' + search + '%');
    }
    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log(rows[0].start_time);
                console.log('Query Select Success(result": "Success)');
                res.json({ "result": "Success",match_info : rows });
            }
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});

router.get('/team_match', function (req, res) {
    console.log('<<match/search/team_match>>');
    var search = req.query.search;
    var search_data_array = [];
    let today = new Date(new Date().toISOString(). replace(/T/, ' ').replace(/\..+/, ''));
    
    //var Data = JSON.parse(data); // JSON data 받음
    var sql;
    console.log('Search = '+ search);
    if (search == "none") {
        sql = "select distinct id, title, ground_name, DATE_FORMAT(start_time,'%Y-%m-%d %H:%i') as start_time, DATE_FORMAT(end_time,'%Y-%m-%d %H:%i') as end_time, cost, max_user,min_user, create_time, participants from best_matching.team_match,best_matching.team_matching_user where team_match.id = team_matching_user.team_match_id and team_match.end_time>=NOW()  and team_match.max_user <> team_match.participants and team_match.participants <> 0 and team_matching_user.team_match_id not in (select team_matching_user.team_match_id from best_matching.team_matching_user where user_id = ?)";
        search_data_array.push( req.query.user_id);
    }
    else {
        sql = "select distinct id, title, ground_name, DATE_FORMAT(start_time,'%Y-%m-%d %H:%i') as start_time, DATE_FORMAT(end_time,'%Y-%m-%d %H:%i') as end_time, cost, max_user,min_user, create_time, participants from best_matching.team_match,best_matching.team_matching_user where team_match.id = team_matching_user.team_match_id and team_match.end_time>=NOW() and team_match.max_user <> team_match.participants and team_match.participants <> 0 and team_match.title like ? and team_matching_user.team_match_id not in (select team_matching_user.team_match_id from best_matching.team_matching_user where user_id = ?)";
        search_data_array.push( req.query.user_id);
        search_data_array.push('%' + search + '%');
    }
    dbconn.query(sql, search_data_array, function (err, rows, fields) {//DB connect
        if (!err) {
            if (rows.length == 0) {
                console.log('Query Select Success("result": "no find")');
                res.json({ "result": "no find" });
            }
            else {
                console.log(rows[0].start_time);
                console.log('Query Select Success(result": "Success)');
                res.json({ "result": "Success",match_info : rows });
            }
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": err });
        }
    });
});


module.exports = router;