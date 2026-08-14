import { Request, Response } from 'express';
import { Vehicle } from '../models/vehicle.model';

export const registerVehicle = async (req: Request, res: Response) => {
    try {
        const { id, licensePlate, make, model, ownerId } = req.body;
        
        // Inserts a new record into the MySQL database
        const newVehicle = await Vehicle.create({ 
            id, licensePlate, make, model, ownerId, status: 'OUT' 
        });
        
        res.status(201).json(newVehicle);
    } catch (error) {
        console.error(error);
        res.status(500).send('Error registering vehicle to database');
    }
};

export const getVehicle = async (req: Request, res: Response) => {
    try {
        // findByPk = Find by Primary Key (id)
        const vehicle = await Vehicle.findByPk(req.params.id);
        
        if (vehicle) {
            res.json(vehicle);
        } else {
            res.status(404).send('Vehicle not found');
        }
    } catch (error) {
        res.status(500).send('Error retrieving vehicle');
    }
};

export const updateVehicleStatus = async (req: Request, res: Response) => {
    try {
        const { status } = req.body;
        
        if (status !== 'IN' && status !== 'OUT') {
            return res.status(400).send('Invalid status. Must be IN or OUT.');
        }

        const vehicle = await Vehicle.findByPk(req.params.id);
        
        if (vehicle) {
            vehicle.status = status;
            await vehicle.save(); // Updates the existing record in MySQL
            res.json(vehicle);
        } else {
            res.status(404).send('Vehicle not found');
        }
    } catch (error) {
        res.status(500).send('Error updating vehicle status');
    }
};