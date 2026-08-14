import { DataTypes, Model, Optional } from 'sequelize';
import { sequelize } from '../config/db.config';

export interface VehicleAttributes {
    id: string;
    licensePlate: string;
    make: string;
    model: string;
    color?: string;
    vehicleType: 'CAR' | 'MOTORCYCLE' | 'TRUCK' | 'VAN' | 'SUV' | 'EV';
    ownerId: string;
    status: 'IN' | 'OUT';
    currentSpaceId?: string | null;
    entryTime?: Date | null;
    exitTime?: Date | null;
    createdAt?: Date;
    updatedAt?: Date;
}

export interface VehicleCreationAttributes extends Optional<VehicleAttributes, 'id' | 'status' | 'color' | 'vehicleType' | 'currentSpaceId' | 'entryTime' | 'exitTime'> {}

export class Vehicle extends Model<VehicleAttributes, VehicleCreationAttributes> implements VehicleAttributes {
    public id!: string;
    public licensePlate!: string;
    public make!: string;
    public model!: string;
    public color!: string;
    public vehicleType!: 'CAR' | 'MOTORCYCLE' | 'TRUCK' | 'VAN' | 'SUV' | 'EV';
    public ownerId!: string;
    public status!: 'IN' | 'OUT';
    public currentSpaceId!: string | null;
    public entryTime!: Date | null;
    public exitTime!: Date | null;

    public readonly createdAt!: Date;
    public readonly updatedAt!: Date;
}

Vehicle.init({
    id: {
        type: DataTypes.STRING(36),
        primaryKey: true,
    },
    licensePlate: {
        type: DataTypes.STRING(50),
        allowNull: false,
    },
    make: {
        type: DataTypes.STRING(50),
        allowNull: false,
    },
    model: {
        type: DataTypes.STRING(50),
        allowNull: false,
    },
    color: {
        type: DataTypes.STRING(30),
        allowNull: true,
        defaultValue: 'Black',
    },
    vehicleType: {
        type: DataTypes.ENUM('CAR', 'MOTORCYCLE', 'TRUCK', 'VAN', 'SUV', 'EV'),
        defaultValue: 'CAR',
    },
    ownerId: {
        type: DataTypes.STRING(50),
        allowNull: false,
    },
    status: {
        type: DataTypes.ENUM('IN', 'OUT'),
        defaultValue: 'OUT',
    },
    currentSpaceId: {
        type: DataTypes.STRING(50),
        allowNull: true,
    },
    entryTime: {
        type: DataTypes.DATE,
        allowNull: true,
    },
    exitTime: {
        type: DataTypes.DATE,
        allowNull: true,
    },
}, {
    sequelize,
    tableName: 'vehicles',
    timestamps: true,
});