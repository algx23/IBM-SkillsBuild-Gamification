package com.example.skillsbuildgamification;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.skillsbuildgamification.config.PasswordEncoder;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.model.UserRank;
import com.example.skillsbuildgamification.repository.StudentRepository;
import com.example.skillsbuildgamification.service.RankService;

import jakarta.transaction.Transactional;

@ExtendWith(SpringExtension.class)
@SpringBootTest()
public class RankTests {

  @MockitoBean
  private StudentRepository studentRepository;

  @Autowired
  private RankService rankService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  public void createTestData() {
    // Mock the functions of the student repository
    assert studentRepository != null;
    Student student = new Student();
    student.setUsername("alex");
    student.setPassword("123456");
    when(studentRepository.findByUsername(anyString())).thenReturn(student);
  }

  @Test
  public void contextLoads() {
  }

  // test rank on startup -> should be bronze
  @Test
  public void TestInitialRank() {
    Student student = studentRepository.findByUsername("alex");
    assert student.getUserRank() == UserRank.BRONZE;
  }

  //  test user rank
  @Test
  @Transactional
  public void testRankingThresholds() {
    UserRank prevRank = null;
    Student student = studentRepository.findByUsername("alex");
//    iterate through each rank, set the students pts to be the minimum of that rank
//    then, iterate through each value in the rank, from minVal to maxVal, and check the
//    rank is not updated unexpectedly
    for (UserRank r : UserRank.values()) {
      for (int i = r.getMinValue(); i <= r.getMaxValue(); i++) {
        student.setPoints(i);
        rankService.setRankOfStudent(student.getUsername());
        studentRepository.save(student);
        assert (student.getUserRank() == r);
      }
    }
  }

// test transition between one rank, and the next rank. Set the points to be 1 more than the maximum for the current rank
//  and check the rank moves to the next one
//  also check that the points have been updated correctly
  @Test
  @Transactional
  public void testBronzeTransition() {
    Student student = studentRepository.findByUsername("alex");
    assert (student.getUserRank().getMaxValue() == 10);
    student.setPoints(student.getUserRank().getMaxValue() + 10);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);
    assert (student.getPoints() == 20);
    assert (student.getUserRank() == UserRank.SILVER);
  }

  @Test
  @Transactional
  public void testSilverTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.SILVER);
    assert (student.getUserRank().getMaxValue() == 30);
    student.setPoints(student.getUserRank().getMaxValue() + 10);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 40);
    assert (student.getUserRank() == UserRank.GOLD);
  }

  @Test
  @Transactional
  public void testGoldTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.GOLD);
    assert (student.getUserRank().getMaxValue() == 50);
    student.setPoints(student.getUserRank().getMaxValue() + 10);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 60);
    assert (student.getUserRank() == UserRank.PLATINUM);
  }

  @Test
  @Transactional
  public void testPlatinumTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.PLATINUM);
    assert (student.getUserRank().getMaxValue() == 70);
    student.setPoints(student.getUserRank().getMaxValue() + 10);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 80);
    assert (student.getUserRank() == UserRank.DIAMOND);
  }

  @Test
  @Transactional
  public void testDiamondTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.DIAMOND);
    assert (student.getUserRank().getMaxValue() == 90);
    student.setPoints(student.getUserRank().getMaxValue() + 10);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 100);
    assert (student.getUserRank() == UserRank.CHAMPION);
  }

  @Test
  @Transactional
  public void testChampionTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.CHAMPION);
    assert (student.getUserRank().getMaxValue() == 110);
    student.setPoints(student.getUserRank().getMaxValue() + 10);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 120);
    assert (student.getUserRank() == UserRank.MEGA_CHAMPION);
  }

  @Test
  @Transactional
  public void testMegaChampionTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.MEGA_CHAMPION);
    assert (student.getUserRank().getMaxValue() == 130);
    student.setPoints(student.getUserRank().getMaxValue() + 10);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 140);
    assert (student.getUserRank() == UserRank.LEGEND);
  }


//  Test Level increment by going through each number from 1, to eg 999, then if the number is even, update the level of the student
//  in rankService, and ensure that this has updated to the correct rank by incrementing the expectedLevel for each number This is to
//  check that the level isn't incremented when it isnt supposed to be
  @Test
  @Transactional
  public void testLevel() {
    Student student = studentRepository.findByUsername("alex");
    int expectedLevel = 0;
    assert (student.getLevel() == expectedLevel); // intially make sure it is 0
    for (int i = 0; i < 1000; i++) {
      student.setPoints(i);
      rankService.setLevelOfStudent(student.getUsername());
      if (student.getPoints() % 2 == 0) {
        expectedLevel++;
      }
      assert (student.getLevel() == expectedLevel);
    }
  }
}

