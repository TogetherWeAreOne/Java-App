package com.togetherweareone.fx.extensions;

import javafx.scene.Scene;
import org.pf4j.ExtensionPoint;

public interface CreateColumnExtensionPoint extends ExtensionPoint {

    void loadCreateColumnModifications(Scene scene);

}
