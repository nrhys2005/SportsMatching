//const { DataTypes } = require("sequelize/types");
var models = require('../models');
module.exports = (sequelize, DataTypes) => {
    const team_board_part_list = sequelize.define('team_board_part_list', {
        team_board_id: {
            type: DataTypes.INTEGER,
            allowNull: false,
        },
        user_id: {
            type: DataTypes.INTEGER,
            allowNull: false,
        },
        part: {
            type: DataTypes.STRING(20),
        }
    }, {
        timestamps: false,
        tableName: 'team_board_part_list'
    });
    team_board_part_list.associate = (models) => {
        models.team_board_part_list.belongsTo(models.user, {
            foreignKey: 'user_id'
        });
        models.team_board_part_list.belongsTo(models.team_board, {
            foreignKey: 'team_board_id'
        });
    };
    return team_board_part_list;
};