var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const notice = sequelize.define('notice',{
        id :{
            type: DataTypes.INTEGER,
            allowNull: false,      
            primaryKey: true,
        },
        title :{
            type: DataTypes.STRING(20),
        },
        create_time : {
            type: DataTypes.STRING(45),
        },        
        content :{
            type: DataTypes.STRING(2048),
        },
    }, {
        timestamps: false,
        tableName: 'notice'
    });
  
    return notice;
};
