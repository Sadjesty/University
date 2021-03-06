package com.foxminded.university.domain;

import com.foxminded.university.dao.DAOException;
import com.foxminded.university.dao.StudentDAO;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Random;

import static java.nio.file.Files.readAllLines;

public class StudentGenerator {
    private final StudentDAO dao;
    private List<String> firstNames;
    private List<String> lastNames;

    public StudentGenerator() {
        dao = new StudentDAO();
    }

    public StudentGenerator(StudentDAO dao) {
        this.dao = dao;
    }

    public boolean generateTwoHundredStudents() throws DomainException {
        boolean success = false;
        getFirstAndLastNames();

        for (int i = 0; i < 200; i++) {
            try {
                dao.addNewStudentToBase(generateName(firstNames, i), generateName(lastNames, i));
                success = true;
            } catch (DAOException e) {
                e.printStackTrace();
                throw new DomainException();
            }
        }

        return success;
    }

    public boolean assignTwoHundredStudentsToGroups() throws DomainException {
        boolean success = false;

        for (int i = 1; i <= 200; i++) {
            Random random = new Random(i);
            try {
                dao.setStudentToGroup(i, random.nextInt(10) + 1);
                success = true;
            } catch (DAOException e) {
                e.printStackTrace();
                throw new DomainException();
            }
        }

        return success;
    }

    public boolean assignTwoHundredStudentsToCourses() throws DomainException {
        boolean success = false;

        for (int i = 1; i <= 200; i++) {
            Random random = new Random(i);
            int courseID = random.nextInt(7) + 1;
            for (int j = random.nextInt(3); j < 3; j++) {
                try {
                    dao.setStudentToCourse(i, courseID + j);
                    success = true;
                } catch (DAOException e) {
                    e.printStackTrace();
                    throw new DomainException();
                }
            }
        }

        return success;
    }

    public String generateName(List<String> names, int seed) throws DomainException {
        Random random = new Random(seed);
        if (names != null) {
            return names.get(random.nextInt(names.size()));
        } else {
            throw new DomainException();
        }
    }

    public boolean getFirstAndLastNames() throws DomainException {
        boolean success = true;

        try {
            Path firstNamesPath = Paths.get(Objects.requireNonNull(StudentGenerator.class
                    .getClassLoader()
                    .getResource("firstNames"))
                    .toURI());
            Path lastNamesPath = Paths.get(Objects.requireNonNull(StudentGenerator.class
                    .getClassLoader()
                    .getResource("lastNames"))
                    .toURI());
            firstNames = new ArrayList<>(readAllLines(firstNamesPath));
            lastNames = new ArrayList<>(readAllLines(lastNamesPath));
            success = true;
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new DomainException();
        }

        return success;
    }
}
