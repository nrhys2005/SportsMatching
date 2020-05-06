var express = require('express');
var router = express.Router();

var dbConObj = require('../../config/db_info');	//디비 정보 import
var dbconn = dbConObj.init(); //sql 실행결과( results(배열 + json 형태)에 저장)

let users = [
    {
      id: 1,
      name: 'alice'
    },
    {
      id: 2,
      name: 'bek'
    },
    {
      id: 3,
      name: 'chris'
    }
]
  
router.get('/users', (req, res) => {
    console.log('who get in here/users');
    res.json(users);
 });
 

router.post('/signup', function (req, res) {
    
    console.log('post Success');
    
    
    var input_data;

    req.on('data', (data) => {
        inputData = JSON.parse(data);
    });
    req.on('end', () => {
        console.log('inputData : ');
         console.log("user_id : "+inputData.id + " , name : "+inputData.name, "pw : "+inputData.pw + " , email : "+inputData.email);
    });
    //console.log("user_id : "+inputData.id + " , name : "+inputData.name, "pw : "+inputData.pw + " , email : "+inputData.email);
    var params = ['test title','testuser','testpw','testcontent'];
    console.log('inputData : ');
    console.log(inputData);
    console.log('params : ');
    console.log(params);

    // var input_data_str = input_data;
    // console.log(inputData);
    var sql = 'INSERT INTO user (id, name, pw , email) VALUES(?, ?, ?, ?)';
    dbconn.query(sql,inputData, function (err, rows, fields) {
        if (!err) {
            // console.log(rows);
            // console.log(fields);
            // var result = 'rows : ' + JSON.stringify(rows) + '<br><br>' +
            //     'fields : ' + JSON.stringify(fields);
            console.log('query insert success');
            res.write("Success");
            res.end();
        } else {
            console.log('Query Error : ' + err);
            res.write(err);
            res.end();
        }
    });
});

router.get('/check', function (req, res, next) {
   
    var sql = 'SELECT * FROM user'; 
    // var param_id = req.body.user_id;
    // var param_name = req.body.name;
    // console.log('id = '+param_id);
    // console.log('name  = '+param_name);
    var find_id = 'jong1236'
    dbconn.query(sql,function (err, rows, fields) {
        if(!err){
            var check = false;
            for(var i =0;i<rows.length;i++){
                if(rows[i].id==find_id){
                    console.log('duplication');
                    res.send('duplication');
                    check=true;
                    break;
                }
            }
            
            if(!check){
                console.log('no duplication');
                res.send('no duplication');
            }
            //res.send('success');
        }else{
            
            res.send(err);
            console.log(err);
        }
    });
});

module.exports = router;