const path = require('path')
const dotenv = require('dotenv')

var config = {}

dotenv.config({ path: path.join(__dirname, '.env') });
const env = process.env


mysql = {}
mysql.development = {}
mysql.development.username = env.MYSQLUSERNAME
mysql.development.password = env.MYSQLPASSWORD
mysql.development.database = env.MYSQLDATABASE
mysql.development.host = env.MYSQLHOST
mysql.development.port = env.MYSQLPORT
mysql.development.dialect = env.MYSQLDIALECT


redis = {}
redis.development = {}
redis.development.host = env.REDISHOST
redis.development.port = env.REDISPORT
redis.development.password = env.REDISPASSWORD
redis.development.secret = env.REDISSECRET

config.redis = redis
config.mysql = mysql

module.exports = config