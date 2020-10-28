const e = require('express');
const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

//게시판 등록
router.post('/create', function (req, res) {
    console.log('<<Team/team_board(post)>>');
    var team_name = req.body.team_name
    var title = req.body.title
    
    var select_sql = "select member_count from team where team_name = ?"
    dbconn.query(select_sql, req.body.team_name, function (err, rows, fields) {//DB connect
        if (!err) {
            var member_count = rows[0].member_count
            var data_array = [team_name,title,member_count,0,0];
            var sql = 'insert into best_matching.team_board(team_name, title, max_part_count, no_part_count, part_count) values ?';
            dbconn.query(sql, [data_array], function (err, rows, fields) {//DB connect
                if (!err) {
                    console.log('Query insert success(result: Success)');
                    res.json({ "result": "Success" });
                } else {
                    console.log('Query insert Error : ' + err);
                    res.json({ "result": 'Fail'});
                }
            });
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": "Fail" });
        }
    });
});
router.get('/list_detail', function (req, res) {
    console.log('<<Team/team_board/list_detail>>');
    var board_id =  req.query.board_id
    var user_id = req.query.user_id
    var select_sql = "select * from best_matching.team_board where team_board.id = ?"
    dbconn.query(select_sql, board_id, function (err, rows, fields) {//DB connect
        if (!err) {
            var result =rows;
            var select_sql = "select * from best_matching.team_board_part_list where team_board_part_list.team_board_id = ? and user_id = ?"
            dbconn.query(select_sql, [board_id, user_id], function (err, rows, fields) {//DB connect
                if (!err) {
                    console.log('Query Select Success(result": "Success)');
                    if(rows.length==0)
                        res.json({ "result": "no_vote", result})
                    else
                        res.json({ "result": "vote", result})
                } 
                else {
                    console.log('Query Select Error : ' + err);
                    res.json({ "result": "Fail" });
                }
            });
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": "Fail" });
        }
    });
});

router.get('/list', function (req, res) {
    console.log('<<Team/team_board/list>>');
    var select_sql = "select team_board.id, team_board.title, team_board.team_name, team_board.part_count, team_board.no_part_count, team_board.max_part_count from best_matching.team_board,user where user.team_name = team_board_team_name and user_id=?"
    dbconn.query(select_sql, req.query.team_name, function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result": "Success)');
            res.json({ "result": "Success", rows})
            
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": "Fail" });
        }
    });
});

router.get('/part_list', function (req, res) {
    console.log('<<Team/team_board/part_list>>');
    var select_sql = "select * from best_matching.team_board_part_list whereteam_board_part_list.team_board_id = ? and team_board_part_list.part = ?"
    dbconn.query(select_sql, [req.query.team_board_id,"1"], function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result": "Success)');
            res.json({ "result": "Success", rows})
            
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": "Fail" });
        }
    });
});

router.get('/part_list', function (req, res) {
    console.log('<<Team/team_board/no_part_list>>');
    var select_sql = "select * from best_matching.team_board_part_list whereteam_board_part_list.team_board_id = ? and team_board_part_list.part = ?"
    dbconn.query(select_sql, [req.query.team_board_id,"0"], function (err, rows, fields) {//DB connect
        if (!err) {
            console.log('Query Select Success(result": "Success)');
            res.json({ "result": "Success", rows})
            
        } else {
            console.log('Query Select Error : ' + err);
            res.json({ "result": "Fail" });
        }
    });
});


router.post('/vote', function (req, res) {
    console.log('<<Team/team_board/vote>>');
    var team_board_id = req.body.team_board_id
    var user_id = req.body.user_id
    var state = req.body.state
    var vote = req.body.vote
    if (state == "no") {
        data = [team_board_id,user_id, vote]
        var insert_sql = "insert into best_matching.team_board_part_list(team_board_id,user_id,part) values ?"
        dbconn.query(insert_sql, [data], function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query Select Success(result": "Success)');
                res.json({ "result": "Success"})

            } else {
                console.log('Query Select Error : ' + err);
                res.json({ "result": "Fail" });
            }
        });
    }
    else if(state == "yes") {
        var update_sql = "update best_matching.team_board_part_list set part = ? where team_board_id = ? and user_id = ?"
        dbconn.query(update_sql, [vote,board_id,user_id], function (err, rows, fields) {//DB connect
            if (!err) {
                console.log('Query update Success(result": "Success)');
                res.json({ "result": "Success"})

            } else {
                console.log('Query update Error : ' + err);
                res.json({ "result": "Fail" });
            }
        });
    }
});


module.exports = router;