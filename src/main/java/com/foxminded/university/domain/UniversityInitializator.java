package com.foxminded.university.domain;

import com.foxminded.university.dao.DAOException;
import com.foxminded.university.dao.TablesCreatorDAO;

public class UniversityInitializator {

    private final TablesCreatorDAO tablesCreator = new TablesCreatorDAO();
    private final CourseGenerator courseGenerator = new CourseGenerator();
    private final GroupGenerator groupGenerator = new GroupGenerator();
    private final StudentGenerator studentGenerator = new StudentGenerator();

    public void initializeUniversityData() {
        try {
            tablesCreator.createTables();

            courseGenerator.generateAllCourses();
            groupGenerator.generateGroups();
            studentGenerator.generateTwoHundredStudents();

            studentGenerator.assignTwoHundredStudentsToGroups();

            tablesCreator.createStudentCoursesTable();
            studentGenerator.assignTwoHundredStudentsToCourses();
        } catch (DAOException | DomainException e) {
            e.printStackTrace();
        }
    }
}