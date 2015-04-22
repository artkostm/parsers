package by.artkostm.androidparsers.core.enhancer;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import by.artkostm.androidparsers.core.builders.Builder;
import by.artkostm.androidparsers.core.builders.PropsObjectBuilder;

public class PropertyEnhancer implements Enhancer{
    
    private Class<?> type;
    
    public PropertyEnhancer(Class<?> type) {
        this.type = type;
    }

    @Override
    public Object enhance(Object source) {
        List<String> props = getProps((File)source);
        Builder<?> builder = new PropsObjectBuilder<>(type);
        Object obj = builder.build(props);
        return obj;
    }
    
    private List<String> getProps(File file){
        BufferedReader br = null;
        List<String> source = new ArrayList<>();
        try{
            FileReader reader = new FileReader(file);
            br = new BufferedReader(reader);
            String str = null;
            while((str = br.readLine()) != null){
                if(!str.trim().isEmpty()){
                    source.add(str);
                }
            }
        } catch (IOException e1) {
            throw new RuntimeException("Can't read a file");
        }finally{
            if(br != null){
                try{br.close();}catch(Exception e){}
            }
        }
        return source;
    }
}
