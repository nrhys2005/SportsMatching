var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const session = sequelize.define('session',{
        session_id :{
            type: DataTypes.STRING(500),
            allowNull: false,      
            primaryKey: true,
        },    
        user_id:{
            type: DataTypes.STRING(14),
        },
        date :{
            type: DataTypes.STRING(45),
        },
    }, {
        timestamps: false,
        tableName: 'session'
    });
  
    return session;
};
