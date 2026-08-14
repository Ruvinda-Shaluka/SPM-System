import { Request, Response } from 'express';
import { Vehicle } from '../models/vehicle.model';
import { v4 as uuidv4 } from 'uuid';

export const registerVehicle = async (req: Request, res: Response): Promise<void> => {
    try {
        const { id, licensePlate, make, model, color, vehicleType, ownerId } = req.body;
        
        const vehicleId = id || uuidv4();

        const existing = await Vehicle.findOne({ where: { licensePlate } });
        if (existing) {
            res.status(409).json({ error: `Vehicle with license plate '${licensePlate}' already exists` });
            return;
        }

        const newVehicle = await Vehicle.create({
            id: vehicleId,
            licensePlate: licensePlate.toUpperCase().trim(),
            make,
            model,
            color: color || 'Black',
            vehicleType: vehicleType || 'CAR',
            ownerId,
            status: 'OUT',
            currentSpaceId: null,
            entryTime: null,
            exitTime: null,
        });

        res.status(201).json(newVehicle);
    } catch (error: any) {
        console.error('Error registering vehicle:', error);
        res.status(500).json({ error: error.message || 'Error registering vehicle to database' });
    }
};

export const getAllVehicles = async (_req: Request, res: Response): Promise<void> => {
    try {
        const vehicles = await Vehicle.findAll({
            order: [['createdAt', 'DESC']],
        });
        res.json(vehicles);
    } catch (error: any) {
        res.status(500).json({ error: error.message || 'Error retrieving vehicles' });
    }
};

export const getVehicle = async (req: Request, res: Response): Promise<void> => {
    try {
        const vehicle = await Vehicle.findByPk(req.params.id);
        if (vehicle) {
            res.json(vehicle);
        } else {
            res.status(404).json({ error: 'Vehicle not found' });
        }
    } catch (error: any) {
        res.status(500).json({ error: error.message || 'Error retrieving vehicle' });
    }
};

export const getVehiclesByOwner = async (req: Request, res: Response): Promise<void> => {
    try {
        const vehicles = await Vehicle.findAll({
            where: { ownerId: req.params.ownerId },
            order: [['createdAt', 'DESC']],
        });
        res.json(vehicles);
    } catch (error: any) {
        res.status(500).json({ error: error.message || 'Error retrieving vehicles for owner' });
    }
};

export const updateVehicle = async (req: Request, res: Response): Promise<void> => {
    try {
        const vehicle = await Vehicle.findByPk(req.params.id);
        if (!vehicle) {
            res.status(404).json({ error: 'Vehicle not found' });
            return;
        }

        const { licensePlate, make, model, color, vehicleType } = req.body;
        if (licensePlate) vehicle.licensePlate = licensePlate.toUpperCase().trim();
        if (make) vehicle.make = make;
        if (model) vehicle.model = model;
        if (color) vehicle.color = color;
        if (vehicleType) vehicle.vehicleType = vehicleType;

        await vehicle.save();
        res.json(vehicle);
    } catch (error: any) {
        res.status(500).json({ error: error.message || 'Error updating vehicle' });
    }
};

export const updateVehicleStatus = async (req: Request, res: Response): Promise<void> => {
    try {
        const { status, spaceId } = req.body;

        const vehicle = await Vehicle.findByPk(req.params.id);
        if (!vehicle) {
            res.status(404).json({ error: 'Vehicle not found' });
            return;
        }

        vehicle.status = status;
        if (status === 'IN') {
            vehicle.entryTime = new Date();
            if (spaceId) vehicle.currentSpaceId = spaceId;
        } else if (status === 'OUT') {
            vehicle.exitTime = new Date();
            vehicle.currentSpaceId = null;
        }

        await vehicle.save();
        res.json({
            message: `Vehicle status updated to ${status}`,
            vehicle,
        });
    } catch (error: any) {
        res.status(500).json({ error: error.message || 'Error updating vehicle status' });
    }
};

export const deleteVehicle = async (req: Request, res: Response): Promise<void> => {
    try {
        const vehicle = await Vehicle.findByPk(req.params.id);
        if (!vehicle) {
            res.status(404).json({ error: 'Vehicle not found' });
            return;
        }

        await vehicle.destroy();
        res.status(204).send();
    } catch (error: any) {
        res.status(500).json({ error: error.message || 'Error deleting vehicle' });
    }
};