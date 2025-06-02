package JDBC_DB;

import JDBC_DB.BasicSetUp.DB_Connection;
import JDBC_DB.Models.*;
import JDBC_DB.Models.Types;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class CRUDUtils {
        private static final String insertAnimal = "INSERT INTO public.animal (id, \"name\", age, type, sex, place)" +
                "VALUES(?, ?, ?, ?, ?, ?);\n";
        private static final String insertPlaces = "INSERT INTO public.places (id, \"row\", place_num, \"name\")" +
                "VALUES(?, ?, ?, ?);\n";
        private static final String insertSex = "INSERT INTO public.sex (id, \"name\") VALUES(?, ?);\n";
        private static final String insertType = "INSERT INTO public.types (id, \"name\") VALUES(?, ?);\n";
        private static final String insertWorker = "INSERT INTO public.worker(id, \"name\", age, \"position\")" +
            "VALUES(?, ?, ?, ?);\n";
    private static final String insertZoo = "INSERT INTO public.zoo (id, \"name\") VALUES(?, ?);\n";
    private static final String getAllAnimalsQuery = "SELECT * FROM animal";
    private static final String getAllPositionsQuery = "SELECT * FROM positions";
    private static final String getAllSexQuery = "SELECT * FROM sex";
    private static final String getAllWorkerQuery = "SELECT * FROM worker";
    private static final String getAllZooQuery = "SELECT * FROM zoo";
    private static final String updateWorker = "UPDATE public.worker SET \"name\" = ?, age = ?, \"position\" = ? WHERE id = ?;";
    private static final String updateZoo = "UPDATE public.zoo SET \"name\" = ? WHERE id = ?;";
    private static final String updatePlaces = "UPDATE public.places SET  \"row\" = ?, place_num = ?, \"name\" = ? WHERE id = ?;";
    private static final String deleteTypes = "DELETE FROM public.types WHERE id = ?;";
    private static final String deleteSex = "DELETE FROM public.sex WHERE id = ?;";
    private static final String deleteZoo = "DELETE FROM public.zoo WHERE id = ?;";

    @SneakyThrows
    public static void insertAnimalData(Animal animal) {
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertAnimal);
        preparedStatement.setInt(1, animal.getId());
        preparedStatement.setString(2, animal.getName());
        preparedStatement.setInt(3, animal.getAge());
        preparedStatement.setInt(4, animal.getType());
        preparedStatement.setInt(5, animal.getSex());
        preparedStatement.setInt(6, animal.getPlace());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void insertPlaces(Places places){
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertPlaces);
        preparedStatement.setInt(1, places.getId());
        preparedStatement.setInt(2, places.getRow());
        preparedStatement.setInt(3, places.getPlace_num());
        preparedStatement.setString(4, places.getName());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void insertSex(Sex sex){
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertSex);
        preparedStatement.setInt(1, sex.getId());
        preparedStatement.setString(2, sex.getName());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void insertType(Types types){
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertType);
        preparedStatement.setInt(1, types.getId());
        preparedStatement.setString(2, types.getName());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void insertWorker(Worker worker){
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertWorker);
        preparedStatement.setInt(1, worker.getId());
        preparedStatement.setString(2, worker.getName());
        preparedStatement.setInt(3, worker.getAge());
        preparedStatement.setInt(4, worker.getPosition());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void insertZoo(Zoo zoo){
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(insertZoo);
        preparedStatement.setInt(1, zoo.getId());
        preparedStatement.setString(2, zoo.getName());
        preparedStatement.executeUpdate();
    }

    public static List<Animal> getAnimalData() {
        List<Animal> animals = new ArrayList<>();
        try {
            Connection connection = DB_Connection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getAllAnimalsQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int type = resultSet.getInt("type");
                int sex = resultSet.getInt("sex");
                int place = resultSet.getInt("place");

                animals.add(new Animal(id, name, age, type, sex, place));
            }
        } catch (SQLException throwables) {
            log.error("Getting animals from select query error", throwables);
        }
        return animals;
    }

    public static List<Positions> getPositionsData() {
        List<Positions> positions = new ArrayList<>();
        try {
            Connection connection = DB_Connection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getAllPositionsQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int salary = resultSet.getInt("salary");

                positions.add(new Positions(id, name, salary));
            }
        } catch (SQLException throwables) {
            log.error("Getting positions from select query error", throwables);
        }
        return positions;
    }

    public static List<Sex> getSexData() {
        List<Sex> sex = new ArrayList<>();
        try {
            Connection connection = DB_Connection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getAllSexQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                sex.add(new Sex(id, name));
            }
        } catch (SQLException throwables) {
            log.error("Getting sex from select query error", throwables);
        }
        return sex;
    }

    public static List<Worker> getWorkerData() {
        List<Worker> workers = new ArrayList<>();
        try {
            Connection connection = DB_Connection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getAllWorkerQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age");
                int position = resultSet.getInt("position");

                workers.add(new Worker(id, name, age, position));
            }
        } catch (SQLException throwables) {
            log.error("Getting worker from select query error", throwables);
        }
        return workers;
    }

    public static List<Zoo> getZooData() {
        List<Zoo> zoos = new ArrayList<>();
        try {
            Connection connection = DB_Connection.createConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(getAllZooQuery);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String name = resultSet.getString("name");

                zoos.add(new Zoo(id, name));
            }
        } catch (SQLException throwables) {
            log.error("Getting zoo from select query error", throwables);
        }
        return zoos;
    }

    public static int getCountRowByTable(String tableName) throws SQLException {
        Connection connection = DB_Connection.createConnection();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT COUNT (*) FROM " + tableName);
        resultSet.next();
        int count = resultSet.getInt("count(*)");
        System.out.printf("В таблице public.%s ровно %s записей%n", tableName, count);
        return count;
    }

    @SneakyThrows
    public static void updateWorker(Worker worker){
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateWorker);
        preparedStatement.setString(1, worker.getName());
        preparedStatement.setInt(2, worker.getAge());
        preparedStatement.setInt(3, worker.getPosition());
        preparedStatement.setInt(4, worker.getId());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void updateZoo(Zoo zoo){
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updateZoo);
        preparedStatement.setString(1, zoo.getName());
        preparedStatement.setInt(2, zoo.getId());
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static int updatePlacesAndShowNumberOfAffectedRows(Places places){
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(updatePlaces);
        preparedStatement.setInt(1, places.getRow());
        preparedStatement.setInt(2, places.getPlace_num());
        preparedStatement.setString(3, places.getName());
        preparedStatement.setInt(4,places.getId());
        return preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void deleteTypeById(int id) {
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteTypes);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static void deleteSexById(int id) {
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteSex);
        preparedStatement.setInt(1, id);
        preparedStatement.executeUpdate();
    }

    @SneakyThrows
    public static int deleteZooByIdAndShowNumberOfDeletedRows(int id) {
        Connection connection = DB_Connection.createConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(deleteZoo);
        preparedStatement.setInt(1, id);
        return preparedStatement.executeUpdate();
    }
}
