package com.example.skillsbuildgamification.repository;

import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.model.UserInventory;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserInventoryRepository extends CrudRepository<UserInventory, Long> {
    List<UserInventory> findByStudent(Student student);

    UserInventory findByStudentAndShopItemId(Student student, Long shopItemId);
}
