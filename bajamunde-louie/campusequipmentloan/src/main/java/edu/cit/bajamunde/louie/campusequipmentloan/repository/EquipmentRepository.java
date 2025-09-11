package edu.cit.bajamunde.louie.campusequipmentloan.repository;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Equipment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EquipmentRepository extends JpaRepository<Equipment, Long> {
    List<Equipment> findByAvailabilityTrue();
}
