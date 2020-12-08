var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const report = sequelize.define('report',{
        id :{
            type: DataTypes.INTEGER,
            allowNull: false,      
            primaryKey: true,
        },    
        title :{
            type: DataTypes.STRING(20),
        },
        user_id:{
            type: DataTypes.STRING(14),
        },
        category : {
            type: DataTypes.STRING(10),
        },        
        target_id:{
            type: DataTypes.STRING(14),
        },
        content :{
            type: DataTypes.STRING(2048),
        },
    }, {
        timestamps: false,
        tableName: 'report'
    });
  
    return report;
};
