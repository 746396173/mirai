package net.mamoe.mirai.utils.config;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.Map;

public class MiraiConfigSection<T> extends MiraiSynchronizedLinkedListMap<String, T> {

    public MiraiConfigSection(){
        super();
    }

    public MiraiConfigSection(LinkedHashMap<String, T> map){
        super(map);
    }



    public Integer getInt(String key){
        return Integer.valueOf(this.get(key).toString());
    }

    public Double getDouble(String key){
        return Double.valueOf(this.get(key).toString());
    }

    public Float getFloat(String key){
        return Float.valueOf(this.get(key).toString());
    }

    public String getString(String key){
        return String.valueOf(this.get(key));
    }


    @Nullable
    @Override
    public T put(String key, T value) {
        return super.put(key, value);
    }

    @SuppressWarnings("unchecked")
    public <D> MiraiConfigSection<D> getTypedSection(String key){
        var content = this.get(key);
        if(content instanceof MiraiConfigSection){
            return (MiraiConfigSection<D>) content;
        }
        if(content instanceof Map){
            return new MiraiConfigSection<>(
                (LinkedHashMap<String, D>) content
            );
        }
        return null;
    }

    public MiraiConfigSection<Object> getSection(String key){
        return this.getTypedSection(key);
    }



}
