package com.example.skillsbuildgamification.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.skillsbuildgamification.model.UserRank;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.model.UserRank;
import com.example.skillsbuildgamification.repository.StudentRepository;

import jakarta.transaction.Transactional;

@Service
public class RankService {

  @Autowired
  private StudentRepository studentRepository;

  @Transactional
  public void setRankOfStudent(String username) {
    Student student = studentRepository.findByUsername(username);
    int pts = student.getPoints();
    for (UserRank r : UserRank.values()) {
      if (pts >= r.getMinValue() && pts <= r.getMaxValue()) {
        student.setUserRank(r);
        break;
      }
    }
    studentRepository.save(student);
  }
}
