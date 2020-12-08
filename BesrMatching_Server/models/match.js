var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const match = sequelize.define('match',{
        id :{
            type: DataTypes.INTEGER,
            allowNull: false,      
            primaryKey: true,
        },
        title :{
            type: DataTypes.STRING(10),                        
        },
        ground_name :{
            type: DataTypes.STRING(45),                        
        },
        start_time:{
            type: DataTypes.DATE, 
        },
        end_time:{
            type: DataTypes.DATE, 
        },
        cost :{
            type: DataTypes.INTEGER,
        },
        max_user :{
            type: DataTypes.INTEGER,
        },
        create_time :{
            type: DataTypes.STRING(45),                        
        },
        participants :{
            type: DataTypes.INTEGER,
        },
    }, {
        timestamps: false,
        tableName: 'match'
    });
    return match;
};