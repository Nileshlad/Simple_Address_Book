package controller;

import model.Person;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class AddressBookController {
    ObjectMapper objectMapper = new ObjectMapper();

    public ArrayList<Person> readFileData(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<ArrayList<Person>>() {
        });
    }

    public void writeFileData(ArrayList<Person> list, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), list);
    }

}