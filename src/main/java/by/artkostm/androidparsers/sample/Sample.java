package by.artkostm.androidparsers.sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.sound.midi.MidiDevice.Info;

import by.artkostm.androidparsers.core.PParserContext;
import by.artkostm.androidparsers.core.ParserContextFactory;
import by.artkostm.androidparsers.core.ParserContextFactory.Type;
import by.artkostm.androidparsers.core.annotations.Property;
import by.artkostm.androidparsers.core.annotations.XMLAttribute;
import by.artkostm.androidparsers.core.annotations.XMLElement;
import by.artkostm.androidparsers.core.annotations.XMLElements;
import by.artkostm.androidparsers.core.context.ParserContext;

public class Sample{
    public static void main(String[] args){
//	    People people = new People();
//	    PrivateInfo info = new PrivateInfo();
//	    Tt t = new Tt();
//	    info.setTt(t);
//	    info.setID("2176527121"); info.setPASSPORT("BA212313");
//	    Man sasha = new Man(); sasha.setName("Sasha"); sasha.setAge(18); sasha.setEyeColor("green"); sasha.setHeight(182.0); sasha.setWeight(75.0); sasha.getpInfo().add(info);
//	    
//	    Man masha = new Man(); masha.setName("Masha"); masha.setAge(21); masha.setEyeColor("green"); masha.setHeight(182.0); masha.setWeight(75.0); masha.getpInfo().add(info);
//        
//        Man art = new Man(); art.setName("Artsiom"); art.setAge(20); art.setEyeColor("green"); art.setHeight(182.0); art.setWeight(75.0); art.getpInfo().add(info);
//        
//        people.getPeople().add(sasha); people.getPeople().add(masha); people.getPeople().add(art);
//        
//        long start = System.currentTimeMillis();
//        PParserContext context = PParserContext.getInstance("by.artkostm.androidparsers");   
////        ParserContext.Marshaller<People> marshaller = context.getMarshaller(People.class);
////        marshaller.marshal(people, new File("people.xml"));
//        PParserContext.Unmarshaller<People> unmarshaller = context.getUnmarshaller();
//        people = unmarshaller.unmarshal(new File("people.xml"));
//        System.out.println("Time is "+(System.currentTimeMillis() - start)+"ms");
//        System.out.println(people.getPeople().size());
        
        
        ParserContext context = ParserContextFactory.getInstance().getParserContext(Type.PROPS, "by.artkostm.androidparsers");
        ParserContext.Unmarshaller<TestProps> unmarshaller = context.getUnmarshaller(TestProps.class);
        TestProps tp = unmarshaller.unmarshal(new File("test.properties"));
        System.out.println(tp);
//        ParserContext.Marshaller<People> marshaller = context.getMarshaller(People.class);
//        marshaller.marshal(people, new File("people11.xml"));
//        ParserContext.Unmarshaller<People> unmarshaller = context.getUnmarshaller();
//        long start = System.currentTimeMillis();
//        people = unmarshaller.unmarshal(new File("people.xml"));
//        System.out.println("Time is "+(System.currentTimeMillis() - start)+"ms");
//        System.out.println(people.getPeople().size());
        
        
	}
    
    public static class TestProps{
        @Property(name = "fieldOne")
        private String fieldOne;
        @Property(name = "fieldTwo")
        private double fieldTwo;
        @Property(name = "fieldThree")
        private boolean fieldThree;
        public String getFieldOne() {
            return fieldOne;
        }
        public void setFieldOne(String fieldOne) {
            this.fieldOne = fieldOne;
        }
        public double getFieldTwo() {
            return fieldTwo;
        }
        public void setFieldTwo(double fieldTwo) {
            this.fieldTwo = fieldTwo;
        }
        public boolean isFieldThree() {
            return fieldThree;
        }
        public void setFieldThree(boolean fieldThree) {
            this.fieldThree = fieldThree;
        }
        @Override
        public String toString() {
            return "TestProps [fieldOne=" + fieldOne + ", fieldTwo=" + fieldTwo
                    + ", fieldThree=" + fieldThree + "]";
        }
    }
    
    @XMLElement(name = "People")
    public static class People{
        
        @XMLElements(baseType = Man.class)
        private List<Man> people;
        
        public People() {
            people = new ArrayList<>();
        }
        public List<Man> getPeople() {
            return people;
        }
    }
    
    @XMLElement(name = "Man")
    public static class Man{
        
        public Man()
        {
            pInfo = new ArrayList<>();
        }
        
        @XMLAttribute(name = "name")
        private String name;
        @XMLAttribute(name = "age")
        private Integer age;
        
        @XMLAttribute(name = "eyeColor")
        private String eyeColor;
        @XMLAttribute(name = "height")
        private Double height;
        @XMLAttribute(name = "weight")
        private Double weight;
        @XMLElements(baseType = PrivateInfo.class)
        private List<PrivateInfo> pInfo;
        
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
        public String getEyeColor()
        {
            return eyeColor;
        }
        public void setEyeColor(String eyeColor)
        {
            this.eyeColor = eyeColor;
        }
        public Double getHeight()
        {
            return height;
        }
        public void setHeight(Double height)
        {
            this.height = height;
        }
        public Double getWeight()
        {
            return weight;
        }
        public void setWeight(Double weight)
        {
            this.weight = weight;
        }
        public List<PrivateInfo> getpInfo()
        {
            return pInfo;
        }
        public void setpInfo(List<PrivateInfo> pInfo)
        {
            this.pInfo = pInfo;
        }
        
    }
    
    @XMLElement(name = "PrivateInfo")
    public static class PrivateInfo{
        @XMLAttribute(name = "id")
        private String ID;
        @XMLAttribute(name = "passport")
        private String PASSPORT;
        @XMLElement(name = "test", type = Tt.class)
        private Tt tt;
        
        public void setTt(Tt t){
            this.tt = t;
        }
        public String getID()
        {
            return ID;
        }
        public void setID(String iD)
        {
            ID = iD;
        }
        public String getPASSPORT()
        {
            return PASSPORT;
        }
        public void setPASSPORT(String pASSPORT)
        {
            PASSPORT = pASSPORT;
        }        
    }
    
    @XMLElement(name = "TEST")
    public static class Tt{
        
    }
    
    private static void print(People people){
        System.out.println(String.format("People(%d): ", people.getPeople().size()));
        for(Man man : people.getPeople()){
            System.out.println(String.format("\t Name: %s, Age: %d\n", man.getName(), man.getAge()));
        }
    }
}