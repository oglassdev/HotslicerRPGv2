package com.hotslicerrpg.rpg.Scripting;

import com.hotslicerrpg.rpg.Main;
import jdk.nashorn.api.scripting.NashornScriptEngine;
import jdk.nashorn.api.scripting.NashornScriptEngineFactory;

import javax.script.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class ScriptFile {
    public ScriptFile(File file) {
        NashornScriptEngineFactory engineFactory = new NashornScriptEngineFactory();
        NashornScriptEngine nashorn = (NashornScriptEngine) engineFactory.getScriptEngine("nashorn");

        Bindings bindings = nashorn.createBindings();
        bindings.put("scriptpath",Main.getPlugin().getDataFolder().getAbsolutePath() + File.separator + "Scripts" + File.separator);
        try {
            nashorn.eval(new FileReader(file),bindings);
        } catch (FileNotFoundException | ScriptException e) {
            e.printStackTrace();
        }
    }
}
