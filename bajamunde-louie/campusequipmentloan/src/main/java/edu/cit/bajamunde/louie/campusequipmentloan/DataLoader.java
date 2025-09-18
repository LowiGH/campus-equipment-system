package edu.cit.bajamunde.louie.campusequipmentloan;

import edu.cit.bajamunde.louie.campusequipmentloan.model.Equipment;
import edu.cit.bajamunde.louie.campusequipmentloan.model.Student;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.EquipmentRepository;
import edu.cit.bajamunde.louie.campusequipmentloan.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    private final EquipmentRepository equipmentRepository;
    private final StudentRepository studentRepository;

    public DataLoader(EquipmentRepository equipmentRepository, StudentRepository studentRepository) {
        this.equipmentRepository = equipmentRepository;
        this.studentRepository = studentRepository;
    }

    @Override
    public void run(String... args) {
        // Only insert if tables are empty
        if (equipmentRepository.count() == 0) {
            equipmentRepository.save(new Equipment("DSLR Camera", "Camera", "CAM-001", true));
            equipmentRepository.save(new Equipment("Projector", "Electronics", "PRJ-001", true));
            equipmentRepository.save(new Equipment("Laptop", "Computer", "LAP-001", true));
        }

        if (studentRepository.count() == 0) {
            studentRepository.save(new Student("S1001", "Alice", "alice@gmail.com"));
            studentRepository.save(new Student("S1002", "Bob", "bob@gmail.com"));
        }
    }
}
