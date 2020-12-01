const path = require('path')
const dotenv = require('dotenv')

var config = {}

dotenv.config({ path: path.join(__dirname, '.env') });
const env = process.env

redis = {}
redis.development = {}
redis.development.host = env.REDISHOST
redis.development.port = env.REDISPORT
redis.development.password = env.REDISPASSWORD
redis.development.secret = env.REDISSECRET

config.redis = redis

module.exports = config