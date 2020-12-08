var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const match = sequelize.define('matching_user',{
     
    }, {
        timestamps: false,
        tableName: 'matching_user'
    });
    return match;
};