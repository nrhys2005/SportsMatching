
var models = require('../models');
module.exports = (sequelize, DataTypes) => {
    const team_waiting = sequelize.define('team_waiting', {
        user_id : {
            type: DataTypes.STRING(14),
            allowNull: false,   
        },
        team_name : {
            type: DataTypes.STRING(45),
            allowNull: false,   
        },
      request_time:{
          type: DataTypes.STRING(45),
      }
    }, {
        timestamps: false,
        tableName: 'team_waiting'
    });
    team_waiting.associate = (models)=>{
        models.Team_waiting.belongsTo(models.user, {
            foreignKey: 'user_id'
        });
        models.Team_waiting.belongsTo(models.team, {
            foreignKey: 'team_name'
        });
    };
    return team_waiting;
};