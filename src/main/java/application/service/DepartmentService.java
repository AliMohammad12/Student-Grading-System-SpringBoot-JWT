package application.service;

import application.model.Department;
import application.repository.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll();
    }
    public Department findById(int departmentId) {
        Optional<Department> departmentOptional = departmentRepository.findById(departmentId);
        return departmentOptional.orElse(null);
    }
}
