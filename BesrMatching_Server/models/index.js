var path = require('path');
var Sequelize = require('sequelize');

// 환경변수, 실제 배포할 때는 'production'으로 바꿔야한다.
const env = process.env.NODE_ENV || 'development'; 
// config
const config = require(path.join(__dirname,'..','config','config.json'))[env]; 
// db 객체 생성
const db = {}; 
//참고로 config파일에는 'development', 'test', 'production' 키 값을 가진 각각의 설정들이 담겨있다!


let sequelize;

if (config.use_env_variable) {
  sequelize = new Sequelize(process.env[config.use_env_variable], config);
} else {
  sequelize = new Sequelize(config.database, config.username, config.password, config);
}

db.sequelize = sequelize;
db.Sequelize = Sequelize;

db.User = require('./user')(sequelize,Sequelize);
db.Team = require('./team')(sequelize,Sequelize);

module.exports = db;