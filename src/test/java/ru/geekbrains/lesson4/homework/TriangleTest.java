package ru.geekbrains.lesson4.homework;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TriangleTest {
    private static Logger logger = LoggerFactory.getLogger(TriangleTest.class);

    @BeforeAll
    static void beforeAll() {
        logger.info("Start of all tests");
    }

    @AfterAll
    static void fterAll() {
        logger.info("End of all tests");
    }

    @Test
    void checkExceptionWhenTriangleSideIsNegative() {
        // logger.debug ("Test: " + Thread.currentThread().getStackTrace()[1].getMethodName());
        assertThatExceptionOfType(FalseInputDataException.class)
                .isThrownBy(() -> new Triangle(-1, 2, 3))
                .withMessageContaining("отрицательн");
    }

    @Test
    void checkExceptionWhenTriangleSideIsNull() {
        assertThatExceptionOfType(FalseInputDataException.class)
                .isThrownBy(() -> new Triangle(1, 2, 0))
                .withMessageContaining("ноль");
    }

    @Test
    void checkExceptionWhenTriangleTwoSidesAreLessThanThirdSide() {
        assertThatExceptionOfType(FalseInputDataException.class)
                .isThrownBy(() -> new Triangle(5, 2, 1))
                .withMessageContaining("меньше");
    }

    @Test
    void checkExceptionWhenSumOfTwoSidesIsEqualThirdSide() {
        assertThatExceptionOfType(FalseInputDataException.class)
                .isThrownBy(() -> new Triangle(5, 5, 10));
    }

    @Test
    void checkTriangleAreaWhenCorrectSides() {
        Triangle triangle = new Triangle(3, 4, 5);
        assertEquals(6, triangle.getArea());
    }

    @Test
    void checkTriangleAreaWhenDifferentInputOrderOfSide() {
        Triangle triangle1 = new Triangle(3, 4, 5);
        Triangle triangle2 = new Triangle(4, 5, 3);
        assertEquals(triangle1.getArea(), triangle2.getArea());
    }

    @Test
    void checkTwoTriangleAreasWhenSidesAreEqual() {
        Triangle triangle1 = new Triangle(3, 4, 5);
        Triangle triangle2 = new Triangle(3, 4, 5);
        assertEquals(triangle1.getArea(), triangle2.getArea());
    }

    @Test
    void checkTwoTriangleAreasWhenSumSidesOfFirstIsLessThanSecond() {
        Triangle triangle1 = new Triangle(3, 4, 5);
        Triangle triangle2 = new Triangle(2, 4, 5);
        assertTrue(triangle2.getArea() < triangle1.getArea());
    }

}
