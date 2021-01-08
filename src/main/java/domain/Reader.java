package domain;

public class Reader {
    private int id;
    private String name;
    private String password;
    private boolean isLibrarian;

    public Reader(){

    }

    public Reader(int id, String name, String password, boolean isLibrarian){
        setId(id);
        setName(name);
        setPassword(password);
        setLibrarian(isLibrarian);
    }

    public Reader(int id, String name, String password){
        setId(id);
        setName(name);
        setPassword(password);
        setLibrarian(false);
    }

    public Reader(String name, String password){
        setName(name);
        setPassword(password);
        setLibrarian(false);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getIsLibrarian() {
        return isLibrarian;
    }

    public void setLibrarian(boolean librarian) {
        isLibrarian = librarian;
    }
}
