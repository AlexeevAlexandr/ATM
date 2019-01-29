package com.demo.application;

import com.demo.application.controller.MainRESTController;
import com.demo.application.dao.EmployeeDAO;
import com.demo.application.model.Employee;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.hamcrest.CoreMatchers.containsString;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class Tests {

    private EmployeeDAO employeeDAO = new EmployeeDAO();
    private MainRESTController mainRESTController = new MainRESTController(employeeDAO);


    @Autowired
    private MockMvc mockMvc;

    @Test
    public void main() {
        SBApplication.main(new String[] {});
    }

    @Test
    public void testEmployee(){
        Employee employee = new Employee();
        String[] data = {"A01", "Jessica", "developer"};

        employee.setEmpNo(data[0]);
        employee.setEmpName(data[1]);
        employee.setPosition(data[2]);

        String[] data2 = {employee.getEmpNo(), employee.getEmpName(), employee.getPosition()};
        assertEquals(data,data2);
    }

    @Test
    public void test_welcome() throws Exception {
        this
            .mockMvc.perform(get("/")).andDo(print()).andExpect(status().isOk())
            .andExpect(content().string(containsString("Welcome page")));
    }

    @Test
    public void test_getEmployees(){
        List list = mainRESTController.getEmployees();
        assertEquals(employeeDAO.getAllEmployees(),list);

    }

    @Test
    public void test_deleteEmployee(){
        mainRESTController.deleteEmployee("E01");
        List list = mainRESTController.getEmployees();
        assertEquals(employeeDAO.getAllEmployees(),list);
    }

    @Test
    public void test_updateEmployee(){
        Employee emp = new Employee("E01", "Smith", "Developer");
        mainRESTController.updateEmployee(emp);
        Employee emp1 = mainRESTController.getEmployee("E01");
        assertEquals(emp,emp1);
    }

    @Test
    public void test_addEmployee(){
        Employee emp = new Employee("E11", "Jon", "Developer");
        mainRESTController.addEmployee(emp);
        assertEquals(emp,mainRESTController.getEmployee("E11"));
    }
}
