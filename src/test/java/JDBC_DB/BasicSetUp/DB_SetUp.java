package JDBC_DB.BasicSetUp;

import java.sql.Connection;
import java.sql.SQLException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DB_SetUp {

    private static final Logger logger = LoggerFactory.getLogger(DB_SetUp.class);

    private static void executeUpdate(String sql) throws SQLException {
        Connection connection = DB_Connection.createConnection();
        connection
                .createStatement()
                .executeUpdate(sql);
    }

    public static void createData(){
        try{
            executeUpdate("""
                    CREATE TABLE public.places (
                    \tid int4 NOT NULL,
                    \t"row" int2 NULL,
                    \tplace_num int4 NULL,
                    \t"name" varchar NULL,
                    \tCONSTRAINT places_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE public.sex(
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tCONSTRAINT sex_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE public.types(
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tCONSTRAINT types_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE public.animal(
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tage int4  NULL,
                    \ttype int4 NULL,
                    \tsex int4 NULL,
                    \tplace int4 NULL,
                    \tCONSTRAINT animal_pk PRIMARY KEY (id),
                    \tCONSTRAINT animal_fk FOREIGN KEY (type) REFERENCES public.types(id),
                    \tCONSTRAINT animal_fk1 FOREIGN KEY (sex) REFERENCES public.sex (id),
                    \tCONSTRAINT animal_fk2 FOREIGN KEY (place) REFERENCES public.places(id) DEFERRABLE
                    );""");
            executeUpdate("""
                    CREATE TABLE public.positions(
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tsalary int4 NULL,
                    \tCONSTRAINT positions_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE public.worker(
                    \tid int4 NOT NULL,
                    \t"name" varchar NOT NULL,
                    \tage int4 NULL,
                    \t"position" int4 NULL,
                    \tCONSTRAINT worker_pk PRIMARY KEY (id),
                    \tCONSTRAINT worker_fk FOREIGN KEY ("position") REFERENCES public.positions(id)
                    );""");
            executeUpdate("""
                    CREATE TABLE public.zoo (
                    \tid int4 NOT NULL,
                    \t"name" varchar NULL,
                    \tCONSTRAINT zoo_pk PRIMARY KEY (id)
                    );""");
            executeUpdate("""
                    CREATE TABLE public.zoo_animal (
                    \tzoo_id int4 NOT NULL,
                    \tanimal_id int4 NOT NULL,
                    \ttime_appearance timestamp(0) NULL,
                    \tworker int4 NULL,
                    \tCONSTRAINT zoo_animal_fk FOREIGN KEY (zoo_id) REFERENCES public.zoo(id),
                    \tCONSTRAINT zoo_animal_fk_1 FOREIGN KEY (animal_id) REFERENCES public.animal(id),
                    \tCONSTRAINT zoo_animal_fk_2 FOREIGN KEY (worker) REFERENCES public.worker(id)
                    );""");
            executeUpdate("""
                    INSERT INTO public.places (id, "row", place_num, "name") VALUES(1, 1, 185, 'Cage 1');
                    INSERT INTO public.places (id, "row", place_num, "name") VALUES(2, 2, 245, 'Cage 2');
                    INSERT INTO public.places (id, "row", place_num, "name") VALUES(3, 3, 123, 'Cage 3');
                    INSERT INTO public.places (id, "row", place_num, "name") VALUES(4, 5, 054, 'Cage 4');
                    INSERT INTO public.places (id, "row", place_num, "name") VALUES(5, 6, 043, 'Cage 5');""");
            executeUpdate("INSERT INTO public.sex (id, \"name\") VALUES(1, 'Male');\n"
                    + "INSERT INTO public.sex (id, \"name\") VALUES(2, 'Female');");
            executeUpdate("""
                    INSERT INTO public.types (id, "name") VALUES(1, 'Cat');
                    INSERT INTO public.types (id, "name") VALUES(2, 'Dog');
                    INSERT INTO public.types (id, "name") VALUES(3, 'Primate');
                    INSERT INTO public.types (id, "name") VALUES(4, 'Bird');
                    INSERT INTO public.types (id, "name") VALUES(5, 'Fish');""");
            executeUpdate("""
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(1, 'Crypto', 2, 1, 1, 1);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(2, 'Bee', 4, 2, 1, 1);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(3, 'Stout', 5, 2, 1, 2);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(4, 'Po', 6, 3, 2, 2);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(5, 'Fluffy', 7, 4, 2,3);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(6, 'Lord', 3, 5, 2, 4);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(7, 'Perk', 5, 3, 1, 5);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(8, 'Rogue', 7, 2, 1, 3);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(9, 'Bagle', 4, 1, 1, 2);
                    INSERT INTO public.animal (id, "name", age, type, sex, place) VALUES(10, 'Smash', 5, 2, 1, 4);""");
            executeUpdate("""
                    INSERT INTO public.positions (id, "name", salary) VALUES(1, 'lead janitor', 25000);
                    INSERT INTO public.positions (id, "name", salary) VALUES(2, 'Janitor', 20000);
                    INSERT INTO public.positions (id, "name", salary) VALUES(3, 'junior janitor', 15000);
                    INSERT INTO public.positions (id, "name", salary) VALUES(4, 'keeper', 45000);""");
            executeUpdate("""
                    INSERT INTO public.worker (id, "name", age, "position") VALUES(1, 'James', 23, 1);
                    INSERT INTO public.worker (id, "name", age, "position") VALUES(2, 'John', 34, 2);
                    INSERT INTO public.worker (id, "name", age, "position") VALUES(3, 'Andrew', 24, 3);
                    INSERT INTO public.worker (id, "name", age, "position") VALUES(4, 'Alex',22, 4);
                    INSERT INTO public.worker (id, "name", age, "position") VALUES(5, 'Michael', 32, 3);
                    INSERT INTO public.worker (id, "name", age, "position") VALUES(6, 'Anthony', 54, 2);""");
            executeUpdate("""
                    INSERT INTO public.zoo (id, "name") VALUES(1, 'Central');
                    INSERT INTO public.zoo (id, "name") VALUES(2, 'Northern');
                    INSERT INTO public.zoo (id, "name") VALUES(3, 'Western');""");
            executeUpdate("""
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(1, 1, null, 1);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(2, 2, null, 2);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(3, 3, null, 3);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(3, 4, null, 4);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(2, 5, null, 5);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(2, 6, null, 6);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(3, 7, null, 1);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(1, 8, null, 2);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(1, 9, null, 3);
                    INSERT INTO public.zoo_animal (zoo_id, animal_id, time_appearance, worker) VALUES(2, 10, null, 4);""");
        } catch (SQLException e) {
           logger.error("Creating tables and putting data error", e);
        }
    }
}
