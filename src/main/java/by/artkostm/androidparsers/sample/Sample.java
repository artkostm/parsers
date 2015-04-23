package by.artkostm.androidparsers.sample;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import by.artkostm.androidparsers.core.ParserContextFactory;
import by.artkostm.androidparsers.core.ParserContextFactory.Type;
import by.artkostm.androidparsers.core.annotations.csv.MapToColumn;
import by.artkostm.androidparsers.core.annotations.props.Property;
import by.artkostm.androidparsers.core.annotations.xml.XMLAttribute;
import by.artkostm.androidparsers.core.annotations.xml.XMLElement;
import by.artkostm.androidparsers.core.annotations.xml.XMLElements;
import by.artkostm.androidparsers.core.context.ParserContext;

public class Sample{
    public static void main(String[] args){
        System.out.println("XML: ");
	    xml();
	    System.out.println("\nProperties: ");
	    properties();
	    System.out.println("\nCSV: ");
	    csv();
	}
    
    private static void xml(){
        People people = new People();
        PrivateInfo info = new PrivateInfo();
        info.setID("2176527121"); info.setPASSPORT("BA212313");
        Man sasha = new Man(); sasha.setName("Sasha"); sasha.setAge(18); sasha.setEyeColor("green"); sasha.setHeight(182.0); sasha.setWeight(75.0); sasha.getpInfo().add(info);
        Man masha = new Man(); masha.setName("Masha"); masha.setAge(21); masha.setEyeColor("green"); masha.setHeight(182.0); masha.setWeight(75.0); masha.getpInfo().add(info);
        Man art = new Man(); art.setName("Artsiom"); art.setAge(20); art.setEyeColor("green"); art.setHeight(182.0); art.setWeight(75.0); art.getpInfo().add(info);
        people.getPeople().add(sasha); people.getPeople().add(masha); people.getPeople().add(art);
        
        ParserContext context = ParserContextFactory.getInstance().getParserContext(Type.XML, "by.artkostm.androidparsers");
        ParserContext.Marshaller<People> marshaller = context.getMarshaller(People.class);
        marshaller.marshal(people, new File("people11.xml"));
      
        ParserContext.Unmarshaller<People> unmarshaller = context.getUnmarshaller();
        long start = System.currentTimeMillis();
        people = unmarshaller.unmarshal(new File("people.xml"));
        System.out.println("Time is "+(System.currentTimeMillis() - start)+"ms");
        System.out.println("Objects: "+people.getPeople().size());
    }
    
    private static void properties(){
        ParserContext context = ParserContextFactory.getInstance().getParserContext(Type.PROPS, "by.artkostm.androidparsers");
        ParserContext.Unmarshaller<TestProps> unmarshaller = context.getUnmarshaller(TestProps.class);
        long s = System.currentTimeMillis();
        TestProps tp = unmarshaller.unmarshal(new File("test.properties"));
        System.out.println("Time is "+(System.currentTimeMillis() - s)+"ms\n"+tp);
    }
    
    private static void csv(){
        ParserContext context = ParserContextFactory.getInstance().getParserContext(Type.CSV, "by.artkostm.androidparsers");
        ParserContext.Unmarshaller<TestCsv> unmarshaller = context.getUnmarshaller(TestCsv.class);
        long start = System.currentTimeMillis();
        List<TestCsv> list = unmarshaller.unmarshalAll(new File("test.csv"));
        System.out.println("Time is "+(System.currentTimeMillis() - start)+"ms");
        System.out.println(list);
    }
    
    public static class TestCsv{
        @MapToColumn(column = 0)
        private String fieldOne;
        @MapToColumn(column = 1)
        private double fieldTwo;
        @MapToColumn(column = 2)
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
    
    @SuppressWarnings("unused")
    private static void print(People people){
        System.out.println(String.format("People(%d): ", people.getPeople().size()));
        for(Man man : people.getPeople()){
            System.out.println(String.format("\t Name: %s, Age: %d\n", man.getName(), man.getAge()));
        }
    }
}