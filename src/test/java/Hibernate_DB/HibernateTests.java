package Hibernate_DB;

import Hibernate_DB.Models.*;
import JDBC_DB.BasicSetUp.DB_Connection;
import JDBC_DB.BasicSetUp.DB_SetUp;
import org.hibernate.exception.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class HibernateTests {

    CRUDUtils utils = new CRUDUtils();

    @BeforeAll
    static void startWork() {
        DB_SetUp.createData();
    }

    @AfterAll
    static void tearDown() {
        DB_Connection.closeConnection();
    }

    @DisplayName("Check that all tables has specified number of rows")
    @ParameterizedTest(name = "Table {0} should have {1} rows")
    @Tag("smoke")
    @MethodSource("entityRowCountsProvider")
    void checkRowCountInTable(Class<?> entityClass, int expectedRowCount) {
        int actualRowCount = utils.getEntityCount(entityClass);
        Assertions.assertEquals(expectedRowCount, actualRowCount, "Values must be equal");
    }

    static Stream<Arguments> entityRowCountsProvider() {
        return Stream.of(
                Arguments.of(Places.class, 5),
                Arguments.of(Animal.class, 10),
                Arguments.of(Positions.class, 4),
                Arguments.of(Sex.class, 2),
                Arguments.of(Types.class, 5),
                Arguments.of(Worker.class, 6),
                Arguments.of(Zoo.class, 3)
        );
    }

    @DisplayName("check that number of rows has increased in Places table after adding a new row")
    @Tags ({@Tag("smoke"), @Tag("Places table")})
    @Test
    void insertPlacesRowCount(){
        int expectedRowNumber = 6;
        Places newPlaces = Places.builder()
                .id(6)
                .row(7)
                .place_num(789)
                .name("Cage 6")
                .build();

        utils.insertPlacesData(newPlaces);
        int actualRowNumber = utils.getEntityCount(Places.class);

        Assertions.assertEquals(expectedRowNumber, actualRowNumber, "Number of rows must have increased");
    }

    @DisplayName("check that after adding a new row in Animal table you can find it")
    @Tags ({@Tag("extended test"), @Tag("Animal table")})
    @Test
    void insertAnimalRowSelectAndCheckValueAdded() {
        Animal newAnimal = Animal.builder()
                .name("Tim")
                .age(21)
                .type(1)
                .sex(1)
                .place(1)
                .build();

        utils.insertAnimalData(newAnimal);

        List<Animal> allAnimals = utils.getAllAnimals();

        Assertions.assertTrue(allAnimals.contains(newAnimal), "Row with new values must have been added");
    }

    @DisplayName("compare values of rows from specified collection with actual after select in Positions table")
    @Tags ({@Tag("extended test"), @Tag("Positions table")})
    @Test
    void getAndCompareRows() {
        List<Positions> expectedPositions = List.of(
                Positions.builder()
                        .id(1)
                        .name("lead janitor")
                        .salary(25000)
                        .build(),
                Positions.builder()
                        .id(2)
                        .name("Janitor")
                        .salary(20000)
                        .build(),
                Positions.builder()
                        .id(3)
                        .name("junior janitor")
                        .salary(15000)
                        .build(),
                Positions.builder()
                        .id(4)
                        .name("keeper")
                        .salary(45000)
                        .build()
        );

        List<Positions> actualPositions = utils.getAllPositions();

        Assertions.assertEquals(expectedPositions, actualPositions, "Values must be equal");
    }

    @DisplayName("update row and check values in Worker table")
    @Tags ({@Tag("smoke"), @Tag("Worker table")})
    @Test
    void updateRowAndCheck(){
        int numberOfAndrewInWorker = 2;
        Worker workerToUpdate = Worker.builder()
                .id(3)
                .name("Andrew")
                .age(25)
                .position(1)
                .build();

        utils.updateWorker(workerToUpdate);

        int actualAge = utils.getAllWorkers().get(numberOfAndrewInWorker).getAge();
        int actualPosition = utils.getAllWorkers().get(numberOfAndrewInWorker).getPosition();

        Assertions.assertEquals(workerToUpdate.getAge(), actualAge, "Values must be equal");
        Assertions.assertEquals(workerToUpdate.getPosition(), actualPosition, "Values must be equal");
    }

    @DisplayName("create a new row, update row, and check that number of rows the same and values has changed in Zoo table")
    @Tags ({@Tag("extended test"), @Tag("Zoo table")})
    @Test
    void createUpdateRowAndCheck() {
        int expectedRowNumber = 4;
        Zoo EasternZoo = Zoo.builder()
                .id(4)
                .name("Eastern")
                .build();

        Zoo ZooToUpdate = Zoo.builder()
                .id(4)
                .name("South-Eastern")
                .build();

        utils.insertZooData(EasternZoo);

        Assertions.assertTrue(utils.getAllZoos().contains(EasternZoo), "New row to the zoo table must have been added");

        utils.updateZoo(ZooToUpdate);

        Assertions.assertEquals(expectedRowNumber , utils.getEntityCount(Zoo.class),
                "Number for rows must have increased");
        Assertions.assertTrue(utils.getAllZoos().contains(ZooToUpdate),
                "New row must have been updated with new values");
    }

    @DisplayName("create a new row, delete it and check that number of rows has changed in Types table")
    @Tags ({@Tag("smoke"), @Tag("Types table")})
    @Test
    void createDeleteAndCheckRows() {
        int expectedRowNumber = 5;
        Types newTypes = Types.builder()
                .id(6)
                .name("Arthropod")
                .build();

        int afterAddingRowNumber = utils.getEntityCount(Types.class) + 1;

        utils.insertTypeData(newTypes);

        Assertions.assertEquals(afterAddingRowNumber, utils.getEntityCount(Types.class),
                "New type must have been added");

        utils.deleteTypeByID(newTypes.getId());

        Assertions.assertEquals(expectedRowNumber, utils.getEntityCount(Types.class),
                "New type must have been deleted");
    }

    @DisplayName("create a new row, delete it and check row number and that there is no deleted value in Sex table")
    @Tags ({@Tag("extended test"), @Tag("Sex table")})
    @Test
    void createDeleteAndCheckMissingValue() {
        int newRowNumber = 3;
        int expectedRowNumber = 2;
        Sex newSex = Sex.builder()
                .id(3)
                .name("Unknown")
                .build();

        utils.insertSexData(newSex);
        int actualRowNumber = utils.getEntityCount(Sex.class);

        Assertions.assertEquals(newRowNumber, actualRowNumber, "New sex must have been added");

        utils.deleteSexByID(newSex.getId());

        Assertions.assertEquals(expectedRowNumber, utils.getEntityCount(Sex.class), "Row must have been deleted");
        Assertions.assertFalse(utils.getAllSex().contains(newSex), "There should not be deleted row");
    }

    @DisplayName("try to create new rows with the same id in Animal table")
    @Tags ({@Tag("extended test"), @Tag("Animal table"), @Tag("negative")})
    @ParameterizedTest
    @MethodSource("animalProvider")
    void createRowsWithTheSameID(Animal animal)  {
        int numberOfRowsBeforeInsert = utils.getEntityCount(Animal.class);

        Throwable exception = Assertions.assertThrows(Exception.class, ()-> CRUDUtils.insertAnimalData(animal),
                "There must be no ability to create a new animal with already occupied id");
        assertThat(exception.getMessage()).contains("PRIMARY KEY ON PUBLIC.ANIMAL(ID)");

        int numberOfRowsAfterInsert = utils.getEntityCount(Animal.class);
        Assertions.assertEquals(numberOfRowsBeforeInsert, numberOfRowsAfterInsert, "Number of rows shouldn't have changed");
    }

    static Stream<Animal> animalProvider() {
        List<Animal> animals = new ArrayList<>();
        for (int id = 1; id <= 10; id++) {
            Animal animal = new Animal();
            animal.setId(id);
            animal.setName("Joker");
            animal.setAge(12);
            animal.setType(2);
            animal.setSex(1);
            animal.setPlace(2);
            animals.add(animal);
        }
        return animals.stream();
    }

    @DisplayName("try to create new rows with null value in Worker table")
    @Tags ({@Tag("extended test"), @Tag("Worker table"), @Tag("negative")})
    @Test
    void createRowWithNoValue(){
        Worker newWorker = Worker.builder()
                .id(7)
                .age(41)
                .position(2)
                .build();

        Assertions.assertThrows(Exception.class, ()-> utils.insertWorkerData(newWorker),
                "Field \"name\" must not be null");
    }

    @DisplayName("try to find a row that wasn't added to Positions table")
    @Tags ({@Tag("extended test"), @Tag("Positions table"), @Tag("negative")})
    @Test
    void findNonExistingValue(){
        Positions newPositions = Positions.builder()
                .id(5)
                .name("chairman")
                .salary(100000)
                .build();

        List <Positions> actualPositions = utils.getAllPositions();

        Assertions.assertFalse(actualPositions.contains(newPositions), "There must not be new row");
    }

    @DisplayName("try to update row with null value in Worker table")
    @Tags ({@Tag("extended test"), @Tag("Worker table"), @Tag("negative")})
    @Test
    void updateWithNullValue() {
        int expectedNumberOfRows = 6;
        Worker workerToUpdate = Worker.builder()
                .id(2)
                .age(35)
                .position(1)
                .build();

        Assertions.assertThrows(Exception.class, ()-> utils.updateWorker(workerToUpdate),
                "Update shouldn't have been done because \"name\" must not be null");

        int actualNumberOfRowsAfter = utils.getEntityCount(Worker.class);

        Assertions.assertEquals(expectedNumberOfRows, actualNumberOfRowsAfter,
                "After updating no new rows must have appeared");
    }

    @DisplayName("try to update non existing row in Places table")
    @Tags ({@Tag("extended test"), @Tag("Places table"), @Tag("negative")})
    @Test
    void updateNonExistingRow(){
        int expectedNumbersOfRowsUpdated = 0;
        Places placeToUpdate = Places.builder()
                .id(6)
                .row(4)
                .place_num(303)
                .name("Cage 6")
                .build();

        int actualNumbersOfRowsUpdated = utils.updatePlacesAndShowNumberOfAffectedRows(placeToUpdate);

        Assertions.assertEquals(expectedNumbersOfRowsUpdated, actualNumbersOfRowsUpdated, "No rows have appeared");
    }

    @DisplayName("try to delete non existing row in Zoo table")
    @Tags ({@Tag("extended test"), @Tag("Zoo table"), @Tag("negative")})
    @Test
    void deleteNonExistingRow() {
        int expectedNumberOfDeletedRows = 0;
        int initNumberOfRows = utils.getEntityCount(Zoo.class);
        Zoo NewZoo = Zoo.builder()
                .id(10)
                .name("Southern")
                .build();

        int actualNumberOfDeletedRows = utils.deleteZooByIdAndShowNumberOfDeletedRows(NewZoo.getId());

        Assertions.assertEquals(expectedNumberOfDeletedRows, actualNumberOfDeletedRows, "No rows have been deleted");
        Assertions.assertEquals(initNumberOfRows, utils.getEntityCount(Zoo.class), "No rows have been deleted");
    }

    @DisplayName("delete existing row and check exception in Types table")
    @Tags ({@Tag("extended test"), @Tag("Types table"), @Tag("negative")})
    @Test
    void deleteAndCheckRows() {
        int dogID = 2;

        Assertions.assertThrows(
                ConstraintViolationException.class,
                () -> utils.deleteTypeByID(dogID), "Referential integrity constraint violation");
    }
}
