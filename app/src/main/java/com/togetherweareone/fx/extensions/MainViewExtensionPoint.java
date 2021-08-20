package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

import java.io.IOException;

public interface MainViewExtensionPoint extends ExtensionPoint {

    void loadMainViewModifications(Scene scene);

}
