package edu.cit.bajamunde.louie.campusequipmentloan.controller;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Equipment;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.EquipmentRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/equipment")
public class EquipmentController {
    private final EquipmentRepository equipmentRepository;
    public EquipmentController(EquipmentRepository equipmentRepository) {
        this.equipmentRepository = equipmentRepository;
    }

    @GetMapping("/available")
    public List<Equipment> listAvailable() {
        return equipmentRepository.findByAvailabilityTrue();
    }
}
