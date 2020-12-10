const express = require('express');
const router = express.Router();
const dbConObj = require('../../config/db_info');   //디비 정보 import
const dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)
const models = require("../../database");

//팀 검색
router.get('/:search', function (req, res) {
    console.log('<<Team/search>>');
    console.log('Search ='+ req.params.search);
    let team_name = ""
    let location = ""
    //'select * from best_matching.team';
    if (req.params.search != "none") {
        //'select * from best_matching.team where team_name like ? or location like ?';
        team_name = "%" + req.params.search + "%"
        location = "%" + req.params.search + "%"
    }
    models.Store.findAll({
        where:{
            team_name : team_name,
            location : location,
        },
    }).then((result) => {
        console.log('Query Select Success(result": "Success)');
        res.json({ "result": 200 ,team_info : result});
    }).catch((err) => {
        console.log('Query Select Success("result": "no find")');
        res.json({ "result": 202 });
    });
});
module.exports = router;