package controller;

import model.Person;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;
import service.IAddressBook;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;

public class AddressBookController implements IAddressBook {
    ObjectMapper objectMapper = new ObjectMapper();

    public ArrayList<Person> readFileData(String filePath) throws IOException {
        return objectMapper.readValue(new File(filePath), new TypeReference<ArrayList<Person>>() {
        });
    }

    public void writeFileData(ArrayList<Person> list, String filePath) throws IOException {
        objectMapper.writeValue(new File(filePath), list);
    }

    //add person information
    @Override
    public void addPersonInformation(Person person, String filePath) {
        try {
            //Read data
            ArrayList<Person> data = readFileData(filePath);
            data.add(person);
            //Write data
            writeFileData(data, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //update person data
    @Override
    public int updatePersonData(Person person, String filePath, String uniqueData) {
        int indexNumber = 0;
        try {
            ArrayList<Person> data = readFileData(filePath);
            for (Person personData : data) {
                if (personData.getPhoneNumber()==(uniqueData)) {
                    indexNumber = data.indexOf(personData);
                    personData.setAddress(person.getAddress());
                    personData.setCity(person.getCity());
                    personData.setState(person.getState());
                    personData.setZip(person.getZip());
                    personData.setPhoneNumber(person.getPhoneNumber());
                }
            }
            writeFileData(data, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return indexNumber;
    }

    @Override
    public void deletePersonData(String filePath, String uniqueData) {
        try {
            ArrayList<Person> data = readFileData(filePath);
            Person removedata = null;
            for (Person personData : data) {
                if (personData.getPhoneNumber().equals(uniqueData)) {
                    removedata = personData;
                    break;
                }
            }
            data.remove(removedata);
            writeFileData(data, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @Override
    public void sortPersonDataByLastName(String filePath) {
        try {
            ArrayList<Person> data = readFileData(filePath);
            data.sort(Comparator.comparing(Person::getLastName));
            writeFileData(data, filePath);
            ArrayList<Person> sortedDataByLastName = readFileData(filePath);
            if (sortedDataByLastName.get(0).getLastName().equals(sortedDataByLastName.get(1).getLastName()))
                sortedDataByLastName.sort(Comparator.comparing(Person::getFirstName));
            writeFileData(sortedDataByLastName, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sortPersonDataByZipCode(String filePath) {
        try {
            ArrayList<Person> data = readFileData(filePath);
            data.sort(Comparator.comparing(Person::getZip));
            writeFileData(data, filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean printPersonEntriesData(String filePath) {
        try {
            ArrayList<Person> data = readFileData(filePath);
            data.forEach(print -> System.out.println(print));
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean createNewAddressBook(String addressBookName) {
        try {
            String currentPath = "./src/main/resources/" + addressBookName + ".json";
            File newfile = new File(currentPath);
            if (newfile.createNewFile())
                return true;
            return false;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean openExistingAddressBook(String addressBookName) {
        try {
            String filePath = "./src/main/resources/" + addressBookName + ".json";
            File file = new File(filePath);
            if (file.exists()) {
                ArrayList<Person> person = readFileData(filePath);
                return true;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveAddressBook(String filePath, ArrayList<Person> data) {
        try {
            if (data.isEmpty())
                return false;
            writeFileData(data, filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean saveAsAddressBook(String filePath, Person personInformation) {
        try {
            ArrayList<Person> data = readFileData(filePath);
            if (data.isEmpty())
                return false;
            writeFileData(data, filePath);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public void quitAddressBook() {
        System.out.println("Exit..");
        System.exit(0);
    }
}
