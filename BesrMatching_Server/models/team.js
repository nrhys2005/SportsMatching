//const { DataTypes } = require("sequelize/types");
const { sequelize, Team } = require(".");
var models = require('../models');
module.exports = (sequelize, DataTypes) => {
    const team = sequelize.define('team', {
        team_name: {
            type: DataTypes.STRING(45),
            allowNull: false,
        },
        master_id: {
            type: DataTypes.STRING(14),
        },
        phonenumber: {
            type: DataTypes.STRING(30),
            allowNull: false,
        },
        age_avg: {
            type: DataTypes.STRING(4),
        },
        level: {
            type: DataTypes.STRING(2),
            allowNull: false,
        },
        location: {
            type: DataTypes.STRING(45),
            allowNull: false,
        },
        week: {
            type: DataTypes.STRING(7),
            allowNull: false,
        },
        comment: {
            type: DataTypes.STRING(200),
        },
        create_time: {
            type: DataTypes.STRING(45),
        },
        teammeber_count: {
            type: DataTypes.INTEGER,
        }
    }, {
        timestamps: false,
        tableName: 'team'
    });
    team.associate = (models) => {
        models.Team.hasMany(models.User, {
            foreignKey: 'fk_user_team'
        });
        models.Team.hasMany(models.Team_waiting, {
            foreignKey: 'fk_team_wating_team'
        });
        models.Team.hasMany(models.Team_board, {
            foreignKey: 'fk_team_board_team'
        });
    };
    return team;
};