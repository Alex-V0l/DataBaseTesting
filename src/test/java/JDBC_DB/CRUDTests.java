package JDBC_DB;

import JDBC_DB.BasicSetUp.DB_Connection;
import JDBC_DB.BasicSetUp.DB_SetUp;
import JDBC_DB.Models.*;
import org.h2.jdbc.JdbcSQLIntegrityConstraintViolationException;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CRUDTests {

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
    @MethodSource("tableRowCountsProvider")
    void checkRowCountInTable(String tableName, int expectedRowCount) throws SQLException {
        int actualRowCount = CRUDUtils.getCountRowByTable(tableName);
        Assertions.assertEquals(expectedRowCount, actualRowCount, "Values must be equal");
    }

    static Stream<Arguments> tableRowCountsProvider() {
        return Stream.of(
                Arguments.of("places", 5),
                Arguments.of("animal", 10),
                Arguments.of("positions", 4),
                Arguments.of("sex", 2),
                Arguments.of("types", 5),
                Arguments.of("worker", 6),
                Arguments.of("zoo", 3)
        );
    }

    @DisplayName("check that number of rows has increased in Places table after adding a new row")
    @Tags ({@Tag("smoke"), @Tag("Places table")})
    @Test
    void insertPlacesRowCount() throws SQLException {
        int expectedRowNumber = 6;
        String tableName = "places";
        Places newPlaces = Places.builder()
                .id(6)
                .row(7)
                .place_num(789)
                .name("Cage 6")
                .build();

        CRUDUtils.insertPlaces(newPlaces);

        int actualRowNumber = CRUDUtils.getCountRowByTable(tableName);

        Assertions.assertEquals(expectedRowNumber, actualRowNumber, "Number of rows must have increased");
    }

    @DisplayName("check that after adding a new row in Animal table you can find it")
    @Tags ({@Tag("extended test"), @Tag("Animal table")})
    @Test
    void insertAnimalRowSelectAndCheckValueAdded() {
        Animal newAnimal = Animal.builder()
                .id(11)
                .name("Blake")
                .age(7)
                .type(2)
                .sex(1)
                .place(2)
                .build();

        CRUDUtils.insertAnimalData(newAnimal);

        List<Animal> actualAnimals = CRUDUtils.getAnimalData();

        Assertions.assertTrue(actualAnimals.contains(newAnimal), "Row with new values must have been added");
    }

    @DisplayName("compare values of rows from specified collection with actual after select in Position table")
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

        List<Positions> actualPositions = CRUDUtils.getPositionsData();

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

        CRUDUtils.updateWorker(workerToUpdate);

        int actualAge = CRUDUtils.getWorkerData().get(numberOfAndrewInWorker).getAge();
        int actualPosition = CRUDUtils.getWorkerData().get(numberOfAndrewInWorker).getPosition();

        Assertions.assertEquals(workerToUpdate.getAge(), actualAge, "Values must be equal");
        Assertions.assertEquals(workerToUpdate.getPosition(), actualPosition, "Values must be equal");
    }

    @DisplayName("create a new row, update row, and check that number of rows the same and values has changed in Zoo table")
    @Tags ({@Tag("extended test"), @Tag("Zoo table")})
    @Test
    void createUpdateRowAndCheck() throws SQLException {
        int expectedRowNumber = 4;
        Zoo EasternZoo = Zoo.builder()
                .id(4)
                .name("Eastern")
                .build();

        Zoo ZooToUpdate = Zoo.builder()
                .id(4)
                .name("South-Eastern")
                .build();

        CRUDUtils.insertZoo(EasternZoo);

        Assertions.assertTrue(CRUDUtils.getZooData().contains(EasternZoo), "New row to the Zoo table must have been added");

        CRUDUtils.updateZoo(ZooToUpdate);

        Assertions.assertEquals(expectedRowNumber, CRUDUtils.getCountRowByTable("zoo"),
                "Number for rows must have increased");
        Assertions.assertTrue(CRUDUtils.getZooData().contains(ZooToUpdate),
                "New row must have been updated with new values");
    }

    @DisplayName("create a new row, delete it and check that number of rows has changed in Types table")
    @Tags ({@Tag("smoke"), @Tag("Types table")})
    @Test
    void createDeleteAndCheckRows() throws SQLException {
        int expectedRowNumber = 5;
        String tableName = "types";
        Types newTypes = Types.builder()
                .id(6)
                .name("Arthropod")
                .build();

        int afterAddingRowNumber = CRUDUtils.getCountRowByTable(tableName) + 1;

        CRUDUtils.insertType(newTypes);

        Assertions.assertEquals(afterAddingRowNumber, CRUDUtils.getCountRowByTable(tableName),
                "New type must have been added");

        CRUDUtils.deleteTypeById(newTypes.getId());

        Assertions.assertEquals(expectedRowNumber, CRUDUtils.getCountRowByTable(tableName),
                "New type must have been deleted");
    }

    @DisplayName("create a new row, delete it and check row number and that there is no deleted value in Sex table")
    @Tags ({@Tag("extended test"), @Tag("Sex table")})
    @Test
    void createDeleteAndCheckMissingValue() throws SQLException {
        int newRowNumber = 3;
        int expectedRowNumber = 2;
        String tableName = "sex";
        Sex newSex = Sex.builder()
                .id(3)
                .name("Unknown")
                .build();

        CRUDUtils.insertSex(newSex);
        int actualRowNumber = CRUDUtils.getCountRowByTable(tableName);

        Assertions.assertEquals(newRowNumber, actualRowNumber, "New sex must have been added");

        CRUDUtils.deleteSexById(newSex.getId());

        Assertions.assertEquals(expectedRowNumber, CRUDUtils.getCountRowByTable(tableName), "Row must have been deleted");
        Assertions.assertFalse(CRUDUtils.getSexData().contains(newSex), "There should not be deleted row");
    }

    @DisplayName("try to create new rows with the same id in Animal table")
    @Tags ({@Tag("extended test"), @Tag("Animal table"), @Tag("negative")})
    @ParameterizedTest
    @MethodSource("animalProvider")
    void createRowsWithTheSameID(Animal animal) throws SQLException {
        String tableName = "animal";
        int numberOfRowsBeforeInsert = CRUDUtils.getCountRowByTable(tableName);

        Throwable exception = Assertions.assertThrows(Exception.class, ()-> CRUDUtils.insertAnimalData(animal),
        "There must be no ability to create a new animal with already occupied id");
        assertThat(exception.getMessage()).contains("PRIMARY KEY ON PUBLIC.ANIMAL(ID)");

        int numberOfRowsAfterInsert = CRUDUtils.getCountRowByTable(tableName);
        Assertions.assertEquals(numberOfRowsBeforeInsert, numberOfRowsAfterInsert,
                "Number of rows shouldn't have changed");
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

        Assertions.assertThrows(Exception.class, ()-> CRUDUtils.insertWorker(newWorker),
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

        List <Positions> actualPositions = CRUDUtils.getPositionsData();

        Assertions.assertFalse(actualPositions.contains(newPositions), "There must not be new row");
    }

    @DisplayName("try to update row with null value in Worker table")
    @Tags ({@Tag("extended test"), @Tag("Worker table"), @Tag("negative")})
    @Test
    void updateWithNullValue() throws SQLException {
        int expectedNumberOfRows = 6;
        String tableName = "worker";
        Worker workerToUpdate = Worker.builder()
                .id(2)
                .age(35)
                .position(1)
                .build();

        Assertions.assertThrows(Exception.class, ()-> CRUDUtils.updateWorker(workerToUpdate),
                "Update shouldn't have been done because \"name\" must not be null");

        int actualNumberOfRowsAfter = CRUDUtils.getCountRowByTable(tableName);

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

        int actualNumbersOfRowsUpdated = CRUDUtils.updatePlacesAndShowNumberOfAffectedRows(placeToUpdate);

        Assertions.assertEquals(expectedNumbersOfRowsUpdated, actualNumbersOfRowsUpdated,
                "No rows have appeared changed");
    }

    @DisplayName("try to delete non existing row in Zoo table")
    @Tags ({@Tag("extended test"), @Tag("Zoo table"), @Tag("negative")})
    @Test
    void deleteNonExistingRow() throws SQLException {
        int expectedNumberOfDeletedRows = 0;
        String tableName = "Zoo";
        int initNumberOfRows = CRUDUtils.getCountRowByTable(tableName);
        Zoo NewZoo = Zoo.builder()
                .id(10)
                .name("Southern")
                .build();

        int actualNumberOfDeletedRows =  CRUDUtils.deleteZooByIdAndShowNumberOfDeletedRows(NewZoo.getId());

        Assertions.assertEquals(expectedNumberOfDeletedRows, actualNumberOfDeletedRows, "No rows have been deleted");
        Assertions.assertEquals(initNumberOfRows, CRUDUtils.getCountRowByTable(tableName), "No rows have been deleted");
    }

    @DisplayName("delete existing row and check exception in Types table")
    @Tags ({@Tag("extended test"), @Tag("Types table"), @Tag("negative")})
    @Test
    void deleteAndCheckRows() {
        int dogID = 2;

        Assertions.assertThrows(
                JdbcSQLIntegrityConstraintViolationException.class,
                () -> CRUDUtils.deleteTypeById(dogID), "Referential integrity constraint violation");
    }
}
