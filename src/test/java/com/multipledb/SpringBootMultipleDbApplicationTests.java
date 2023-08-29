package com.multipledb;

import com.multipledb.services.DbService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class SpringBootMultipleDbApplicationTests {

    @Autowired
    private DbService dbService;

    @Test
    void contextLoads() {
    }

    //testing database service method to save data into database
    @Test
    void saveData() {
        this.dbService.saveUserAndProduct();
    }

    //testing database service to get all data from database
    @Test
    void getData() {
        this.dbService.getAllUserAndProduct();
    }
}
