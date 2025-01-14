package net.mamoe.mirai.utils.setting;

import org.ini4j.Config;
import org.ini4j.Ini;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Mirai Config
 * Only support {INI} format
 * Support MAP and LIST
 * Thread safe
 */
public class MiraiSetting {

    private File file;

    private Ini ini;

    private volatile Map<String, MiraiSettingSection> cacheSection = new ConcurrentHashMap<>();

    public MiraiSetting(File file){
        if(!file.getName().contains(".")){
            file = new File(file.getParent() + file.getName() + ".ini");
        }
        this.file = file;
        try {
            if(file.exists()){
                file.createNewFile();
            }
            Config config = new Config();
            config.setMultiSection(true);
            ini = new Ini();
            ini.setConfig(config);
            ini.load(this.file.toURI().toURL());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setSection(String key, MiraiSettingSection section){
        cacheSection.put(key, section);
    }


    public MiraiSettingMapSection getMapSection(String key){
        if(!cacheSection.containsKey(key)) {
            MiraiSettingMapSection section = new MiraiSettingMapSection();
            if(ini.containsKey(key)){
                section.putAll(ini.get(key));
            }
            cacheSection.put(key, section);
        }
        return (MiraiSettingMapSection) cacheSection.get(key);
    }

    public MiraiSettingListSection getListSection(String key){
        if(!cacheSection.containsKey(key)) {
            MiraiSettingListSection section = new MiraiSettingListSection();
            if(ini.containsKey(key)){
                section.addAll(ini.get(key).values());
            }
            cacheSection.put(key, section);
        }
        return (MiraiSettingListSection) cacheSection.get(key);
    }


    public synchronized void save(){
        cacheSection.forEach((k,a) -> {
            if(!ini.containsKey(k)) {
                ini.put(k,"",new HashMap<>());
            }
            a.saveAsSection(ini.get(k));
        });
        this.clearCache();
        try {
            ini.store(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void clearCache(){
        cacheSection.clear();
    }
}

