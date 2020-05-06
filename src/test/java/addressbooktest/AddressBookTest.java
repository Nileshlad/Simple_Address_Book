package addressbooktest;

import controller.AddressBookController;
import model.Person;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddressBookTest {


    AddressBookController addressBookController;
    ObjectMapper objectMapper;
    Person person;
    private static final String filePath = "C:\\Users\\Blackhawkkk1\\IdeaProjects\\Simple_AddressBook_Statement\\src\\main\\resources\\AddressBookData.json";

    @Before
    public void SetUp() {
        addressBookController = new AddressBookController();
        objectMapper = new ObjectMapper();
        person = new Person();
    }

    //TEST CASE 1.1
    @Test
    public void givenPersonalInformation_whenStoreInFile_shouldReturnTrue() {
        try {
            Person person = new Person
                    ("Nilesh", "lad", "kranti nagar", "sangli", "Maharashtra", "416309", "8888310299");
            addressBookController.addPersonInformation(person, filePath);
            ArrayList<Person> data = objectMapper
                    .readValue(new File(filePath), new TypeReference<ArrayList<Person>>() {
                    });
            Assert.assertEquals(person.getPhoneNumber(), data.get(data.size() - 1).getPhoneNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TEST CASE 1.2
    @Test
    public void givenPersonalInformation_whenUpdateData_shouldReturnTrue() {
        try {
            String uniqueData = "8888310299";
            Person personInformation = new Person
                    ("pawan", "lad", "krantinagar", "sangli", "maharashtra", "416309", "8888310299");
            int indexNumber = addressBookController.updatePersonData(personInformation, filePath, uniqueData);
            ArrayList<Person> data = objectMapper
                    .readValue(new File(filePath), new TypeReference<ArrayList<Person>>() {
                    });
            Assert.assertEquals(personInformation.getPhoneNumber(), data.get(indexNumber).getPhoneNumber());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TEST CASE 1.3
    @Test
    public void givenPersonInformation_whenDeleteData_shouldRetunTrue() throws Exception {
        try {
            String uniqueData = "8888310299";
            ArrayList<Person> beforeDeletedData = objectMapper
                    .readValue(new File(filePath), new TypeReference<ArrayList<Person>>() {
                    });
            int beforeDeletedSize = beforeDeletedData.size();
            addressBookController.deletePersonData(filePath, uniqueData);
            ArrayList<Person> afterDeletedData = objectMapper
                    .readValue(new File(filePath), new TypeReference<ArrayList<Person>>() {
                    });
            int afterDeletedSize = afterDeletedData.size() + 1;
            Assert.assertEquals(beforeDeletedSize, afterDeletedSize);
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }
}