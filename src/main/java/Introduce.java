import java.util.*;

public class Introduce {
    private String name;
    private int age;
    private String soptDept;
    private List<String> friends;
    private Map<String, String> proLangs;
    public Introduce(String name, int age, String soptDept, ArrayList<String> friends, Map<String, String> proLangs) {
        super();
        this.name = name;
        this.age = age;
        this.soptDept = soptDept;
        this.friends = friends;
        this.proLangs = proLangs;
    }
    public Introduce() {

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getSoptDept() {
        return soptDept;
    }
    public void setSoptDept(String soptDept) {
        this.soptDept = soptDept;
    }
    public List<String> getFriends() {
        return friends;
    }
    public void setFriends(ArrayList<String> friends) {
        this.friends = friends;
    }
    public Map<String, String> getProLangs() {
        return proLangs;
    }
    public void setProLangs(Map<String, String> proLangs) {
        this.proLangs = proLangs;
    }
//    public void introMyself() {
//        System.out.println("이름: "+name);
//        System.out.println("나이: "+age);
//        System.out.println("SOPT 소속: "+soptDept);
//        System.out.println("친구: "+friends);
//        System.out.println("사용 프로그래밍 언어: "+proLangs);
//    }
    public String introMyself(){
        String contents = "이름: "+name +"\n나이: "+age+"\nSOPT 소속: "+soptDept+"\n친구: "+friends.toString()+"\n사용 프로그래밍 언어: "+proLangs.toString();
        return contents;
    }

}
