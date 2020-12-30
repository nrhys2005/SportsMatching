const mysql = require('mysql');
const os = require('os');  //호스트 이름을 가져오기 위한 모듈

const dbconnInfo = {
	dev:{
		host: 'localhost',
		port: '3306',
		user: 'root',
		password: 'root',
		database: 'best_matching', 
		multipleStatements : true,
	}
};

const dbconnection = {
	init : function(){
		var hostname = os.hostname();
		if(hostname === 'SANGHUN'){
			return mysql.createConnection(dbconnInfo.dev);	//로컬개발환경
        }
        // else{
		// 	return mysql.createConnection(dbconnInfo.real);	//cafe24 서버환경
		// }
	},
	
	dbopen : function(con){
		con.connect(function(err){
			if(err){
				console.error("mysql connection error : " + err);
			}else{
				console.info("mysql connection successfully.");
			}
		});
	}
};


module.exports = dbconnection;