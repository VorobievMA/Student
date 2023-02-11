package edu.java.satudentorder.dao;


import org.junit.*;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.Statement;
import java.util.List;
import java.util.stream.Collectors;

public class DictionaryDaoImplTest {
    @BeforeClass
    public static void startUp() throws Exception {
        URL url = DictionaryDaoImplTest.class.getClassLoader().getResource("student_project.sql");
        List<String> str = Files.readAllLines(Paths.get(url.toURI()));
       String sql= str.stream().collect(Collectors.joining());
       try (Connection con = ConnectionBuilder.getConnection(); Statement stmt = con.createStatement();){
           stmt.executeUpdate(sql);
       }
    }

    @Test
    public void testExample1() {
        System.out.println("TEST1");
    }
    @Test

    public void testExample2() {
        System.out.println("TEST2");
    }
    @Test
    public void testExample3() {
        System.out.println("TEST3");
    }
}