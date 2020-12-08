var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const team_match = sequelize.define('team_match',{
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
        participants:{
            type: DataTypes.INTEGER,
        },
        max_user :{
            type: DataTypes.INTEGER,
        },
        min_user:{
            type: DataTypes.INTEGER,
        },
        create_time :{
            type: DataTypes.DATE,          
        },
       
    }, {
        timestamps: false,
        tableName: 'team_match'
    });
    team_match.associate = (models)=>{
        models.Team_match.hasMany(models.team_matching_user);
    };
    return team_match;
};