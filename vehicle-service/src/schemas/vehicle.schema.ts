import { z } from 'zod';

export const registerVehicleSchema = z.object({
    id: z.string().optional(),
    licensePlate: z.string().min(2, 'License plate must have at least 2 characters'),
    make: z.string().min(1, 'Make is required'),
    model: z.string().min(1, 'Model is required'),
    color: z.string().optional().default('Black'),
    vehicleType: z.enum(['CAR', 'MOTORCYCLE', 'TRUCK', 'VAN', 'SUV', 'EV']).default('CAR'),
    ownerId: z.string().min(1, 'Owner ID is required'),
});

export const updateStatusSchema = z.object({
    status: z.enum(['IN', 'OUT'], {
        errorMap: () => ({ message: "Status must be either 'IN' or 'OUT'" }),
    }),
    spaceId: z.string().optional(),
});

export const updateVehicleSchema = z.object({
    licensePlate: z.string().min(2).optional(),
    make: z.string().min(1).optional(),
    model: z.string().min(1).optional(),
    color: z.string().optional(),
    vehicleType: z.enum(['CAR', 'MOTORCYCLE', 'TRUCK', 'VAN', 'SUV', 'EV']).optional(),
});
