package by.artkostm.androidparsers.core.context.internal;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import by.artkostm.androidparsers.core.context.ParserContext;
import by.artkostm.androidparsers.core.enhancer.Enhancer;
import by.artkostm.androidparsers.core.enhancer.PropertyEnhancer;

public class PropsParserContext implements ParserContext
{
    @SuppressWarnings("unused")
    private final String pkg;
    
    public PropsParserContext(String pkg)
    {
        this.pkg = pkg;
    }

    @Override
    public <T> Unmarshaller<T> getUnmarshaller(final Class<?>...cls)
    {
        final Unmarshaller<T> u = new Unmarshaller<T>() {
            @SuppressWarnings("unchecked")
            @Override
            public T unmarshal(File file) {
                BufferedReader br = null;
                Object t = null;
                try{
                    FileReader reader = new FileReader(file);
                    br = new BufferedReader(reader);
                    String str = null;
                    List<String> source = new ArrayList<>();
                    while((str = br.readLine()) != null){
                        if(!str.trim().isEmpty()){
                            source.add(str);
                        }
                    }
                    Enhancer e = new PropertyEnhancer(cls[0]);
                    t = e.enhance(source);
                } catch (IOException e1) {
                    throw new RuntimeException("Can't read a file");
                }finally{
                    if(br != null){
                        try{br.close();}catch(Exception e){}
                    }
                }
                return (T)t;
            }
        };
        return u;
    }

    @Override
    public <T> Marshaller<T> getMarshaller(Class<?> cls)
    {
        // TODO Auto-generated method stub
        return null;
    }

}
