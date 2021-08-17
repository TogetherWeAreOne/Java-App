package com.togetherweareone;

import com.togetherweareone.cli.Cli;
import com.togetherweareone.fx.Fx;
import javafx.application.Application;
import javafx.stage.Stage;
import org.pf4j.DefaultPluginManager;
import org.pf4j.PluginManager;


import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws IOException {
        new Fx(primaryStage);
    }

    public static void main(String[] args) {

        PluginManager pluginManager = new DefaultPluginManager();
        pluginManager.loadPlugins();
        pluginManager.startPlugins();
        //System.out.println(System.getProperty("pf4j.pluginsDir", "plugins"));

        if (args.length > 0 && args[0].equals("-cli")) {
            new Cli();
        } else {
            launch(args);
        }
        System.exit(0);
    }
}
