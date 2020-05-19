import org.springframework.context.support.GenericXmlApplicationContext;

public class MainClass {
    public static void main(String[] args) {
        MessageMeow mm = new MessageMeow();
        mm.meow("고양이");
        GenericXmlApplicationContext ctx = new GenericXmlApplicationContext(
                "classpath:applicationContext.xml");
		Calculator calc = ctx.getBean("myCalc", Calculator.class);
		calc.getSum();
		calc.getMul();
		TransportationWalk tpWalk = ctx.getBean("tWalk", TransportationWalk.class);
		tpWalk.move();

        MessageAnimal ma = ctx.getBean("myAnimal", MessageAnimal.class);
        ma.bowwow();
        Introduce in = ctx.getBean("myIntro", Introduce.class );
        System.out.println(in.introMyself());
        ctx.close();

    }
}
