package com.example.skillsbuildgamification.controller;

import com.example.skillsbuildgamification.model.Course;
import com.example.skillsbuildgamification.model.Goal;
import com.example.skillsbuildgamification.model.Student;
import com.example.skillsbuildgamification.service.GoalService;
import com.example.skillsbuildgamification.repository.CourseRepository;
import com.example.skillsbuildgamification.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class DashBoardController {

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private GoalService goalService;

    private final CourseRepository courseRepository;

    public DashBoardController(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    @RequestMapping("{username}/dashboard-page")
    public String dashboardPage(@PathVariable String username, Model model) {
        Student student = studentRepository.findByUsername(username);

        if (student == null) {
            return "redirect:/login-page";
        }

        List<Course> courses = (List<Course>) courseRepository.findAll();
        List<Goal> activeGoals = goalService.getActiveGoals(username);

        Map<Integer, Integer> goalProgressMap = new HashMap<>();
        boolean goalJustCompleted = false;

        for (Goal goal : activeGoals) {
            boolean completed = goalService.checkAndCompleteGoal(goal);
            if (completed) {
                goalJustCompleted = true;
            }
            goalProgressMap.put(goal.getGoalID(), goalService.getGoalProgress(goal));
        }

        if (goalJustCompleted) {
            model.addAttribute("congratsMessage", "🎉 Congratulations! You've completed your goal! 🎉");
        }

        model.addAttribute("courses", courses);
        model.addAttribute("username", username);
        model.addAttribute("enrollments", student.getEnrollments());
        model.addAttribute("activeGoals", activeGoals);
        model.addAttribute("goalProgressMap", goalProgressMap);
        model.addAttribute("isAdmin", student.isAdmin());

        return "dashboard/dashboard";
    }

    @RequestMapping("/{username}/completedGoals")
    public String completedGoalsPage(@PathVariable String username, Model model) {
        List<Goal> completedGoals = goalService.getCompletedGoals(username);

        model.addAttribute("completedGoals", completedGoals);
        model.addAttribute("username", username);

        return "dashboard/completedGoals";
    }

}





