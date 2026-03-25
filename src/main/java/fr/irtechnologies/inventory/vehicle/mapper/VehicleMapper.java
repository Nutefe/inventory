package fr.irtechnologies.inventory.vehicle.mapper;

import fr.irtechnologies.inventory.vehicle.domain.Vehicle;
import fr.irtechnologies.inventory.vehicle.dto.VehicleDto;

public class VehicleMapper {

    public static VehicleDto toDto(Vehicle vehicle) {
        VehicleDto dto = new VehicleDto();
        dto.setId(vehicle.getId());
        dto.setTenantId(vehicle.getTenantId());
        dto.setDealer(vehicle.getDealer());
        dto.setModel(vehicle.getModel());
        dto.setPrice(vehicle.getPrice());
        dto.setStatus(vehicle.getStatus());
        return dto;
    }

    public static Vehicle toVehicle(VehicleDto dto) {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(dto.getId());
        vehicle.setTenantId(dto.getTenantId());
        vehicle.setDealer(dto.getDealer());
        vehicle.setModel(dto.getModel());
        vehicle.setPrice(dto.getPrice());
        vehicle.setStatus(dto.getStatus());
        return vehicle;
    }
}
