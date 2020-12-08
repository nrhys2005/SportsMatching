var models = require('../models');
module.exports = (sequelize, DataTypes)=>{
    const team_matching_user = sequelize.define('team_matching_user',{
        user_id:{
            type: DataTypes.STRING(14),
            allowNull: false,
        },
        team_match_id:{
            type: DataTypes.INTEGER,
            allowNull: false,
        },
    }, {
        timestamps: false,
        tableName: 'team_matching_user'
    });
    team_matching_user.associate = (models)=>{
        models.Team_matching_user.belongsTo(models.user, {
            foreignKey: 'user_id'
        });
        models.Team_matching_user.belongsTo(models.team_match, {
            foreignKey: 'match_id'
        });
    };
    return team_matching_user;
};