package by.artkostm.androidparsers.sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import by.artkostm.androidparsers.core.ParserContext;
import by.artkostm.androidparsers.core.annotations.DOMAttribute;
import by.artkostm.androidparsers.core.annotations.DOMElement;
import by.artkostm.androidparsers.core.annotations.DOMElements;

public class Sample{
    public static void main(String[] args){
	    People people = new People();
	    
	    Man sasha = new Man(); sasha.setName("Sasha"); sasha.setAge(18);
	    
	    Man masha = new Man(); masha.setName("Masha"); masha.setAge(21);
        
        Man art = new Man(); art.setName("Artsiom"); art.setAge(20);
        
        people.getPeople().add(sasha); people.getPeople().add(masha); people.getPeople().add(art);
        
        ParserContext context = ParserContext.getInstance("by.artkostm.androidparsers");   
        ParserContext.Marshaller<People> marshaller = context.getMarshaller(People.class);
        marshaller.marshal(people, new File("people.xml"));
        ParserContext.Unmarshaller<People> unmarshaller = context.getUnmarshaller();
        people = unmarshaller.unmarshal(new File("people.xml"));
        print(people);
	}
    
    @DOMElement(name = "People")
    public static class People{
        
        @DOMElements(baseType = Man.class)
        private List<Man> people;
        
        public People() {
            people = new ArrayList<>();
        }
        public List<Man> getPeople() {
            return people;
        }
    }
    
    @DOMElement(name = "Man")
    public static class Man{
        
        @DOMAttribute(name = "name")
        private String name;
        @DOMAttribute(name = "age")
        private Integer age;
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
        public Integer getAge() {
            return age;
        }
        public void setAge(Integer age) {
            this.age = age;
        }
    }
    
    private static void print(People people){
        System.out.println(String.format("People(%d): ", people.getPeople().size()));
        for(Man man : people.getPeople()){
            System.out.println(String.format("\t Name: %s, Age: %d\n", man.getName(), man.getAge()));
        }
    }
}