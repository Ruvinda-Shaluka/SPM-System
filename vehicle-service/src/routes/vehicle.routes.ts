import { Router } from 'express';
import {
    registerVehicle,
    getAllVehicles,
    getVehicle,
    getVehiclesByOwner,
    updateVehicle,
    updateVehicleStatus,
    deleteVehicle,
} from '../controllers/vehicle.controller';
import { validate } from '../middleware/validate.middleware';
import {
    registerVehicleSchema,
    updateStatusSchema,
    updateVehicleSchema,
} from '../schemas/vehicle.schema';

const router = Router();

router.post('/', validate(registerVehicleSchema), registerVehicle);
router.get('/', getAllVehicles);
router.get('/:id', getVehicle);
router.get('/owner/:ownerId', getVehiclesByOwner);
router.put('/:id', validate(updateVehicleSchema), updateVehicle);
router.put('/:id/status', validate(updateStatusSchema), updateVehicleStatus);
router.delete('/:id', deleteVehicle);

export default router;