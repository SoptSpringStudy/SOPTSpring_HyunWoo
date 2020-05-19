public class MessageAnimal {
    private String name;
    private String bow;

    public void bowwow() {
        System.out.println(name + "는 "+bow+"~!");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBow() {
        return bow;
    }

    public void setBow(String bow) {
        this.bow = bow;
    }

    public MessageAnimal(String name, String bow) {
        super();
        this.name = name;
        this.bow = bow;
    }
    public MessageAnimal() {

    }
}
