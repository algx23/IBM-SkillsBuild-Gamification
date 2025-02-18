package com.example.skillsbuildgamification;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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

  @Autowired
  private StudentRepository studentRepository;

  @Autowired
  private RankService rankService;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @BeforeEach
  public void createTestData() {
    // only use data from the test
    studentRepository.deleteAll();

    Student student = new Student();
    student.setUsername("alex");
    student.setPassword(passwordEncoder.encoder().encode("123456"));
    studentRepository.save(student);
  }

  @Test
  public void contextLoads() {
  };

  // test rank on startup -> should be bronze
  @Test
  public void TestInitialRank() {
    Student student = studentRepository.findByUsername("alex");
    assert student.getUserRank() == UserRank.BRONZE;
  }

  // test bronze
  @Test
  public void TestBronzeRank() {
    Student student = studentRepository.findByUsername("alex");

    for (int i = 0; i <= UserRank.BRONZE.getMaxValue(); i++) {
      student.setPoints(i);
      rankService.setRankOfStudent("alex");
      studentRepository.save(student);
      assert (student.getUserRank() == UserRank.BRONZE);
    }
  }

  @Test
  @Transactional // because i am using rank service which uses a transactional method
  public void TestSilverRank() {
    Student student = studentRepository.findByUsername("alex");
    int minPts = UserRank.SILVER.getMinValue();
    int maxPts = UserRank.SILVER.getMaxValue();

    for (int i = minPts; i <= maxPts; i++) {
      student.setPoints(i);
      rankService.setRankOfStudent("alex");
      studentRepository.save(student);
      assert (student.getUserRank() == UserRank.SILVER);
    }
  }

  @Test
  @Transactional
  public void TestGoldRank() {
    Student student = studentRepository.findByUsername("alex");
    int minPts = UserRank.GOLD.getMinValue();
    int maxPts = UserRank.GOLD.getMaxValue();

    for (int i = minPts; i <= maxPts; i++) {
      student.setPoints(i);
      rankService.setRankOfStudent("alex");
      studentRepository.save(student);
      assert (student.getUserRank() == UserRank.GOLD);
    }

  }

  @Test
  @Transactional
  public void TestPlatinumRank() {
    Student student = studentRepository.findByUsername("alex");
    int minPts = UserRank.PLATINUM.getMinValue();
    int maxPts = UserRank.PLATINUM.getMaxValue();

    for (int i = minPts; i <= maxPts; i++) {
      student.setPoints(i);
      rankService.setRankOfStudent("alex");
      studentRepository.save(student);
      assert (student.getUserRank() == UserRank.PLATINUM);
    }
  }

  @Test
  @Transactional
  public void TestDiamondRank() {
    Student student = studentRepository.findByUsername("alex");
    int minPts = UserRank.DIAMOND.getMinValue();
    int maxPts = UserRank.DIAMOND.getMaxValue();

    for (int i = minPts; i <= maxPts; i++) {
      student.setPoints(i);
      rankService.setRankOfStudent("alex");
      studentRepository.save(student);
      assert (student.getUserRank() == UserRank.DIAMOND);
    }
  }

  @Test
  @Transactional
  public void TestChampionRank() {
    Student student = studentRepository.findByUsername("alex");
    int minPts = UserRank.CHAMPION.getMinValue();
    int maxPts = UserRank.CHAMPION.getMaxValue();

    for (int i = minPts; i <= maxPts; i++) {
      student.setPoints(i);
      rankService.setRankOfStudent("alex");
      studentRepository.save(student);
      assert (student.getUserRank() == UserRank.CHAMPION);
    }
  }

  @Test
  @Transactional
  public void MegaChampionRank() {
    Student student = studentRepository.findByUsername("alex");
    int minPts = UserRank.MEGA_CHAMPION.getMinValue();
    int maxPts = UserRank.MEGA_CHAMPION.getMaxValue();

    for (int i = minPts; i <= maxPts; i++) {
      student.setPoints(i);
      rankService.setRankOfStudent("alex");
      studentRepository.save(student);
      assert (student.getUserRank() == UserRank.MEGA_CHAMPION);
    }
  }

  @Test
  @Transactional
  public void TestLegendRank() {
    Student student = studentRepository.findByUsername("alex");
    int minPts = UserRank.LEGEND.getMinValue();
    int maxPts = UserRank.LEGEND.getMaxValue();

    for (int i = minPts; i <= maxPts; i++) {
      student.setPoints(i);
      rankService.setRankOfStudent("alex");
      studentRepository.save(student);
      assert (student.getUserRank() == UserRank.LEGEND);
    }
  }

  // test transition between one rank, and the next rank

  @Test
  @Transactional
  public void testBronzeTransition() {
    Student student = studentRepository.findByUsername("alex");
    assert (student.getUserRank().getMaxValue() == 1);
    student.setPoints(student.getUserRank().getMaxValue() + 1);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);
    assert (student.getPoints() == 2);
    assert (student.getUserRank() == UserRank.SILVER);
  }

  @Test
  @Transactional
  public void testSilverTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.SILVER);
    assert (student.getUserRank().getMaxValue() == 3);
    student.setPoints(student.getUserRank().getMaxValue() + 1);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 4);
    assert (student.getUserRank() == UserRank.GOLD);
  }

  @Test
  @Transactional
  public void testGoldTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.GOLD);
    assert (student.getUserRank().getMaxValue() == 5);
    student.setPoints(student.getUserRank().getMaxValue() + 1);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 6);
    assert (student.getUserRank() == UserRank.PLATINUM);
  }

  @Test
  @Transactional
  public void testPlatinumTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.PLATINUM);
    assert (student.getUserRank().getMaxValue() == 7);
    student.setPoints(student.getUserRank().getMaxValue() + 1);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 8);
    assert (student.getUserRank() == UserRank.DIAMOND);
  }

  @Test
  @Transactional
  public void testDiamondTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.DIAMOND);
    assert (student.getUserRank().getMaxValue() == 9);
    student.setPoints(student.getUserRank().getMaxValue() + 1);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 10);
    assert (student.getUserRank() == UserRank.CHAMPION);
  }

  @Test
  @Transactional
  public void testChampionTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.CHAMPION);
    assert (student.getUserRank().getMaxValue() == 11);
    student.setPoints(student.getUserRank().getMaxValue() + 1);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 12);
    assert (student.getUserRank() == UserRank.MEGA_CHAMPION);
  }

  @Test
  @Transactional
  public void testMegaChampionTransition() {
    Student student = studentRepository.findByUsername("alex");
    student.setUserRank(UserRank.MEGA_CHAMPION);
    assert (student.getUserRank().getMaxValue() == 13);
    student.setPoints(student.getUserRank().getMaxValue() + 1);
    rankService.setRankOfStudent(student.getUsername());
    studentRepository.save(student);

    assert (student.getPoints() == 14);
    assert (student.getUserRank() == UserRank.LEGEND);
  }

}
